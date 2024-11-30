package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest

class EspecificacionGenerica : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_generica)

        var numMesa: String? = ""
        var nombreCuenta: String? = ""
        var numCuentas: String? = ""
        var tipoPlatillo: String? = ""
        val btnMas: Button = findViewById(R.id.btn_especificacion_mas)
        val btnMenos: Button = findViewById(R.id.btn_especificacion_menos)
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        var tvCantidad: TextView = findViewById(R.id.tv_cantidad)
        var ivComida: ImageView = findViewById(R.id.iv_especificar)
        var tvNombre: TextView = findViewById(R.id.tv_nombreComida)
        var tvPrecio: TextView = findViewById(R.id.tv_precio)
        var tvDescripcion: TextView = findViewById(R.id.tv_descripcion)

        val bundle = intent.extras

        if (bundle != null) {
            ivComida.setImageResource(bundle.getInt("imagen"))
            tvNombre.setText(bundle.getString("nombre"))
            tvPrecio.setText("$${bundle.getDouble("precio")}")
            tvDescripcion.setText(bundle.getString("descripcion"))
            numMesa = bundle.getString("mesa")
            nombreCuenta = bundle.getString("cuenta")
            tipoPlatillo = bundle.getString("tipo")
            numCuentas = bundle.getString("numCuentas")
        }

        btnMas.setOnClickListener {
            var txtCantidad: String = tvCantidad.text.toString()
            try {
                var cantidad = Integer.parseInt(txtCantidad)
                cantidad++
                tvCantidad.setText(cantidad.toString())
            } catch (e: Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btnMenos.setOnClickListener {
            var txtCantidad: String = tvCantidad.text.toString()
            try {
                var cantidad = Integer.parseInt(txtCantidad)
                if (cantidad != 1) {
                    cantidad--
                    tvCantidad.setText(cantidad.toString())
                }
            } catch (e: Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                var txtCantidad = tvCantidad.text.toString()
                try {
                    var cantidad = Integer.parseInt(txtCantidad)
                    agregarPlatillo(cantidad, tvNombre.text.toString(), nombreCuenta, numMesa, numCuentas)
                } catch (e: Exception) {
                    System.err.println("Could not parse " + e)
                }
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
            intent.putExtra("tipo", tipoPlatillo)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarPlatillo(cantidad: Int, nombrePlatillo: String?, nombreCuenta: String?, numMesa: String?, numCuentas: String?) {
        val platillo = PlatilloCuenta(cantidad, null, nombrePlatillo)

        lifecycleScope.launch {
            try {
                // Get current mesa
                val mesa = SupabaseClient.client.postgrest["mesas"]
                    .select {
                        if (numMesa != null) {
                            eq("nombre", numMesa)
                        }
                    }
                    .decodeSingle<Mesa>()

                // Find and update the correct cuenta
                mesa.cuentas?.find { it.nombre == nombreCuenta }?.let { cuenta ->
                    cuenta.platillos?.add(platillo)
                    
                    // Update mesa in database
                    SupabaseClient.client.postgrest["mesas"]
                        .update({ 
                            set("cuentas", mesa.cuentas)
                        }) {
                            eq("nombre", numMesa)
                        }

                    runOnUiThread {
                        val intent = Intent(this@EspecificacionGenerica, SeguirAgregando::class.java)
                        intent.putExtra("cuenta", nombreCuenta)
                        intent.putExtra("mesa", numMesa)
                        intent.putExtra("numCuentas", numCuentas)
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@EspecificacionGenerica,
                        "Error al agregar platillo: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

fun Any.set(s: String, cuentas: List<CuentaBD>) {

}

private fun Any.eq(column: String, value: String?) {
    try {
        TODO("Not yet implemented")
    } catch (e: Exception) {
        TODO("Not yet implemented")
    } finally {
    }
}

private fun Any?.add(platillo: PlatilloCuenta) {
    try {
        TODO("Not yet implemented")
    } catch (e: Exception) {
        TODO("Not yet implemented")
    } finally {
    }
}
