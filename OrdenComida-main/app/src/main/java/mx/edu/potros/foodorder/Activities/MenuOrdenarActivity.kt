package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.R.id.spinner_numero_mesa

class MenuOrdenarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_ordenar)

        val numeroMesaSpinner: Spinner = findViewById(spinner_numero_mesa)
        val etNombreOrden: EditText = findViewById(R.id.et_nombre_orden)
        val btnOrdenar: Button = findViewById(R.id.btn_ordenar)
        val btnRegresar: Button = findViewById(R.id.btn_regresar)

        btnOrdenar.setOnClickListener {
            var numeroMesa = numeroMesaSpinner.selectedItem.toString()
            var nombreOrden = etNombreOrden.text.toString()
            val intent = Intent(this@MenuOrdenarActivity, MenuActivity::class.java)
            intent.putExtra("numeroMesa", numeroMesa)
            intent.putExtra("nombreOrden", nombreOrden)
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}