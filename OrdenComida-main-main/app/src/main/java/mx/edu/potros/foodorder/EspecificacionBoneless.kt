package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest

class EspecificacionBoneless : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_boneless)

        var nombreCuenta: String = ""
        var numMesa: String = ""
        var numCuentas: String = ""
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        val tvDescripcion: TextView = findViewById(R.id.tv_descripcion)

        val bundle = intent.extras

        if (bundle != null) {
            nombreCuenta = bundle.getString("cuenta") ?: ""
            numMesa = bundle.getString("mesa") ?: ""
            numCuentas = bundle.getString("numCuentas") ?: ""
            tvDescripcion.setText(bundle.getString("descripcion"))
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                agregarBoneless(nombreCuenta, numMesa, numCuentas)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "entradas")
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarBoneless(nombreCuenta: String, numMesa: String, numCuentas: String) {
        val salsaBbq: CheckBox = findViewById(R.id.checkBox)
        val salsaBufalo: CheckBox = findViewById(R.id.checkBox2)
        val salsaMixta: CheckBox = findViewById(R.id.checkBox3)

        val salsaChecked = listOf(salsaBbq, salsaBufalo, salsaMixta)
        var contadorSalsa = 0
        var salsaSeleccionada = ""

        for (sC in salsaChecked) {
            if (sC.isChecked) {
                salsaSeleccionada = sC.text.toString()
                contadorSalsa++
            }
        }

        if (contadorSalsa != 1) {
            Toast.makeText(this, "Escoja una salsa", Toast.LENGTH_SHORT).show()
            return
        }

        val platillo = PlatilloCuenta(1, salsaSeleccionada, "Boneless")

        lifecycleScope.launch {
            try {
                // Get current mesa
                val mesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numMesa) }
                    .decodeSingle<Mesa>()

                // Create new lists to modify
                val updatedCuentas = mesa.cuentas?.toMutableList() ?: mutableListOf()

                // Find the cuenta to update
                val cuentaIndex = updatedCuentas.indexOfFirst { it.nombre == nombreCuenta }
                if (cuentaIndex != -1) {
                    val cuenta = updatedCuentas[cuentaIndex]
                    val updatedPlatillos = cuenta.platillos?.toMutableList() ?: mutableListOf()
                    updatedPlatillos.add(platillo)

                    // Create new cuenta with updated platillos
                    val updatedCuenta = cuenta.copy(platillos = updatedPlatillos)
                    updatedCuentas[cuentaIndex] = updatedCuenta

                    // Update mesa in database
                    SupabaseClient.client.postgrest["mesas"]
                        .update({
                            set("cuentas", updatedCuentas)
                        }) {
                            eq("nombre", numMesa)
                        }

                    runOnUiThread {
                        val intent = Intent(this@EspecificacionBoneless, SeguirAgregando::class.java)
                        intent.putExtra("mesa", numMesa)
                        intent.putExtra("cuenta", nombreCuenta)
                        intent.putExtra("numCuentas", numCuentas)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@EspecificacionBoneless,
                            "No se encontró la cuenta especificada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@EspecificacionBoneless,
                        "Error al agregar platillo: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}