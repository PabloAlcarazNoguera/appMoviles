package com.example.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.limpiarErrorAlEscribir
import com.example.app.validarConfirmacion
import com.example.app.validarEmail
import com.example.app.validarNombre
import com.example.app.validarPasswordSegura
import com.google.android.material.textfield.TextInputLayout

/** Pantalla de Registro (Sign Up) con validación visual de cada campo. */
class RegistroFragment : Fragment(R.layout.fragment_registro) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilNombre = view.findViewById<TextInputLayout>(R.id.tilNombre)
        val tilEmail = view.findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.tilPassword)
        val tilConfirmar = view.findViewById<TextInputLayout>(R.id.tilConfirmar)
        listOf(tilNombre, tilEmail, tilPassword, tilConfirmar).forEach { it.limpiarErrorAlEscribir() }

        view.findViewById<View>(R.id.btnRegistrar).setOnClickListener {
            val resultados = listOf(
                tilNombre.validarNombre(),
                tilEmail.validarEmail(),
                tilPassword.validarPasswordSegura(),
                tilConfirmar.validarConfirmacion(tilPassword)
            )
            if (resultados.all { it }) {
                Toast.makeText(requireContext(), R.string.msg_registro_ok, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registro_to_login)
            }
        }
        view.findViewById<View>(R.id.btnIrLogin).setOnClickListener {
            findNavController().navigate(R.id.action_registro_to_login)
        }
    }
}
