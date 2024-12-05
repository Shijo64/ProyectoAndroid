package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.edu.potros.foodorder.Activities.HomeActivity

class CobraCuenta : AppCompatActivity() {

    //obtener mesa de firebase
    //private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

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
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun borrarCuenta(numMesa: String?, nombreCuenta: String?) {
        //borrar cuenta de firebase
        /*mesaRef.orderByChild("nombre").equalTo(numMesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    val mesa = s.getValue(Mesa::class.java)
                    val cuentaBorrar = CuentaBD(nombreCuenta)

                    if (mesa!!.cuentas!!.remove(cuentaBorrar)) {
                        if (mesa.cuentas!!.size == 0) {
                            s.ref.removeValue()
                        } else {
                            s.ref.setValue(mesa)
                        }

                        Toast.makeText(this@CobraCuenta, "La cuenta ha sido enviada a caja", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CobraCuenta, MenuPrincipal::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }
}