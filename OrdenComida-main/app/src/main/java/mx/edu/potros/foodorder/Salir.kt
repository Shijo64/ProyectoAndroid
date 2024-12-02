package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
        //auth.signOut()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}