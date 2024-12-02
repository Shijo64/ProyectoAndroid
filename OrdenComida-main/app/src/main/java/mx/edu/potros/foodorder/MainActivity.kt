package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            reload()
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

        var usuario: String = etUsuario.text.toString().trim()
        var password: String = etPassword.text.toString().trim()

        auth.signInWithEmailAndPassword(usuario, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
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