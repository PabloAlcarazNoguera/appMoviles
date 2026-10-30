package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class RegistroFragment : Fragment(R.layout.fragment_registro) {

    private lateinit var tilNombre: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmar: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tilNombre = view.findViewById(R.id.tilNombre)
        tilEmail = view.findViewById(R.id.tilEmail)
        tilPassword = view.findViewById(R.id.tilPassword)
        tilConfirmar = view.findViewById(R.id.tilConfirmar)

        listOf(tilNombre, tilEmail, tilPassword, tilConfirmar).forEach {
            it.limpiarErrorAlEscribir()
        }

        view.findViewById<View>(R.id.btnRegistrar).setOnClickListener {
            if (formularioValido()) {
                // Prototipo: aquí iría la creación real de la cuenta
                Toast.makeText(requireContext(), R.string.msg_registro_ok, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registro_to_login)
            }
        }

        view.findViewById<View>(R.id.btnIrLogin).setOnClickListener {
            findNavController().navigate(R.id.action_registro_to_login)
        }
    }

    private fun formularioValido(): Boolean {
        // Se evalúan todos para mostrar todos los errores a la vez
        val nombreOk = tilNombre.validarNombre()
        val emailOk = tilEmail.validarEmail()
        val passOk = tilPassword.validarPasswordSegura()
        val confirmarOk = tilConfirmar.validarConfirmacion(tilPassword)
        return nombreOk && emailOk && passOk && confirmarOk
    }
}