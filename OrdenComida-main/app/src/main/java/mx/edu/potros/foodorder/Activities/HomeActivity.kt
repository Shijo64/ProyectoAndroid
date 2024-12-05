package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.edu.potros.foodorder.Adapters.OrdenesAdapter
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.HomeViewModel
import mx.edu.potros.foodorder.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCerrarSesion: Button = findViewById(R.id.btn_cerrarSesion)
        val btnOrdenar: FloatingActionButton = findViewById(R.id.btn_ordenar)
        val mesasList: ListView = findViewById(R.id.list_view)
        val adapter = OrdenesAdapter(this,  emptyList())
        mesasList.adapter = adapter

        viewModel.getOrdenes()
        viewModel.ordenes.observe(this, Observer { ordenes ->
            mesasList.adapter = OrdenesAdapter(this, ordenes)
            adapter.notifyDataSetChanged()
        })

        btnCerrarSesion.setOnClickListener{
            var intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }

        btnOrdenar.setOnClickListener{
            var intent = Intent(this, MenuOrdenarActivity::class.java)
            startActivity(intent)
        }
    }
}