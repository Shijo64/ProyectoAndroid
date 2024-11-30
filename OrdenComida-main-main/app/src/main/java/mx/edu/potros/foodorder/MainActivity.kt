package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.providers.builtin.Email

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if user is already logged in
        lifecycleScope.launch {
            try {
                val session = SupabaseClient.client.gotrue.currentSessionOrNull()
                if (session != null) {
                    reload()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity,
                    "Error checking session: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val tvOlvidasteContra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnRegistro: Button = findViewById(R.id.btn_crearCuenta)

        tvOlvidasteContra.setOnClickListener {
            val intent = Intent(this, LoginVerificacion::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            logIn()
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {

        val etEmail: EditText = findViewById(R.id.et_usuario)
        val etPassword: EditText = findViewById(R.id.et_password)

        val userEmail = etEmail.text.toString()
        val userPassword = etPassword.text.toString()

        if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    // Updated sign-in method using Email provider
                    SupabaseClient.client.gotrue.loginWith(Email) {
                        email = userEmail
                        password = userPassword
                    }
                    reload()
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity,
                            "Error logging in: ${e.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this,
                "Please fill all the fields",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun reload() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }
}