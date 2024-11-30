package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest

class NuevaMesa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mesa)

        val btnRegresar: Button = findViewById(R.id.btn_regresar)
        val ivUnaCuenta: ImageView = findViewById(R.id.iv_cuenta)
        val ivVariasCuentas: ImageView = findViewById(R.id.iv_varias_cuentas)

        ivUnaCuenta.setOnClickListener {
            creaMesa("una")
        }

        ivVariasCuentas.setOnClickListener {
            creaMesa("varias")
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun creaMesa(numCuentas: String) {
        val numMesa: Spinner = findViewById(R.id.spinner_numero_mesa)
        val numeroMesa: String = numMesa.selectedItem.toString()

        lifecycleScope.launch {
            try {
                // Check if mesa already exists
                val existingMesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numeroMesa) }
                    .single<Mesa>()

                if (existingMesa != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@NuevaMesa,
                            "Una mesa con ese número ya existe",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // Create new mesa
                val mesa = Mesa(numeroMesa)
                SupabaseClient.client.postgrest["mesas"]
                    .insert(mesa)

                runOnUiThread {
                    Toast.makeText(
                        this@NuevaMesa,
                        "Mesa agregada exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@NuevaMesa, MenuOrdenar::class.java)
                    intent.putExtra("mesa", numeroMesa)
                    intent.putExtra("numCuentas", numCuentas)
                    startActivity(intent)
                    finish()
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@NuevaMesa,
                        "Error al crear mesa: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

private fun <T> Any.single(): Any {
    TODO("Not yet implemented")
}
