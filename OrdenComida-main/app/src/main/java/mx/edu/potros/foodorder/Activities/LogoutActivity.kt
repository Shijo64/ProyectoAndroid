package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import kotlinx.coroutines.coroutineScope
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.LogoutViewModel

class LogoutActivity : AppCompatActivity() {

    private val viewModel: LogoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salir)

        val buttonSalir: Button = findViewById(R.id.btn_salir)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar)

        buttonSalir.setOnClickListener{
            logOut()
        }

        buttonCancelar.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logOut() {
        viewModel.logout()
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}