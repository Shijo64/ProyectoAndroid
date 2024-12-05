package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.edu.potros.foodorder.Activities.HomeActivity

class NuevaMesa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mesa)

        val btnRegresar: Button = findViewById(R.id.btn_regresar)

        /*ivUnaCuenta.setOnClickListener {
            lifecycleScope.launch {
                creaMesa("una")
            }
        }

        ivVariasCuentas.setOnClickListener {
            lifecycleScope.launch {
                creaMesa("varias")
            }
        }*/

        btnRegresar.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun creaMesa(numCuentas: String) {
        /*var numMesa: Spinner = findViewById(R.id.spinner_numero_mesa)


        var numeroMesa: String = numMesa.selectedItem.toString()

        //val mesaID = Appwrite.database.addMesa(numeroMesa.toInt())
        //Toast.makeText(this@NuevaMesa, "Mesa agregada exitosamente", Toast.LENGTH_SHORT).show()

        var intent = Intent(this@NuevaMesa, MenuOrdenar::class.java)
        intent.putExtra("mesa", numeroMesa)
        intent.putExtra("mesaID", mesaID)
        intent.putExtra("numCuentas", numCuentas)
        startActivity(intent)
        finish()*/
    }
}