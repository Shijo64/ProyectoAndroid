package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import mx.edu.potros.foodorder.Data.PlatilloOrden
import mx.edu.potros.foodorder.R
import mx.edu.potros.foodorder.ViewModels.SeleccionPlatilloViewModel
import java.lang.Exception

class SeleccionPlatilloActivity : AppCompatActivity() {

    private var numeroMesa: String = ""
    private var nombreOrden: String = ""
    private val viewModel: SeleccionPlatilloViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_platillo)

        val btn_back = findViewById<Button>(R.id.btn_back)
        val btn_agregar = findViewById<Button>(R.id.btn_agregar)
        val btn_mas = findViewById<Button>(R.id.btn_mas)
        val btn_menos = findViewById<Button>(R.id.btn_menos)

        val etPlatilloImagen = findViewById<ImageView>(R.id.platillo_imagen)
        val etPlatilloNombre = findViewById<TextView>(R.id.platillo_nombre)
        val etPrecio = findViewById<TextView>(R.id.platillo_precio)
        val etDescripcion = findViewById<TextView>(R.id.platillo_descripcion)
        val et_Cantidad = findViewById<TextView>(R.id.platillo_cantidad)
        var precio = ""

        var tipoPlatillo: String? = ""

        val bundle = intent.extras

        if (bundle != null) {
            etPlatilloImagen.setImageResource(bundle.getInt("imagen"))
            etPlatilloNombre.setText(bundle.getString("nombre"))
            precio = bundle.getString("precio").toString()
            etPrecio.setText("$${precio}")
            etDescripcion.setText(bundle.getString("descripcion"))
            tipoPlatillo = bundle.getString("tipo")
            numeroMesa = bundle.getString("numeroMesa").toString()
            nombreOrden = bundle.getString("nombreOrden").toString()
        }

        btn_mas.setOnClickListener {
            var txtCantidad: String = et_Cantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                cantidad++
                et_Cantidad.setText(cantidad.toString())
            } catch (e: Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btn_menos.setOnClickListener {
            var txtCantidad: String = et_Cantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                if (cantidad != 1) {
                    cantidad--
                    et_Cantidad.setText(cantidad.toString())
                }
            } catch (e: Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btn_agregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->

                try {
                    val platillo = PlatilloOrden(
                        nombreOrden = nombreOrden.toString(),
                        numeroMesa = numeroMesa,
                        cantidad = et_Cantidad.text.toString(),
                        nombrePlatillo = etPlatilloNombre.text.toString(),
                        precio = precio
                    )

                    agregarPlatillo(platillo)
                } catch (e: Exception) {
                    System.err.println("Could not parse " + e)
                }
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun agregarPlatillo(platillo: PlatilloOrden) {

        viewModel.guardarPlatillo(platillo)

        var intent = Intent(this@SeleccionPlatilloActivity, SeguirAgregandoActivity::class.java)
        intent.putExtra("numeroMesa", numeroMesa)
        intent.putExtra("nombreOrden", nombreOrden)
        startActivity(intent)
        finish()
    }
}