package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import mx.edu.potros.foodorder.Models.PlatilloOrden
import mx.edu.potros.foodorder.R
import java.lang.Exception

class SeleccionPlatilloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_platillo)

        val btn_back = findViewById<Button>(R.id.btn_back)
        val btn_agregar = findViewById<Button>(R.id.btn_agregar)
        val btn_mas = findViewById<Button>(R.id.btn_mas)
        val btn_menos = findViewById<Button>(R.id.btn_menos)

        val platillo_imagen = findViewById<ImageView>(R.id.platillo_imagen)
        val platillo_nombre = findViewById<TextView>(R.id.platillo_nombre)
        val platillo_precio = findViewById<TextView>(R.id.platillo_precio)
        val platillo_descripcion = findViewById<TextView>(R.id.platillo_descripcion)
        val platillo_cantidad = findViewById<TextView>(R.id.platillo_cantidad)

        var numeroMesa: String? = ""
        var nombreOrden: String? = ""
        var tipoPlatillo: String? = ""

        val bundle = intent.extras

        if (bundle != null) {
            platillo_imagen.setImageResource(bundle.getInt("imagen"))
            platillo_nombre.setText(bundle.getString("nombre"))
            platillo_precio.setText("$${bundle.getDouble("precio")}")
            platillo_descripcion.setText(bundle.getString("descripcion"))
            tipoPlatillo = bundle.getString("tipo")
            numeroMesa = bundle.getString("numeroMesa")
            nombreOrden = bundle.getString("nombreOrden")
        }

        btn_mas.setOnClickListener {
            var txtCantidad: String = platillo_cantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                cantidad++
                platillo_cantidad.setText(cantidad.toString())
            } catch (e: Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btn_menos.setOnClickListener {
            var txtCantidad: String = platillo_cantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                if (cantidad != 1) {
                    cantidad--
                    platillo_cantidad.setText(cantidad.toString())
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
                        0,
                        nombreOrden.toString(),
                        numeroMesa.toString().toInt(),
                        platillo_cantidad.text.toString().toInt(),
                        "",
                        platillo_nombre.text.toString(),
                        platillo_precio.text.toString().toDouble()
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
            var intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", tipoPlatillo)
            intent.putExtra("mesa", numeroMesa)
            intent.putExtra("nombreOrden", nombreOrden)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarPlatillo(platillo: PlatilloOrden) {

        //guardar platillo en base de datos


        //val platillo = PlatilloCuenta(cantidad, null, nombrePlatillo)

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

                        var intent = Intent(this@EspecificacionGenerica, SeguirAgregando::class.java)
                        intent.putExtra("cuenta", nombreCuenta)
                        intent.putExtra("mesa", numMesa)
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