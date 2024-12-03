package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.Models.Cuenta
import mx.edu.potros.foodorder.R

class MenuOrdenar : AppCompatActivity() {

    //private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_ordenar)

        var numeroMesa: String? = ""
        var numCuentas: String? = ""
        var mesaID : String? = ""
        val tvNumeroMesa: TextView = findViewById(R.id.tv_numeroMesa)
        val btnOrdenar: Button = findViewById(R.id.btn_ordenar)
        val btnRegresar: Button = findViewById(R.id.btn_regresar)

        val bundle = intent.extras

        if (bundle != null) {
            numeroMesa = bundle.getString("mesa")
            mesaID = bundle.getString("mesaID")
            numCuentas = bundle.getString("numCuenta")
            tvNumeroMesa.setText("Mesa " + numeroMesa)
        }

        btnOrdenar.setOnClickListener {
            lifecycleScope.launch {
                crearCuenta(numeroMesa, numCuentas, mesaID)
            }
        }

        btnRegresar.setOnClickListener {
            var intent = Intent(this, NuevaMesa::class.java)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun crearCuenta(numeroMesa: String?, numCuentas: String?, mesaID: String?) {
        var etNombreCuenta: EditText = findViewById(R.id.et_nombre_cuenta)

        if (etNombreCuenta.text.isBlank()) {
            Toast.makeText(this, "El nombre de la cuenta no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        var nombreCuenta: String = etNombreCuenta.text.toString().trim()
        val cuenta = Cuenta(
            nombre = nombreCuenta,
            mesaID = mesaID.toString(),
            numeroMesa = numeroMesa.toString(),
            platillos = emptyList()
        )
        val cuentaID = Appwrite.database.crearCuenta(cuenta)

        Toast.makeText(this@MenuOrdenar, "Cuenta agregada exitosamente", Toast.LENGTH_SHORT).show()

        var intent = Intent(this@MenuOrdenar, Menu::class.java)
        intent.putExtra("mesa", numeroMesa)
        intent.putExtra("numCuentas", numCuentas)
        intent.putExtra("cuenta", nombreCuenta)
        intent.putExtra("cuentaID", cuentaID)
        startActivity(intent)
        finish()


        /*mesaRef.orderByChild("nombre").equalTo(mesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    val mesaExistente = s.getValue(Mesa::class.java)

                    if (mesaExistente != null) {
                        for (c in mesaExistente.cuentas!!) {
                            if (c.nombre == nombreCuenta) {
                                Toast.makeText(this@MenuOrdenar, "Una cuenta con ese nombre ya existe", Toast.LENGTH_SHORT).show()
                                return
                            }
                        }

                        val cuenta = CuentaBD(nombreCuenta)
                        mesaExistente.cuentas?.add(cuenta)
                        s.ref.setValue(mesaExistente)
                        Toast.makeText(this@MenuOrdenar, "Cuenta agregada exitosamente", Toast.LENGTH_SHORT).show()

                        var intent = Intent(this@MenuOrdenar, Menu::class.java)
                        intent.putExtra("mesa", mesa)
                        intent.putExtra("numCuentas", numCuentas)
                        intent.putExtra("cuenta", nombreCuenta)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }
}