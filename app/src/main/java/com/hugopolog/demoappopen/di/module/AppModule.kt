package com.hugopolog.demoappopen.di.module

import android.content.Context
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(service: ApiService): MainRepository {
        return MainRepositoryImpl(service)
    }
}