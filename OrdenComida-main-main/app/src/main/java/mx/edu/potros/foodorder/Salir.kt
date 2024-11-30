package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

class Salir : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salir)

        val buttonSalir: Button = findViewById(R.id.btn_salir)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar)

        buttonSalir.setOnClickListener {
            logOut()
        }

        buttonCancelar.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logOut() {
        lifecycleScope.launch {
            try {
                // Sign out from Supabase
                SupabaseClient.client.gotrue.logout()
                
                // Navigate to login screen
                val intent = Intent(this@Salir, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                // Handle any errors
            }
        }
    }
}