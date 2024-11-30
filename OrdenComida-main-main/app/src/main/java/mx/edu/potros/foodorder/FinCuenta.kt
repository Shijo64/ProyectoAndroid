package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class FinCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fin_cuenta)

        var numCuentas: String? = ""
        var numMesa: String? = ""
        val btnAgregarCuenta: Button = findViewById(R.id.btn_agregar_cuenta)
        val btnIrPedido: Button = findViewById(R.id.btn_ir_pedido)

        val bundle = intent.extras

        if (bundle != null) {
            numCuentas = bundle.getString("numCuentas")
            numMesa = bundle.getString("mesa")
        }

        btnAgregarCuenta.setOnClickListener {
            if (numCuentas == "varias") {
                var intent = Intent(this, MenuOrdenar::class.java)
                intent.putExtra("mesa", numMesa)
                intent.putExtra("numCuentas", numCuentas)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Esta mesa es de solo una cuenta", Toast.LENGTH_SHORT).show()
            }
        }

        btnIrPedido.setOnClickListener {
            var intent = Intent(this, Cuenta::class.java)
            intent.putExtra("mesa", numMesa)
            startActivity(intent)
            finish()
        }
    }
}