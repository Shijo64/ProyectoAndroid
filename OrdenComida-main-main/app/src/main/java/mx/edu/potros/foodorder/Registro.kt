package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Registro : AppCompatActivity() {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://fbizjwpnslniypbvpajs.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZiaXpqd3Buc2xuaXlwYnZwYWpzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEzODI5NDcsImV4cCI6MjA0Njk1ODk0N30.y7xI6gRFnEofoY-_cmJFKDjsYw31W_jYaiRysGl0xQ0"
    ) {
        install(GoTrue)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnCrear: Button = findViewById(R.id.btn_crear)

        btnCrear.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val etCorreo: EditText = findViewById(R.id.input_correo)
        val etPassword: EditText = findViewById(R.id.input_password)

        val email = etCorreo.text.toString()
        val password = etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Updated syntax for Supabase 1.3.2
                    val response = supabase.gotrue.signUpWith(Email) {
                        this.email = email
                        this.password = password
                    }

                    runOnUiThread {
                        Toast.makeText(
                            this@Registro,
                            "Usuario creado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@Registro, MainActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        val message = when {
                            e.message?.contains("weak password") == true ->
                                "La contraseña debe tener al menos 6 caracteres"
                            e.message?.contains("email already exists") == true ->
                                "Este correo ya está registrado"
                            else -> "Error al crear usuario: ${e.message}"
                        }
                        Toast.makeText(this@Registro, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(
                this@Registro,
                "Por favor complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}