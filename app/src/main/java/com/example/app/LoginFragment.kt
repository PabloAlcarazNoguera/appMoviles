package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilEmail = view.findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.tilPassword)

        tilEmail.limpiarErrorAlEscribir()
        tilPassword.limpiarErrorAlEscribir()

        view.findViewById<View>(R.id.btnLogin).setOnClickListener {
            // Se evalúan ambos para mostrar todos los errores a la vez
            val emailOk = tilEmail.validarEmail()
            val passOk = tilPassword.validarPasswordNoVacia()
            if (emailOk && passOk) {
                // Prototipo: aquí iría la llamada real de autenticación
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
