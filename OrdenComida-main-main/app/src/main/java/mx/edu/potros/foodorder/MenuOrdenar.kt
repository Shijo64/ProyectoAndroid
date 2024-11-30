package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.*

class MenuOrdenar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_ordenar)

        var numeroMesa: String? = ""
        var numCuentas: String? = ""
        val tvNumeroMesa: TextView = findViewById(R.id.tv_numeroMesa)
        val btnOrdenar: Button = findViewById(R.id.btn_ordenar)
        val btnRegresar: Button = findViewById(R.id.btn_regresar)

        val bundle = intent.extras

        if (bundle != null) {
            numeroMesa = bundle.getString("mesa")
            numCuentas = bundle.getString("numCuentas")
            tvNumeroMesa.setText("Mesa " + numeroMesa)
        }

        btnOrdenar.setOnClickListener {
            crearCuenta(numeroMesa, numCuentas)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, NuevaMesa::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun crearCuenta(mesa: String?, numCuentas: String?) {
        val etNombreCuenta: EditText = findViewById(R.id.et_nombre_cuenta)
        val nombreCuenta = etNombreCuenta.text.toString().trim()

        if (nombreCuenta.isBlank()) {
            Toast.makeText(this, "El nombre de la cuenta no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        if (mesa == null) {
            Toast.makeText(this, "Error: Número de mesa inválido", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                // Get current mesa
                val mesaExistente = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", mesa) }
                    .decodeSingle<Mesa>()

                // Check if cuenta already exists
                if (mesaExistente.cuentas?.any { it.nombre == nombreCuenta } == true) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuOrdenar,
                            "Una cuenta con ese nombre ya existe",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // Create new cuenta with empty platillos list
                val nuevaCuenta = CuentaBD(
                    nombre = nombreCuenta,
                    platillos = mutableListOf()
                )

                // Update mesa's cuentas list
                val updatedCuentas = (mesaExistente.cuentas ?: mutableListOf()).toMutableList()
                updatedCuentas.add(nuevaCuenta)

                // Update mesa in database
                SupabaseClient.client.postgrest["mesas"]
                    .update({
                        set("cuentas", updatedCuentas)
                    }) { 
                        eq("nombre", mesa)
                    }

                runOnUiThread {
                    Toast.makeText(
                        this@MenuOrdenar,
                        "Cuenta agregada exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@MenuOrdenar, Menu::class.java)
                    intent.putExtra("mesa", mesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    startActivity(intent)
                    finish()
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@MenuOrdenar,
                        "Error al crear cuenta: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}