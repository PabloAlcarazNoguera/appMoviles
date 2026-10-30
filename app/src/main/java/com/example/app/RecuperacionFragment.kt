package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

/**
 * Recuperación de contraseña con dos estados dentro del mismo fragment:
 *  1) El usuario escribe su correo y pulsa "Enviar código".
 *  2) El usuario introduce el código de 6 dígitos recibido y lo verifica.
 */
class RecuperacionFragment : Fragment(R.layout.fragment_recuperacion) {

    private lateinit var grupoPaso1: View
    private lateinit var grupoPaso2: View
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilCodigo: TextInputLayout
    private lateinit var tvInstruccion: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        grupoPaso1 = view.findViewById(R.id.grupoPaso1)
        grupoPaso2 = view.findViewById(R.id.grupoPaso2)
        tilEmail = view.findViewById(R.id.tilEmail)
        tilCodigo = view.findViewById(R.id.tilCodigo)
        tvInstruccion = view.findViewById(R.id.tvInstruccion)

        tilEmail.limpiarErrorAlEscribir()
        tilCodigo.limpiarErrorAlEscribir()

        // Restaura el estado si se gira la pantalla
        if (savedInstanceState?.getBoolean(ESTADO_CODIGO) == true) mostrarEstadoCodigo()

        view.findViewById<View>(R.id.btnEnviarCodigo).setOnClickListener {
            if (tilEmail.validarEmail()) {
                mostrarEstadoCodigo()
                Toast.makeText(requireContext(), R.string.msg_codigo_enviado, Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<View>(R.id.btnRestablecer).setOnClickListener {
            if (tilCodigo.validarCodigo()) {
                // Prototipo: aquí se comprobaría el código con el servidor
                Toast.makeText(requireContext(), R.string.msg_codigo_enviado, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // vuelve al Login
            }
        }

        view.findViewById<View>(R.id.btnCambiarCorreo).setOnClickListener {
            mostrarEstadoCorreo()
        }

        view.findViewById<View>(R.id.btnVolverLogin).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun mostrarEstadoCorreo() {
        grupoPaso1.visibility = View.VISIBLE
        grupoPaso2.visibility = View.GONE
        tvInstruccion.setText(R.string.recuperacion_paso1)
    }

    private fun mostrarEstadoCodigo() {
        grupoPaso1.visibility = View.GONE
        grupoPaso2.visibility = View.VISIBLE
        tvInstruccion.setText(R.string.recuperacion_paso2)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ESTADO_CODIGO, grupoPaso2.visibility == View.VISIBLE)
    }

    private companion object {
        const val ESTADO_CODIGO = "estado_codigo"
    }
}