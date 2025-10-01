/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hugopolog.demoappopen.ui.feature.BaseViewModel
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : BaseViewModel() {

    var screenState by mutableStateOf(DetailState())
        private set

    init {
        screenState = screenState.copy(isLoading = true)
    }

    fun onAction(action: DetailActions) {
        when (action) {
            is DetailActions.BackClicked -> {
                navigateUp()
            }
            is DetailActions.TabSelected -> {
                screenState = screenState.copy(
                    selectedTab = action.tab
                )
            }
        }
    }




    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            Log.d( "DetailViewModel", "Fetching details for Pokemon ID: $id")
            when(val result = getPokemonDetailUseCase(id)) {
                is DataResult.Success -> {
                    val pokemon = result.data
                    screenState = screenState.copy(
                        isLoading = false,
                        pokemon = pokemon
                    )
                }
                is  DataResult.Error -> {
                    // Handle error
                }
            }
        }
    }

}