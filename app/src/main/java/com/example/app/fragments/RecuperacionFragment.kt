package com.example.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.limpiarErrorAlEscribir
import com.example.app.validarCodigo
import com.example.app.validarConfirmacion
import com.example.app.validarEmail
import com.example.app.validarPasswordSegura
import com.google.android.material.textfield.TextInputLayout

/**
 * Recuperación de contraseña en dos estados dentro de la misma pantalla:
 *  1) pedir el correo y "enviar" el código
 *  2) introducir el código de verificación y la nueva contraseña
 */
class RecuperacionFragment : Fragment(R.layout.fragment_recuperacion) {

    private var paso = PASO_CORREO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paso = savedInstanceState?.getInt(KEY_PASO, PASO_CORREO) ?: PASO_CORREO

        val tvInstruccion = view.findViewById<TextView>(R.id.tvInstruccion)
        val grupoPaso1 = view.findViewById<View>(R.id.grupoPaso1)
        val grupoPaso2 = view.findViewById<View>(R.id.grupoPaso2)
        val tilEmail = view.findViewById<TextInputLayout>(R.id.tilEmail)
        val tilCodigo = view.findViewById<TextInputLayout>(R.id.tilCodigo)
        val tilNueva = view.findViewById<TextInputLayout>(R.id.tilNuevaPassword)
        val tilConfirmar = view.findViewById<TextInputLayout>(R.id.tilConfirmar)
        listOf(tilEmail, tilCodigo, tilNueva, tilConfirmar).forEach { it.limpiarErrorAlEscribir() }

        // Muestra u oculta cada estado según el paso actual
        fun mostrarPaso() {
            val enCorreo = paso == PASO_CORREO
            grupoPaso1.visibility = if (enCorreo) View.VISIBLE else View.GONE
            grupoPaso2.visibility = if (enCorreo) View.GONE else View.VISIBLE
            tvInstruccion.setText(
                if (enCorreo) R.string.recuperacion_paso1 else R.string.recuperacion_paso2
            )
        }
        mostrarPaso()

        view.findViewById<View>(R.id.btnEnviarCodigo).setOnClickListener {
            if (tilEmail.validarEmail()) {
                Toast.makeText(requireContext(), R.string.msg_codigo_enviado, Toast.LENGTH_SHORT).show()
                paso = PASO_CODIGO
                mostrarPaso()
            }
        }
        view.findViewById<View>(R.id.btnCambiarCorreo).setOnClickListener {
            paso = PASO_CORREO
            mostrarPaso()
        }
        view.findViewById<View>(R.id.btnRestablecer).setOnClickListener {
            val resultados = listOf(
                tilCodigo.validarCodigo(),
                tilNueva.validarPasswordSegura(),
                tilConfirmar.validarConfirmacion(tilNueva)
            )
            if (resultados.all { it }) {
                Toast.makeText(requireContext(), R.string.msg_password_restablecida, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // vuelve al Login
            }
        }
        view.findViewById<View>(R.id.btnVolverLogin).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PASO, paso)
    }

    private companion object {
        const val PASO_CORREO = 1
        const val PASO_CODIGO = 2
        const val KEY_PASO = "paso"
    }
}
