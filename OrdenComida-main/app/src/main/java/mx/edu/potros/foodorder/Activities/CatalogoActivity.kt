package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mx.edu.potros.foodorder.Adapters.PlatillosAdapter
import mx.edu.potros.foodorder.Enums.PlatilloEnum
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.CatalogoViewModel
import mx.edu.potros.foodorder.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding
    var platillos = ArrayList<Platillo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: CatalogoViewModel by viewModels()
        var tipo: String? = intent.getStringExtra("tipo")
        var nombreOrden: String? = intent.getStringExtra("nombreOrden")
        var numeroMesa: String? = intent.getStringExtra("numeroMesa")


        val tvMenuOption: TextView = findViewById(R.id.tv_menu_option)
        val gridview: GridView = findViewById(R.id.gridView)
        val adapter = PlatillosAdapter(platillos,
            tipo.toString(), numeroMesa.toString(), nombreOrden.toString(), this)
        gridview.adapter = adapter

        val backButton: Button = findViewById(R.id.btn_catalagoPlatillo_regresar)

        viewModel.cargarPlatillos(tipo)
        viewModel.platillos.observe(this, Observer { platillos ->
            gridview.adapter = PlatillosAdapter(platillos, tipo.toString(), numeroMesa.toString(), nombreOrden.toString(), this)
            adapter.notifyDataSetChanged()
        })

       when (tipo) {
            PlatilloEnum.ENTRADAS.name -> {
                tvMenuOption.setText("Entradas")
            }

           PlatilloEnum.ROLLOS.name -> {
                tvMenuOption.setText("Rollos")
            }

           PlatilloEnum.PLATILLOS.name -> {
                tvMenuOption.setText("Platillos")
            }

           PlatilloEnum.EXTRAS.name -> {
                tvMenuOption.setText("Extras")
            }

           PlatilloEnum.POSTRES.name -> {
                tvMenuOption.setText("Postres")
            }

           PlatilloEnum.BEBIDAS.name -> {
                tvMenuOption.setText("Bebidas")
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}