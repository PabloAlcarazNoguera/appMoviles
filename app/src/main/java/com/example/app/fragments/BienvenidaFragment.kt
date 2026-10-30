package com.example.app.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R

/** Pantalla de bienvenida (splash) con las llamadas a la acción Login / Registro. */
class BienvenidaFragment : Fragment(R.layout.fragment_bienvenida) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnIrLogin).setOnClickListener {
            findNavController().navigate(R.id.action_bienvenida_to_login)
        }
        view.findViewById<View>(R.id.btnIrRegistro).setOnClickListener {
            findNavController().navigate(R.id.action_bienvenida_to_registro)
        }
    }
}
