package com.alanturing.cpifp.stateflowdemo


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // ViewModel delegado: esto permite que sobreviva a la destrucción o recreación
    // de la actividdad
    private val viewModel: DiceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.roll_dice)
        val text: TextView = findViewById(R.id.dice_result)
        button.setOnClickListener {
            // Método que tira el dado
           viewModel.rollDice()
        }
        // Este método, es una corutina!. Es código suspendible
        // lifecylescope hace que el código dentro se pare en función
        // de los eventos de ciclo de vida de la actividad
        // Si se para, Kotlin suspendera la función suspendible
        lifecycleScope.launch {
            // Este método, hace que cada vez que se alcance o supere el
            // estado de ciclo de vida por parametro, se ejecute el
            // código que tiene dentro
            repeatOnLifecycle(State.STARTED) {
                // Esta corrutina, en este caso lo que hace es suscribirse al
                // flujo expuestos por el modelo de datos y leer el último valor
                // emitido. uiState es de tipo StateFlow y representa estados
                // que comunicamos a la Interfaz de usuraio
                viewModel.uiState.collect { state ->
                    // Actualizamos la interfaz de usuario en función
                    // del estado
                    text.text = state.value.toString()
                }
            }
        }
    }
}