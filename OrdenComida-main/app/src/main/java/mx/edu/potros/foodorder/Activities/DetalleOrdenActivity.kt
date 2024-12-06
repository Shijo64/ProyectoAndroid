package mx.edu.potros.foodorder.Activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import mx.edu.potros.foodorder.Adapters.DetalleOrdenAdapter
import mx.edu.potros.foodorder.Adapters.OrdenesAdapter
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Models.Mesa
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.DetalleOrdenViewModel

class DetalleOrdenActivity : AppCompatActivity() {
    private val viewModel: DetalleOrdenViewModel by viewModels()

    var mesa: Mesa? = null
    var total = 0.0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_orden)

        val tvNumeroMesa: TextView = findViewById(R.id.tv_num_mesa)
        val btnEnviarPedido: Button = findViewById(R.id.btn_enviar_orden)
        val totalOrden: TextView = findViewById(R.id.total_orden)
        val gridView: GridView = findViewById(R.id.platillos_orden)
        val btnAtras: Button = findViewById(R.id.btn_atras)

        val bundle = intent.extras
        var numeroMesa: String? = ""
        var nombreOrden: String? = ""

        if (bundle != null) {
            numeroMesa = bundle.getString("numeroMesa")
            nombreOrden = bundle.getString("nombreOrden")
        }

        tvNumeroMesa.setText("Mesa " + numeroMesa)

        viewModel.getPlatillosByNombreOrden(nombreOrden.toString())
        val adapter = OrdenesAdapter(this,  emptyList())

        viewModel.platillos.observe(this, { platillos ->
                viewModel.getTotalOrden(platillos)
                gridView.adapter = DetalleOrdenAdapter(platillos, this)
                adapter.notifyDataSetChanged()
        })

        viewModel.totalOrden.observe(this, { precio ->
            total += precio
            val totalString = total.toString()
            totalOrden.text = "Total: $totalString"
        })

        viewModel.addOrdenResult.observe(this, { result ->
            if (result != null) {
                Toast.makeText(this, "Orden enviada correctamente", Toast.LENGTH_SHORT).show()
                for (platillo in viewModel.platillos.value!!) {
                    viewModel.enviarPlatillo(platillo)
                }
            } else {
                Toast.makeText(this, "Error al enviar la orden", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.addPlatillosResult.observe(this, { result ->
            if (result != null) {
                Toast.makeText(this, "Platillos enviados correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        btnEnviarPedido.setOnClickListener {
            viewModel.enviarOrden(numeroMesa.toString(), nombreOrden.toString())
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }
}