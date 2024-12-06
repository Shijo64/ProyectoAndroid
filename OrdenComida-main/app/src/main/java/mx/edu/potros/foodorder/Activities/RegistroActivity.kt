package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.RegistroViewModel

class RegistroActivity : AppCompatActivity() {

    val viewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnCrear: Button = findViewById(R.id.btn_crear)
        val etCorreo: EditText = findViewById(R.id.input_correo)
        val etPassword: EditText = findViewById(R.id.input_password)
        val etVerifyPassword: EditText = findViewById(R.id.inpud_verify_password)
        val btnBack: Button = findViewById(R.id.btn_back)

        viewModel.loginResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                reload()
            }
        }

        btnCrear.setOnClickListener {
            viewModel.correo = etCorreo.text.toString()
            viewModel.contraseña = etPassword.text.toString()
            viewModel.contraseñaVerificada = etVerifyPassword.text.toString()

            if (etCorreo.text.isBlank() || etPassword.text.isBlank() || etVerifyPassword.text.isBlank()) {
                Toast.makeText(this, "Llene los campos antes de registrar un nuevo usuario", Toast.LENGTH_SHORT).show()
            }else if (viewModel.contraseña != viewModel.contraseñaVerificada) {
                val invalidPass: TextView = findViewById(R.id.tv_invalidpass)
                invalidPass.setText("La contraseña no coincide")
            } else {
                viewModel.createAccount()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun reload() {
        val intent = Intent(this, BienvenidoActivity::class.java)
        startActivity(intent)
        finish()
    }
}