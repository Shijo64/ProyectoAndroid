package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EspecificacionChickeMongolia : AppCompatActivity() {

    private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_chicke_mongolia)

        var numMesa: String? = ""
        var nombreCuenta: String? = ""
        var numCuentas: String? = ""
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        var tvDescripcion: TextView = findViewById(R.id.tv_especificacionDescripcion)

        val bundle = intent.extras

        if (bundle != null) {
            tvDescripcion.setText(bundle.getString("descripcion"))
            numMesa = bundle.getString("mesa")
            nombreCuenta = bundle.getString("cuenta")
            numCuentas = bundle.getString("numCuentas")
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                agregarChickenMongolia(nombreCuenta, numMesa, numCuentas)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }

        btnRegresar.setOnClickListener {
            var intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "platillos")
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
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

        if (contadorArroz < 1 || contadorArroz > 1) {
            Toast.makeText(this, "Seleccione un tipo de arroz", Toast.LENGTH_SHORT).show()
            return
        }

        for (vC in verdurasChecked) {
            if (vC.isChecked) {
                verduras = "," + vC.text.toString()
                contadorVerduras++
            }
        }

        if (contadorVerduras < 1 || contadorVerduras > 1) {
            Toast.makeText(this, "Seleccione un tipo de verduras", Toast.LENGTH_SHORT).show()
            return
        }

        var extras = arroz + verduras
        val platillo = PlatilloCuenta(1, extras, "Chicken Mongolia")

        mesaRef.orderByChild("nombre").equalTo(numMesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    var mesaExistente = s.getValue(Mesa::class.java)

                    if (mesaExistente != null) {
                        for (c in mesaExistente.cuentas!!) {
                            if (c.nombre == nombreCuenta) {
                                c.platillos?.add(platillo)
                                break
                            }
                        }

                        s.ref.setValue(mesaExistente)

                        var intent = Intent(this@EspecificacionChickeMongolia, SeguirAgregando::class.java)
                        intent.putExtra("cuenta", nombreCuenta)
                        intent.putExtra("mesa", numMesa)
                        intent.putExtra("numCuentas", numCuentas)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}