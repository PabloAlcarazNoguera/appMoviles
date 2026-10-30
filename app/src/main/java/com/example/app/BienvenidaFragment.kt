package com.example.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class BienvenidaFragment : Fragment(R.layout.fragment_bienvenida) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnLogin).setOnClickListener {
            findNavController().navigate(R.id.action_bienvenida_to_login)
        }
        view.findViewById<View>(R.id.btnRegistrar).setOnClickListener {
            findNavController().navigate(R.id.action_bienvenida_to_registro)
        }
    }
}
