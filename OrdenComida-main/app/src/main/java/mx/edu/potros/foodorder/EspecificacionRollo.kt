package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EspecificacionRollo : AppCompatActivity() {

    private val mesaRef = FirebaseDatabase.getInstance().getReference("Mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especificacion_rollo)

        var nombreCuenta: String? = ""
        var numMesa: String? = ""
        var numCuentas: String? = ""
        val btnAgregar: Button = findViewById(R.id.btn_especificacion_agregar)
        val btnRegresar: Button = findViewById(R.id.btn_especificacion_regresar)
        val tvDescripcion: TextView = findViewById(R.id.tv_especificacionDescripcion)
        var ivRollo: ImageView = findViewById(R.id.iv_rollo)
        var tvNombre: TextView = findViewById(R.id.tv_nombreEspecificacionRollo)
        var tvPrecio: TextView = findViewById(R.id.tv_especificacionPrecio)

        val bundle = intent.extras

        if (bundle != null) {
            ivRollo.setImageResource(bundle.getInt("imagen"))
            tvNombre.setText(bundle.getString("nombre"))
            tvPrecio.setText("$${bundle.getDouble("precio")}")
            tvDescripcion.setText(bundle.getString("descripcion"))
            nombreCuenta = bundle.getString("cuenta")
            numMesa = bundle.getString("mesa")
            numCuentas = bundle.getString("numCuentas")
        }

        btnAgregar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de agregar ese platillo?")

            builder.setPositiveButton("Si") { dialog, which ->
                agregarRollo(tvNombre.text.toString(), nombreCuenta, numMesa, numCuentas)
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
            intent.putExtra("tipo", "rollos")
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            startActivity(intent)
            finish()
        }
    }

    private fun agregarRollo(nombrePlatillo: String?, nombreCuenta: String?, numMesa: String?, numCuentas: String?) {
        val empanizado: CheckBox = findViewById(R.id.checkBox)
        val natural: CheckBox = findViewById(R.id.checkBox2)
        val mitad: CheckBox = findViewById(R.id.checkBox3)
        val conAlga: CheckBox = findViewById(R.id.checkBox4)
        val sinAlga: CheckBox = findViewById(R.id.checkBox5)
        val carne: CheckBox = findViewById(R.id.checkBox111)
        val camaron: CheckBox = findViewById(R.id.checkBox122)
        val pollo: CheckBox = findViewById(R.id.checkBox6)
        val tocino: CheckBox = findViewById(R.id.checkBox7)
        val surimi: CheckBox = findViewById(R.id.checkBox8)
        val tampico: CheckBox = findViewById(R.id.checkBox9)
        val gratinado: CheckBox = findViewById(R.id.checkBox10)

        val arrozChecked = listOf(empanizado, natural, mitad)
        val algaChecked = listOf(conAlga, sinAlga)
        val ingredienteExtraChecked = listOf(carne, camaron, pollo, tocino, surimi, tampico, gratinado)

        var contadorArroz = 0
        var contadorAlga = 0
        var contadorIngrediente = 0

        var arroz = ""
        var alga = ""
        var ingredienteExtra = ""

        for (aC in arrozChecked) {
            if (aC.isChecked) {
                arroz = aC.text.toString()
                contadorArroz++
            }
        }

        if (contadorArroz < 1 || contadorArroz > 1) {
            Toast.makeText(this, "Seleccione un tipo de arroz", Toast.LENGTH_SHORT).show()
            return
        }

        for (aC in algaChecked) {
            if (aC.isChecked) {
                alga = "," + aC.text.toString()
                contadorAlga++
            }
        }

        if (contadorAlga < 1 || contadorAlga > 1) {
            Toast.makeText(this, "Seleccione como quiere su alga", Toast.LENGTH_SHORT).show()
            return
        }

        for (iC in ingredienteExtraChecked) {
            if (iC.isChecked) {
                contadorIngrediente++
            }
        }

        if (contadorIngrediente > 1) {
            Toast.makeText(this, "Solo seleccione un ingrediente extra", Toast.LENGTH_SHORT).show()
            return
        }

        if (carne.isChecked) {
            ingredienteExtra = ",CarneEx"
        } else if (camaron.isChecked) {
            ingredienteExtra = ",CamarónEx"
        } else if (pollo.isChecked) {
            ingredienteExtra = ",PolloEx"
        } else if (tocino.isChecked) {
            ingredienteExtra = ",TocinoEx"
        } else if (surimi.isChecked) {
            ingredienteExtra = ",SurimiEx"
        } else if (tampico.isChecked) {
            ingredienteExtra = ",TampicoEx"
        } else if (gratinado.isChecked) {
            ingredienteExtra = ",GratinadoEx"
        }

        val extras = arroz + alga + ingredienteExtra
        val platillo = PlatilloCuenta(1, extras, nombrePlatillo)

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

                        var intent = Intent(this@EspecificacionRollo, SeguirAgregando::class.java)
                        intent.putExtra("mesa", numMesa)
                        intent.putExtra("cuenta", nombreCuenta)
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