package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SeguirAgregando : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguir_agregando)

        var numMesa: String? = ""
        var nombreCuenta: String? = ""
        var numCuentas: String? = ""
        val btnSeguir: Button = findViewById(R.id.btn_seguir)
        val btnFinalizar: Button = findViewById(R.id.btn_finalizar)

        val bundle = intent.extras

        if (bundle != null) {
            numMesa = bundle.getString("mesa")
            nombreCuenta = bundle.getString("cuenta")
            numCuentas = bundle.getString("numCuentas")
        }

        btnSeguir.setOnClickListener {
            val intent = Intent(this, Menu::class.java).apply {
                putExtra("mesa", numMesa)
                putExtra("cuenta", nombreCuenta)
                putExtra("numCuentas", numCuentas)
            }
            startActivity(intent)
            finish()
        }

        btnFinalizar.setOnClickListener {
            finalizarCuenta(numMesa, numCuentas)
        }
    }

    private fun finalizarCuenta(numMesa: String?, numCuentas: String?) {
        val intent = Intent(this@SeguirAgregando, FinCuenta::class.java).apply {
            putExtra("mesa", numMesa)
            putExtra("numCuentas", numCuentas)
        }
        startActivity(intent)
        finish()
    }
}