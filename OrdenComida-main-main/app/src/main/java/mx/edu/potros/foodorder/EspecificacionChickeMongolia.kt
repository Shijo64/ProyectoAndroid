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

class EspecificacionChickeMongolia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ... rest of onCreate remains the same ...
    }

    private fun agregarChickenMongolia(nombreCuenta: String?, numMesa: String?, numCuentas: String?) {
        val arrozBlanco: CheckBox = findViewById(R.id.checkBox)
        val arrozFrito: CheckBox = findViewById(R.id.checkBox2)
        val verdurasVapor: CheckBox = findViewById(R.id.checkBox3)
        val verdurasSalteadas: CheckBox = findViewById(R.id.checkBox4)

        val arrozChecked = listOf(arrozBlanco, arrozFrito)
        val verdurasChecked = listOf(verdurasVapor, verdurasSalteadas)

        var contadorArroz = 0
        var contadorVerduras = 0
        var arroz = ""
        var verduras = ""

        for (aC in arrozChecked) {
            if (aC.isChecked) {
                arroz = aC.text.toString()
                contadorArroz++
            }
        }

        if (contadorArroz != 1) {
            Toast.makeText(this, "Seleccione un tipo de arroz", Toast.LENGTH_SHORT).show()
            return
        }

        for (vC in verdurasChecked) {
            if (vC.isChecked) {
                verduras = "," + vC.text.toString()
                contadorVerduras++
            }
        }

        if (contadorVerduras != 1) {
            Toast.makeText(this, "Seleccione un tipo de verduras", Toast.LENGTH_SHORT).show()
            return
        }

        val extras = arroz + verduras
        val platillo = PlatilloCuenta(1, extras, "Chicken Mongolia")

        lifecycleScope.launch {
            try {
                if (numMesa == null) {
                    throw IllegalArgumentException("Mesa number cannot be null")
                }

                // Get current mesa
                val mesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numMesa as String) }
                    .decodeSingle<Mesa>()

                // Create new lists with updated data
                val updatedCuentas = mesa.cuentas?.map { cuenta ->
                    if (cuenta.nombre == nombreCuenta) {
                        // Create new cuenta with updated platillos list
                        val updatedPlatillos = (cuenta.platillos ?: emptyList()) + platillo
                        cuenta.copy(platillos = updatedPlatillos)
                    } else {
                        cuenta
                    }
                } ?: emptyList()

                // Update mesa in database with the new cuentas list
                SupabaseClient.client.postgrest["mesas"]
                    .update({
                        set("cuentas", updatedCuentas)
                    }) {
                        eq("nombre", numMesa as String)
                    }

                runOnUiThread {
                    val intent = Intent(this@EspecificacionChickeMongolia, SeguirAgregando::class.java)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("numCuentas", numCuentas)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@EspecificacionChickeMongolia,
                        "Error al agregar platillo: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}