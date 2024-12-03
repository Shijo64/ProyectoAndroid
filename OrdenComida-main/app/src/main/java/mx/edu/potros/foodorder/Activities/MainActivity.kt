package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.LoginVerificacion
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            Appwrite.init(applicationContext)
            val result = Appwrite.account.getLoggedIn()
            if (result != null) {
                reload()
            }
        }

        val tvOlvidasteContra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnRegistro: Button = findViewById(R.id.btn_crearCuenta)

        tvOlvidasteContra.setOnClickListener {
            var intent = Intent(this, LoginVerificacion::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            logIn()
        }

        btnRegistro.setOnClickListener {
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {
        var etUsuario: EditText = findViewById(R.id.et_usuario)
        var etPassword: EditText = findViewById(R.id.et_password)

        if (etUsuario.text.isBlank() || etPassword.text.isBlank()) {
            Toast.makeText(this, "Llene los campos antes de iniciar sesión", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario: String = etUsuario.text.toString().trim()
        val password: String = etPassword.text.toString().trim()

        lifecycleScope.launch {
            val result = Appwrite.account.login(usuario, password)
            if (result != null) {
                Toast.makeText(this@MainActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                reload()
            } else {
                Toast.makeText(this@MainActivity, "Hubo un problema al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun reload() {
        val intent = Intent(this, Bienvenido::class.java)
        startActivity(intent)
        finish()
    }
}