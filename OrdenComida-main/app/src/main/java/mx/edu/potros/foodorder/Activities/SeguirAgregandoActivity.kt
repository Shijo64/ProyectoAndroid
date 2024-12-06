package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.OrdenViewModel

class SeguirAgregandoActivity : AppCompatActivity() {

    private val viewModel: OrdenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguir_agregando)

        var numeroMesa = ""
        var nombreOrden = ""
        val btnSeguir = findViewById<Button>(R.id.btn_seguir)
        val btnFinalizar = findViewById<Button>(R.id.btn_finalizar)

        val bundle = intent.extras

        if (bundle != null) {
            numeroMesa = bundle.getString("numeroMesa").toString()
            nombreOrden = bundle.getString("nombreOrden").toString()
        }

        btnSeguir.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("numeroMesa", numeroMesa)
            intent.putExtra("nombreOrden", nombreOrden)
            startActivity(intent)
            finish()
        }

        btnFinalizar.setOnClickListener {
            val orden = Orden(
                nombreOrden = nombreOrden,
                numeroMesa = numeroMesa
            )
            viewModel.guardarOrden(orden)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}