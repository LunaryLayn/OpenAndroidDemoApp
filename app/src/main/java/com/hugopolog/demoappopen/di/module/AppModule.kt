package com.hugopolog.demoappopen.di.module

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.hugopolog.data.api.ApiService
import com.hugopolog.data.local.Constants
import com.hugopolog.data.repository.MainRepositoryImpl
import com.hugopolog.demoappopen.di.app.DemoApp
import com.hugopolog.demoappopen.navigation.AppScreens
import com.hugopolog.demoappopen.navigation.DefaultNavigator
import com.hugopolog.demoappopen.navigation.Navigator
import com.hugopolog.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): DemoApp {
        return app as DemoApp
    }

    @Provides
    @Singleton
    fun provideContext(application: DemoApp): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return DefaultNavigator(startDestination = AppScreens.MainScreen)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object CoilModule {

        @Provides
        @Singleton
        fun provideImageLoader(
            @ApplicationContext context: Context
        ): ImageLoader {
            return ImageLoader.Builder(context)
                .dispatcher(Dispatchers.IO.limitedParallelism(4)) // limit concurrent decoding
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(apolloClient: ApolloClient): MainRepository {
        return MainRepositoryImpl(apolloClient)
    }
}