package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = Firebase.auth

        val btnCrear: Button = findViewById(R.id.btn_crear)

        btnCrear.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        var etCorreo: EditText = findViewById(R.id.input_correo)
        var etPassword: EditText = findViewById(R.id.input_password)
        var etVerifyPassword: EditText = findViewById(R.id.inpud_verify_password)

        if (etCorreo.text.isBlank() || etPassword.text.isBlank() || etVerifyPassword.text.isBlank()) {
            Toast.makeText(this, "Llene los campos antes de registrar un nuevo usuario", Toast.LENGTH_SHORT).show()
            return
        }

        var correo: String = etCorreo.text.toString().trim()
        var password: String = etPassword.text.toString().trim()
        var verifyPassword: String = etVerifyPassword.text.toString().trim()

        if (password != verifyPassword) {
            var invalidPass: TextView = findViewById(R.id.tv_invalidpass)
            invalidPass.setText("La contraseña no coincide")
            return
        }

        auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@Registro, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                reload()
            } else {
                when (task.exception) {
                    is FirebaseAuthWeakPasswordException -> {
                        Toast.makeText(this@Registro, "La contraseña debe de tener mínimo 6 caracteres", Toast.LENGTH_LONG).show()
                    }

                    is FirebaseAuthUserCollisionException -> {
                        Toast.makeText(this@Registro, "Ese usuario ya existe", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this@Registro, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun reload() {
        val intent = Intent(this, Bienvenido::class.java)
        startActivity(intent)
        finish()
    }
}