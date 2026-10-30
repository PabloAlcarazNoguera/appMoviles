package com.example.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.limpiarErrorAlEscribir
import com.example.app.validarEmail
import com.example.app.validarPasswordNoVacia
import com.google.android.material.textfield.TextInputLayout

/** Pantalla de Login con acceso directo a la recuperación de contraseña. */
class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilEmail = view.findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.tilPassword)
        tilEmail.limpiarErrorAlEscribir()
        tilPassword.limpiarErrorAlEscribir()

        view.findViewById<View>(R.id.btnLogin).setOnClickListener {
            // Se validan ambos campos para mostrar todos los errores a la vez
            val resultados = listOf(tilEmail.validarEmail(), tilPassword.validarPasswordNoVacia())
            if (resultados.all { it }) {
                Toast.makeText(requireContext(), R.string.msg_login_ok, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<View>(R.id.btnOlvide).setOnClickListener {
            findNavController().navigate(R.id.action_login_to_recuperacion)
        }
        view.findViewById<View>(R.id.btnIrRegistro).setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registro)
        }
    }
}
