package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import mx.edu.potros.foodorder.Singleton.Appwrite
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Appwrite.init(applicationContext)

        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnRegistro: Button = findViewById(R.id.btn_crearCuenta)

        viewModel.checkIfLoggedIn()
        viewModel.loginResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                reload()
            }
        }

        btnLogin.setOnClickListener {
            logIn()
        }

        btnRegistro.setOnClickListener {
            var intent = Intent(this, RegistroActivity::class.java)
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

        viewModel.login(usuario, password)
    }

    private fun reload() {
        val intent = Intent(this, BienvenidoActivity::class.java)
        startActivity(intent)
        finish()
    }
}