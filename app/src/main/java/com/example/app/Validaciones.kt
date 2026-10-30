package com.example.app

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

/** Reglas de validación (sin interfaz). */
object Validaciones {
    fun esEmailValido(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun esPasswordValida(password: String) =
        password.length >= 8 && password.any { it.isLetter() } && password.any { it.isDigit() }

    fun esNombreValido(nombre: String) = nombre.trim().length >= 2

    fun esCodigoValido(codigo: String) = codigo.length == 6 && codigo.all { it.isDigit() }
}

// ---- Validación visual: muestra el error en el propio TextInputLayout ----

/** Texto que hay escrito en el campo. */
val TextInputLayout.texto: String
    get() = editText?.text?.toString().orEmpty()

/** Quita el mensaje de error en cuanto el usuario vuelve a escribir. */
fun TextInputLayout.limpiarErrorAlEscribir() {
    editText?.doAfterTextChanged { if (error != null) error = null }
}

private fun TextInputLayout.fallo(@StringRes mensaje: Int): Boolean {
    error = context.getString(mensaje)
    return false
}

private fun TextInputLayout.correcto(): Boolean {
    error = null
    return true
}

fun TextInputLayout.validarNombre(): Boolean =
    if (Validaciones.esNombreValido(texto)) correcto() else fallo(R.string.error_nombre)

fun TextInputLayout.validarEmail(): Boolean {
    val email = texto.trim()
    return when {
        email.isEmpty() -> fallo(R.string.error_email_vacio)
        !Validaciones.esEmailValido(email) -> fallo(R.string.error_email_invalido)
        else -> correcto()
    }
}

/** Para el login basta con que no esté vacía. */
fun TextInputLayout.validarPasswordNoVacia(): Boolean =
    if (texto.isNotEmpty()) correcto() else fallo(R.string.error_password_vacia)

/** Para el registro y el restablecimiento se exige una contraseña segura. */
fun TextInputLayout.validarPasswordSegura(): Boolean = when {
    texto.isEmpty() -> fallo(R.string.error_password_vacia)
    !Validaciones.esPasswordValida(texto) -> fallo(R.string.error_password_debil)
    else -> correcto()
}

fun TextInputLayout.validarConfirmacion(original: TextInputLayout): Boolean = when {
    texto.isEmpty() -> fallo(R.string.error_confirmar_vacia)
    texto != original.texto -> fallo(R.string.error_confirmar_distinta)
    else -> correcto()
}

fun TextInputLayout.validarCodigo(): Boolean =
    if (Validaciones.esCodigoValido(texto)) correcto() else fallo(R.string.error_codigo)
