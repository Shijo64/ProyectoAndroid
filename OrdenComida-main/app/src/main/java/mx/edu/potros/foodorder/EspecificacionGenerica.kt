package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EspecificacionGenerica : AppCompatActivity() {

    private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_generica)

        var numMesa: String? = ""
        var nombreCuenta: String? = ""
        var numCuentas: String? = ""
        var tipoPlatillo: String? = ""
        val btnMas: Button = findViewById(R.id.btn_especificacion_mas)
        val btnMenos: Button = findViewById(R.id.btn_especificacion_menos)
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        var tvCantidad: TextView = findViewById(R.id.tv_cantidad)
        var ivComida: ImageView = findViewById(R.id.iv_especificar)
        var tvNombre: TextView = findViewById(R.id.tv_nombreComida)
        var tvPrecio: TextView = findViewById(R.id.tv_precio)
        var tvDescripcion: TextView = findViewById(R.id.tv_descripcion)

        val bundle = intent.extras

        if (bundle != null) {
            ivComida.setImageResource(bundle.getInt("imagen"))
            tvNombre.setText(bundle.getString("nombre"))
            tvPrecio.setText("$${bundle.getDouble("precio")}")
            tvDescripcion.setText(bundle.getString("descripcion"))
            numMesa = bundle.getString("mesa")
            nombreCuenta = bundle.getString("cuenta")
            tipoPlatillo = bundle.getString("tipo")
            numCuentas = bundle.getString("numCuentas")
        }

        btnMas.setOnClickListener {
            var txtCantidad: String = tvCantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                cantidad++
                tvCantidad.setText(cantidad.toString())
            } catch (e: java.lang.Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btnMenos.setOnClickListener {
            var txtCantidad: String = tvCantidad.text.toString()

            try {
                var cantidad = Integer.parseInt(txtCantidad)
                if (cantidad != 1) {
                    cantidad--
                    tvCantidad.setText(cantidad.toString())
                }
            } catch (e: java.lang.Exception) {
                System.out.println("Could not parse " + e)
            }
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                var txtCantidad = tvCantidad.text.toString()

                try {
                    var cantidad = Integer.parseInt(txtCantidad)
                    agregarPlatillo(cantidad, tvNombre.text.toString(), nombreCuenta, numMesa, numCuentas)
                } catch (e: java.lang.Exception) {
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

        btnRegresar.setOnClickListener {
            var intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", tipoPlatillo)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarPlatillo(cantidad: Int, nombrePlatillo: String?, nombreCuenta: String?, numMesa: String?, numCuentas: String?) {
        val platillo = PlatilloCuenta(cantidad, null, nombrePlatillo)

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
        })
    }
}