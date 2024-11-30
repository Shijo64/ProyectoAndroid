package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import java.lang.reflect.Array.set

class CobraCuenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobra_cuenta)

        var numMesa: String? = ""
        var nombreCuenta: String? = ""
        val btnAceptar: Button = findViewById(R.id.btn_aceptar)
        val btnCancelar: Button = findViewById(R.id.btn_cancelar)
        val bundle = intent.extras

        if (bundle != null) {
            numMesa = bundle.getString("mesa")
            nombreCuenta = bundle.getString("cuenta")
        }

        btnAceptar.setOnClickListener {
            borrarCuenta(numMesa, nombreCuenta)
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun borrarCuenta(numMesa: String?, nombreCuenta: String?) {
        lifecycleScope.launch {
            try {
                // First get the mesa record
                val mesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numMesa) }
                    .decodeSingle<Mesa>()

                // Remove the specified cuenta from the mesa
                val cuentaBorrar = CuentaBD(nombreCuenta)
                if (mesa.cuentas!!.remove(cuentaBorrar)) {
                    if (mesa.cuentas!!.isEmpty()) {
                        // If no more cuentas, delete the mesa
                        SupabaseClient.client.postgrest["mesas"]
                            .delete { eq("nombre", numMesa) }
                    } else {
                        // Update the mesa with remaining cuentas
                        SupabaseClient.client.postgrest["mesas"]
                            .update({ 
                                set("cuentas", mesa.cuentas)
                            }) {
                                eq("nombre", numMesa)
                            }
                    }

                    runOnUiThread {
                        Toast.makeText(
                            this@CobraCuenta, 
                            "La cuenta ha sido enviada a caja", 
                            Toast.LENGTH_LONG
                        ).show()
                        
                        val intent = Intent(this@CobraCuenta, MenuPrincipal::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@CobraCuenta,
                        "Error al procesar la cuenta: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

private fun Any.eq(column: String, value: String?) {
    TODO("Not yet implemented")
}

private fun Any.remove(cuentaBorrar: CuentaBD): Boolean {
    TODO("Not yet implemented")
}
