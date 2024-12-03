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
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.R

class Registro : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnCrear: Button = findViewById(R.id.btn_crear)

        btnCrear.setOnClickListener {
            lifecycleScope.launch {
                signUp()
            }
        }
    }

    private suspend fun signUp() {
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

        try {
            var result = Appwrite.account.register(correo, password)
            if (result != null) {
                Toast.makeText(this@Registro, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                this.reload()
            } else {
                Toast.makeText(this@Registro, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
            }
        } catch(e : Exception) {
            e.printStackTrace()
        }

        /*auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this) { task ->
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
        }*/
    }

    private fun reload() {
        val intent = Intent(this, Bienvenido::class.java)
        startActivity(intent)
        finish()
    }
}