package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class NuevaMesa : AppCompatActivity() {

    private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mesa)

        val btnRegresar: Button = findViewById(R.id.btn_regresar)
        val ivUnaCuenta: ImageView = findViewById(R.id.iv_cuenta)
        val ivVariasCuentas: ImageView = findViewById(R.id.iv_varias_cuentas)

        ivUnaCuenta.setOnClickListener {
            creaMesa("una")
        }

        ivVariasCuentas.setOnClickListener {
            creaMesa("varias")
        }

        btnRegresar.setOnClickListener {
            var intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun creaMesa(numCuentas: String) {
        var numMesa: Spinner = findViewById(R.id.spinner_numero_mesa)


        var numeroMesa: String = numMesa.selectedItem.toString()

        mesaRef.orderByChild("nombre").equalTo(numeroMesa).addListenerForSingleValueEvent(object: ValueEventListener {
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
        })
    }
}