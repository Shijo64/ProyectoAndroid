package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.edu.potros.foodorder.Adapters.MesasAdapter
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.HomeViewModel
import mx.edu.potros.foodorder.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: HomeViewModel by viewModels()
        val btnCerrarSesion: Button = findViewById(R.id.btn_cerrarSesion)
        val btnOrdenar: FloatingActionButton = findViewById(R.id.btn_ordenar)
        val mesasList: ListView = findViewById(R.id.list_view)
        val adapter = MesasAdapter(this, viewModel.mesas.value ?: emptyList())
        mesasList.adapter = adapter

        viewModel.cargarMesas()
        viewModel.mesas?.observe(this, Observer { mesas ->
            mesasList.adapter = MesasAdapter(this, mesas)
            adapter.notifyDataSetChanged()
        })

        btnCerrarSesion.setOnClickListener{
            var intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }

        btnOrdenar.setOnClickListener{
            var intent = Intent(this, MenuOrdenar::class.java)
            startActivity(intent)
        }
    }
}