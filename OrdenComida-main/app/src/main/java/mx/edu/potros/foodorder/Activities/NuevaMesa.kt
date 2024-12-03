package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.R

class NuevaMesa : AppCompatActivity() {

    //private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mesa)

        val btnRegresar: Button = findViewById(R.id.btn_regresar)
        val ivUnaCuenta: ImageView = findViewById(R.id.iv_cuenta)
        val ivVariasCuentas: ImageView = findViewById(R.id.iv_varias_cuentas)

        ivUnaCuenta.setOnClickListener {
            lifecycleScope.launch {
                creaMesa("una")
            }
        }

        ivVariasCuentas.setOnClickListener {
            lifecycleScope.launch {
                creaMesa("varias")
            }
        }

        btnRegresar.setOnClickListener {
            var intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun creaMesa(numCuentas: String) {
        var numMesa: Spinner = findViewById(R.id.spinner_numero_mesa)


        var numeroMesa: String = numMesa.selectedItem.toString()

        val mesaID = Appwrite.database.addMesa(numeroMesa.toInt())
        Toast.makeText(this@NuevaMesa, "Mesa agregada exitosamente", Toast.LENGTH_SHORT).show()

        var intent = Intent(this@NuevaMesa, MenuOrdenar::class.java)
        intent.putExtra("mesa", numeroMesa)
        intent.putExtra("mesaID", mesaID)
        intent.putExtra("numCuentas", numCuentas)
        startActivity(intent)
        finish()
        /*mesaRef.orderByChild("nombre").equalTo(numeroMesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    val mesaExistente = s.getValue(Mesa::class.java)

                    if (mesaExistente != null) {
                        Toast.makeText(this@NuevaMesa, "Una mesa con ese número ya existe", Toast.LENGTH_SHORT).show()
                        return
                    }
                }

                val mesa = Mesa(numeroMesa)

                mesaRef.push().setValue(mesa)
                Toast.makeText(this@NuevaMesa, "Mesa agregada exitosamente", Toast.LENGTH_SHORT).show()

                var intent = Intent(this@NuevaMesa, MenuOrdenar::class.java)
                intent.putExtra("mesa", numeroMesa)
                intent.putExtra("numCuentas", numCuentas)
                startActivity(intent)
                finish()
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }
}