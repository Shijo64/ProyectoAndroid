package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.Models.Cuenta
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.R.id.spinner_numero_mesa

class MenuOrdenar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_ordenar)

        val numeroMesa: Spinner = findViewById(spinner_numero_mesa)
        val nombreOrden: EditText = findViewById(R.id.et_nombre_orden)
        val btnOrdenar: Button = findViewById(R.id.btn_ordenar)
        val btnRegresar: Button = findViewById(R.id.btn_regresar)

        btnOrdenar.setOnClickListener {
            val intent = Intent(this@MenuOrdenar, Menu::class.java)
            intent.putExtra("mesa", numeroMesa.selectedItem.toString())
            intent.putExtra("nombreOrden", nombreOrden.text.toString())
            startActivity(intent)
            finish()
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, NuevaMesa::class.java)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun crearCuenta(numeroMesa: String?, numCuentas: String?, mesaID: String?) {
        /*var etNombreCuenta: EditText = findViewById(R.id.et_nombre_orden)

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
        finish()*/
    }
}