package mx.edu.potros.foodorder.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import mx.edu.potros.foodorder.CobraCuenta
import mx.edu.potros.foodorder.Models.Cuenta
import mx.edu.potros.foodorder.Models.Mesa
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R

class CuentaActivity : AppCompatActivity() {

    var mesa: Mesa? = null
    var platillos = ArrayList<Platillo>()
    var adapter: CuentaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        var numMesa: String? = ""
        val tvNumMesa: TextView = findViewById(R.id.tv_num_mesa)
        val gridView: GridView = findViewById(R.id.cuentas)
        val btnAdd: ImageView = findViewById(R.id.btn_add)
        val btnEnviarPedido: Button = findViewById(R.id.btn_cuenta_ordenar)
        val bundle = intent.extras

        if (bundle != null) {
            numMesa = bundle.getInt("mesa").toString()
        }

        getMesaYPlatillos(numMesa, gridView)
        tvNumMesa.setText("Mesa " + numMesa)

        btnAdd.setOnClickListener {
            val intent = Intent(this, MenuOrdenar::class.java)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", "una")
            startActivity(intent)
            finish()
        }

        btnEnviarPedido.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de enviar ese pedido?")

            builder.setPositiveButton("Si") { dialog, which ->
                borrarMesa(numMesa)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun getMesaYPlatillos(numMesa: String?, gridView: GridView) {
        //obtener mesa y platillos de la base de datos
        /*
        mesaRef.orderByChild("nombre").equalTo(numMesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    mesa = s.getValue(Mesa::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        platilloRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    val platillo = s.getValue(Platillo::class.java)
                    platillo?.let { platillos.add(it) }
                }

                adapter = CuentaAdapter(mesa!!.cuentas, platillos, this@Cuenta, mesa!!.nombre)
                gridView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }

    private fun borrarMesa(numMesa: String?) {
        //borrar mesa de la base de datos
       /* mesaRef.orderByChild("nombre").equalTo(numMesa).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    s.ref.removeValue()
                    Toast.makeText(this@Cuenta, "Las cuentas de la mesa han sido enviadas a caja", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Cuenta, MenuPrincipal::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })*/
    }

    class CuentaAdapter : BaseAdapter {

        var cuentas: ArrayList<Cuenta>? = null
        var platillos = ArrayList<Platillo>()
        var context: Context? = null
        var numMesa: String? = null

        constructor(cuentas: ArrayList<Cuenta>?, platillos: ArrayList<Platillo>, context: Context, numMesa: String?): super() {
            this.cuentas = cuentas
            this.platillos = platillos
            this.context = context
            this.numMesa = numMesa
        }

        override fun getCount(): Int {
            return cuentas!!.size
        }

        override fun getItem(position: Int): Any {
            return cuentas!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var cuenta = cuentas!![position]
            var inflador = LayoutInflater.from(context)
            var vista = inflador.inflate(R.layout.cuentas, null)

            var nombreCuenta: TextView = vista.findViewById(R.id.tv_nombre_cuenta)
            var platilloCuenta: TextView = vista.findViewById(R.id.tv_cuenta_platillo)
            var precioCuenta: TextView = vista.findViewById(R.id.tv_cuenta_precio)
            var btnCobrar: Button = vista.findViewById(R.id.btn_cuenta_cobrar)
            var btnOrdenar: Button = vista.findViewById(R.id.btn_cuenta_ordenar)
            var textoPlatillo = ""
            var textoPrecio = ""

            nombreCuenta.setText(cuenta.nombre)

            for (platillo in cuenta.platillos!!) {
                var i = 0

                while (i < platillo.cantidad!!) {
                    textoPlatillo += "${platillo.platillo}\n"

                    for (item in platillos) {
                        if (item.nombre == platillo.platillo) {
                            textoPrecio += "$${item.precio}\n"
                        }
                    }

                    if (platillo.extras != null) {
                        var extras = platillo.extras!!.split(",")

                        for (extra in extras) {
                            if (extra.endsWith("Ex")) {
                                val conPrecio = extra.split("Ex")
                                textoPlatillo += "  + " + conPrecio[0] + "\n"
                                textoPrecio += "$20\n"
                            } else {
                                textoPlatillo += "  ${extra}\n"
                                textoPrecio += "\n"
                            }
                        }
                    }

                    i++
                }
            }

            platilloCuenta.setText(textoPlatillo)
            precioCuenta.setText(textoPrecio)

            btnCobrar.setOnClickListener {
                val intent = Intent(context, CobraCuenta::class.java)
                intent.putExtra("mesa", numMesa)
                intent.putExtra("cuenta", cuenta.nombre)
                context!!.startActivity(intent)
                (context as AppCompatActivity).finish()
            }

            btnOrdenar.setOnClickListener {
                val intent = Intent(context, Menu::class.java)
                intent.putExtra("mesa", numMesa)
                intent.putExtra("cuenta", cuenta.nombre)
                context!!.startActivity(intent)
                (context as AppCompatActivity).finish()
            }

            return vista
        }
    }
}