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
import mx.edu.potros.foodorder.Activities.CatalogoActivity
import mx.edu.potros.foodorder.Activities.SeguirAgregandoActivity

class EspecificacionBoneless : AppCompatActivity() {

    //obtener mesa de la base de datos
    //private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_boneless)

        var nombreCuenta: String? = ""
        var numMesa: String? = ""
        var numCuentas: String? = ""
        var cuentaID: String? = ""
        val btnAgregar: Button = findViewById(R.id.btn_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_back)
        val tvDescripcion: TextView = findViewById(R.id.platillo_descripcion)

        val bundle = intent.extras

        if (bundle != null) {
            nombreCuenta = bundle.getString("cuenta")
            numMesa = bundle.getString("mesa")
            numCuentas = bundle.getString("numCuentas")
            cuentaID = bundle.getString("cuentaID")
            tvDescripcion.setText(bundle.getString("descripcion"))
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                lifecycleScope.launch {
                    agregarBoneless(nombreCuenta, numMesa, numCuentas, cuentaID)
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
            var intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", "entradas")
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("cuentaID", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun agregarBoneless(nombreCuenta: String?, numMesa: String?, numCuentas: String?, cuentaID: String?) {
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

        if (contadorSalsa < 1 || contadorSalsa > 1) {
            Toast.makeText(this, "Escoja una salsa", Toast.LENGTH_SHORT).show()
            return
        }

        //val platilloID = Appwrite.database.agregarPlatillo(platillo)

        var intent = Intent(this@EspecificacionBoneless, SeguirAgregandoActivity::class.java)
        intent.putExtra("mesa", numMesa)
        intent.putExtra("cuenta", nombreCuenta)
        intent.putExtra("numCuentas", numCuentas)
        intent.putExtra("cuentaID", cuentaID)
        startActivity(intent)
        finish()

        //añadir platillo a cuenta
        /*mesaRef.orderByChild("nombre").equalTo(numMesa).addListenerForSingleValueEvent(object: ValueEventListener {
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

                        var intent = Intent(this@EspecificacionBoneless, SeguirAgregando::class.java)
                        intent.putExtra("mesa", numMesa)
                        intent.putExtra("cuenta", nombreCuenta)
                        intent.putExtra("numCuentas", numCuentas)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }
}