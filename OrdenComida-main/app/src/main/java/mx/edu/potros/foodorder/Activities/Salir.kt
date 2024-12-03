package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.R

class Salir : AppCompatActivity() {

    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salir)

        //auth = Firebase.auth

        val buttonSalir: Button = findViewById(R.id.btn_salir)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar)

        buttonSalir.setOnClickListener{
            logOut()
        }

        buttonCancelar.setOnClickListener{
            var intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logOut() {
        lifecycleScope.launch {
            Appwrite.account.logout()
        }
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}