package mx.edu.potros.foodorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest

class Cuenta : AppCompatActivity() {
    var mesa: Mesa? = null
    var platillos = ArrayList<Platillo>()
    var adapter: CuentaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        var numMesa: String? = ""
        val tvNumMesa: TextView = findViewById(R.id.tv_num_mesa)
        val gridView: GridView = findViewById(R.id.cuentas)
        val btnAdd: ImageView = findViewById(R.id.btn_add)
        val btnEnviarPedido: Button = findViewById(R.id.btn_cuenta_ordenar)
        val bundle = intent.extras

        if (bundle != null) {
            numMesa = bundle.getString("mesa")
        }

        getMesaYPlatillos(numMesa ?: "", gridView)
        tvNumMesa.text = "Mesa ${numMesa ?: ""}"

        btnAdd.setOnClickListener {
            val intent = Intent(this, MenuOrdenar::class.java)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", "una")
            startActivity(intent)
            finish()
        }

        btnEnviarPedido.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de enviar ese pedido?")

            builder.setPositiveButton("Si") { dialog, which ->
                borrarMesa(numMesa ?: "")
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun getMesaYPlatillos(numMesa: String, gridView: GridView) {
        lifecycleScope.launch {
            try {
                // Get mesa data
                mesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numMesa) }
                    .decodeSingle<Mesa>()

                // Get platillos data
                val platillosResult = SupabaseClient.client.postgrest["platillos"]
                    .select()
                    .decodeList<Platillo>()
                
                platillos.addAll(platillosResult)

                // Update UI on main thread
                runOnUiThread {
                    adapter = CuentaAdapter(mesa!!.cuentas as ArrayList<CuentaBD>?, 
                                         platillos, 
                                         this@Cuenta, 
                                         mesa!!.nombre)
                    gridView.adapter = adapter
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Cuenta, 
                        "Error cargando datos: ${e.message}", 
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun borrarMesa(numMesa: String) {
        lifecycleScope.launch {
            try {
                // Delete mesa from database
                SupabaseClient.client.postgrest["mesas"]
                    .delete { eq("nombre", numMesa) }

                runOnUiThread {
                    Toast.makeText(this@Cuenta, 
                        "Las cuentas de la mesa han sido enviadas a caja", 
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Cuenta, MenuPrincipal::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Cuenta,
                        "Error al borrar mesa: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    class CuentaAdapter : BaseAdapter {
        var cuentas: ArrayList<CuentaBD>? = null
        var platillos = ArrayList<Platillo>()
        var context: Context? = null
        var numMesa: String? = null

        constructor(cuentas: ArrayList<CuentaBD>?, 
                   platillos: ArrayList<Platillo>, 
                   context: Context, 
                   numMesa: String?): super() {
            this.cuentas = cuentas
            this.platillos = platillos
            this.context = context
            this.numMesa = numMesa
        }

        override fun getCount(): Int = cuentas?.size ?: 0
        override fun getItem(position: Int): Any = cuentas!![position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val cuenta = cuentas!![position]
            val inflador = LayoutInflater.from(context)
            val vista = inflador.inflate(R.layout.cuentas, null)

            // ... rest of the getView implementation remains the same
            // (keeping the existing UI setup code)

            return vista
        }
    }
}