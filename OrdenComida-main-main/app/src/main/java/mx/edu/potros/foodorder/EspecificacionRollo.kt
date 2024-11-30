package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest

class EspecificacionRollo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_rollo)

        var nombreCuenta: String? = ""
        var numMesa: String? = ""
        var numCuentas: String? = ""
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        val tvDescripcion: TextView = findViewById(R.id.tv_especificacionDescripcion)
        var ivRollo: ImageView = findViewById(R.id.iv_rollo)
        var tvNombre: TextView = findViewById(R.id.tv_nombreEspecificacionRollo)
        var tvPrecio: TextView = findViewById(R.id.tv_especificacionPrecio)

        val bundle = intent.extras

        if (bundle != null) {
            ivRollo.setImageResource(bundle.getInt("imagen"))
            tvNombre.setText(bundle.getString("nombre"))
            tvPrecio.setText("$${bundle.getDouble("precio")}")
            tvDescripcion.setText(bundle.getString("descripcion"))
            nombreCuenta = bundle.getString("cuenta")
            numMesa = bundle.getString("mesa")
            numCuentas = bundle.getString("numCuentas")
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                agregarRollo(tvNombre.text.toString(), nombreCuenta, numMesa, numCuentas)
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
            intent.putExtra("tipo", "rollos")
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarRollo(nombrePlatillo: String?, nombreCuenta: String?, numMesa: String?, numCuentas: String?) {
        val empanizado: CheckBox = findViewById(R.id.checkBox)
        val natural: CheckBox = findViewById(R.id.checkBox2)
        val mitad: CheckBox = findViewById(R.id.checkBox3)
        val conAlga: CheckBox = findViewById(R.id.checkBox4)
        val sinAlga: CheckBox = findViewById(R.id.checkBox5)
        val carne: CheckBox = findViewById(R.id.checkBox111)
        val camaron: CheckBox = findViewById(R.id.checkBox122)
        val pollo: CheckBox = findViewById(R.id.checkBox6)
        val tocino: CheckBox = findViewById(R.id.checkBox7)
        val surimi: CheckBox = findViewById(R.id.checkBox8)
        val tampico: CheckBox = findViewById(R.id.checkBox9)
        val gratinado: CheckBox = findViewById(R.id.checkBox10)

        val arrozChecked = listOf(empanizado, natural, mitad)
        val algaChecked = listOf(conAlga, sinAlga)
        val ingredienteExtraChecked = listOf(carne, camaron, pollo, tocino, surimi, tampico, gratinado)

        var contadorArroz = 0
        var contadorAlga = 0
        var contadorIngrediente = 0

        var arroz = ""
        var alga = ""
        var ingredienteExtra = ""

        // Validate arroz selection
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

        // Validate alga selection  
        for (aC in algaChecked) {
            if (aC.isChecked) {
                alga = "," + aC.text.toString()
                contadorAlga++
            }
        }
        if (contadorAlga != 1) {
            Toast.makeText(this, "Seleccione como quiere su alga", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate ingrediente extra
        for (iC in ingredienteExtraChecked) {
            if (iC.isChecked) {
                contadorIngrediente++
                when (iC) {
                    carne -> ingredienteExtra = ",CarneEx"
                    camaron -> ingredienteExtra = ",CamarónEx" 
                    pollo -> ingredienteExtra = ",PolloEx"
                    tocino -> ingredienteExtra = ",TocinoEx"
                    surimi -> ingredienteExtra = ",SurimiEx"
                    tampico -> ingredienteExtra = ",TampicoEx"
                    gratinado -> ingredienteExtra = ",GratinadoEx"
                }
            }
        }
        if (contadorIngrediente > 1) {
            Toast.makeText(this, "Solo seleccione un ingrediente extra", Toast.LENGTH_SHORT).show()
            return
        }

        val extras = arroz + alga + ingredienteExtra
        val platillo = PlatilloCuenta(1, extras, nombrePlatillo)

        lifecycleScope.launch {
            try {
                if (numMesa == null || nombreCuenta == null) {
                    Toast.makeText(this@EspecificacionRollo, "Datos de mesa o cuenta faltantes.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val mesa = SupabaseClient.client.postgrest["mesas"]
                    .select { eq("nombre", numMesa) }
                    .decodeSingle<Mesa>()

                val updatedCuentas = mesa.cuentas?.map { cuenta ->
                    if (cuenta.nombre == nombreCuenta) {
                        val updatedPlatillos = cuenta.platillos?.toMutableList() ?: mutableListOf()
                        updatedPlatillos.add(platillo)
                        cuenta.copy(platillos = updatedPlatillos)
                    } else {
                        cuenta
                    }
                } ?: emptyList()

                SupabaseClient.client.postgrest["mesas"]
                    .update({
                        set("cuentas", updatedCuentas)
                    }) {
                        eq("nombre", numMesa)
                    }

                runOnUiThread {
                    val intent = Intent(this@EspecificacionRollo, SeguirAgregando::class.java)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@EspecificacionRollo,
                        "Error al agregar platillo: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}