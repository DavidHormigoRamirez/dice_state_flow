package com.alanturing.cpifp.stateflowdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

// Clases con estado de la IU, siempre
// las propiedades no modificables!
class DiceUiState(
    val value:Int=0
)

class DiceViewModel(): ViewModel() {
    // Estado que representamos, en este caso MutableStateFlow
    // MutableStateFlow necesita un estado inicial
    private val _uiState: MutableStateFlow<DiceUiState> = MutableStateFlow(DiceUiState(Random.nextInt(1..6)))
    // Exponemos el estado como no mutable
    val uiState:StateFlow<DiceUiState>
        get() = _uiState.asStateFlow()
    // Función que tira el dado y emite el nuevo estado!
    fun rollDice() {
        viewModelScope.launch {
            val result = DiceUiState(Random.nextInt(1..6))
            _uiState.value = result
        }
    }
}