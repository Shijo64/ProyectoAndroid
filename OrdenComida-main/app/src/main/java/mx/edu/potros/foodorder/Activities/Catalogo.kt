package mx.edu.potros.foodorder.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import mx.edu.potros.foodorder.EspecificacionChickeMongolia
import mx.edu.potros.foodorder.EspecificacionGenerica
import mx.edu.potros.foodorder.EspecificacionRollo
import mx.edu.potros.foodorder.EspecificacionTeriyaki
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R

class Catalogo : AppCompatActivity() {

    var adapter: PlatilloAdapter? = null
    var platillos = ArrayList<Platillo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        var numMesa: String? = intent.getStringExtra("mesa")
        var numCuentas: String? = intent.getStringExtra("numCuentas")
        var nombreCuenta: String? = intent.getStringExtra("cuenta")
        var cuentaID: String? = intent.getStringExtra("cuentaID")

        var menuOption: String? = intent.getStringExtra("tipo")
        cargarPlatillo(menuOption)

        var tvMenuOption: TextView = findViewById(R.id.tv_menu_option)
        val gridview: GridView = findViewById(R.id.gridView)
        adapter = PlatilloAdapter(platillos, this, tvMenuOption, numMesa, nombreCuenta, menuOption, numCuentas, cuentaID)
        gridview.adapter = adapter

        var button: Button = findViewById(R.id.btn_catalagoPlatillo_regresar)

        when (menuOption) {
            "entradas" -> {
                tvMenuOption.setText("Entradas")
            }

            "rollos" -> {
                tvMenuOption.setText("Rollos")
            }

            "platillos" -> {
                tvMenuOption.setText("Platillos")
            }

            "extras" -> {
                tvMenuOption.setText("Extras")
            }

            "postres" -> {
                tvMenuOption.setText("Postres")
            }

            "bebidas" -> {
                tvMenuOption.setText("Bebidas")
            }
        }

        button.setOnClickListener {
            var intent = Intent(this, Menu::class.java)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }
    }

    fun cargarPlatillo(option: String?) {
        when (option) {
            "entradas" -> {
                platillos.add(Platillo(R.drawable.boneless, "Boneless", "Tiras de pechuga de pollo empanizadas y bañadas en salsa de su elección.", 140.00))
                platillos.add(Platillo(R.drawable.chilesrellenos, "Chiles rellenos", "Chile caribe empanizado relleno con camarón, surimi, rocino y queso phila.", 100.00))
                platillos.add(Platillo(R.drawable.dedosdequeso, "Dedos de queso", "Tiras de queso phila o queso manchego.", 90.00))
            }

            "rollos" -> {
                platillos.add(Platillo(R.drawable.california, "California Tradicional", "Pepino, aguacate, phila y un ingrediente a elegir: marlin, tocino, pollo, plátano, chile toreado, camarón, surimi o tampico.", 110.00))
                platillos.add(Platillo(R.drawable.mangoroll, "Mango roll", "Rollo de pepino, aguacate, pollo y tocino con una mezcla de queso phila con trozos de piña por fuera del rollo y cubierto de una salsa de mango con chile.", 111.00))
                platillos.add(Platillo(R.drawable.manchegoroll, "Manchego roll", "Rollo de pepino, aguacate, queso phila, tocino, res y gratinado con queso manchego.", 112.00))
                platillos.add(Platillo(R.drawable.sushilitoroll, "Suhilito roll", "Rollo de pepino, aguacate, queso phila, tocino, camarón, chiles toreados y un toque de salsa picosa por dentro del rollo.", 145.00))
            }

            "platillos" -> {
                platillos.add(Platillo(R.drawable.teriyaki, "Teriyaki", "Verduras cebolla, brócoli, zanahoria, apio, calabaza con arroz, preparación e ingrediente a elegir, bañado con salsa teriyaki y ajonjolí espolvoreado.", 150.00))
                platillos.add(Platillo(R.drawable.chickenmongolia, "Chicken Mongolia", "Pollo, cebolla, pimientos verdes y rojos, chile de árbol, cacahuate, pollo capeado, salsa mongolia spicy.", 150.00))
                platillos.add(Platillo(R.drawable.yakimeshi, "Yakimeshi especial", "Tazón de arroz frito preparado con verduras picadas finamente, res, pollo, tocino y tampico", 150.00))
            }

            "extras" -> {
                platillos.add(Platillo(R.drawable.ordendetampico, "Orden de tampico", "", 35.00))
            }

            "postres" -> {
                platillos.add(Platillo(R.drawable.paydequeso, "Pay de queso", "", 60.00))
                platillos.add(Platillo(R.drawable.flannapolitano, "Flan napolitano", "", 50.00))
            }

            "bebidas" -> {
                platillos.add(Platillo(R.drawable.limonada, "Limonada natural", "", 40.00))
                platillos.add(Platillo(R.drawable.limonada, "Limonada mineral", "", 45.00))
                platillos.add(Platillo(R.drawable.refresco, "Refresco 600 ml", "", 35.00))
            }
        }
    }

    class PlatilloAdapter: BaseAdapter {

        var platillos = ArrayList<Platillo>()
        var context: Context? = null
        var option: TextView
        var numMesa: String? = null
        var nombreCuenta: String? = null
        var tipoPlatillo: String? = null
        var numCuentas: String? = null
        var cuentaID: String? = null

        constructor(platillos: ArrayList<Platillo>, context: Context?, option: TextView, numMesa: String?, nombreCuenta: String?, tipoPlatillo: String?, numCuentas: String?, cuentaID: String?): super() {
            this.platillos = platillos
            this.context = context
            this.option = option
            this.numMesa = numMesa
            this.nombreCuenta = nombreCuenta
            this.tipoPlatillo = tipoPlatillo
            this.numCuentas = numCuentas
            this.cuentaID = cuentaID
        }

        override fun getCount(): Int {
            return platillos.size
        }

        override fun getItem(position: Int): Any {
            return platillos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var platillo = platillos[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.comida, null)

            var imagen = vista.findViewById(R.id.iv_comida) as ImageView
            var nombre = vista.findViewById(R.id.tv_nombre) as TextView
            var descripcion = vista.findViewById(R.id.tv_descripcion) as TextView
            var precio = vista.findViewById(R.id.tv_precio) as TextView

            imagen.setImageResource(platillo.image)
            nombre.setText(platillo.nombre)
            descripcion.setText(platillo.descripcion)
            precio.setText("$${platillo.precio}")

            if (platillo.image == R.drawable.boneless) {
                vista.setOnClickListener {
                    var intent = Intent(context, EspecificacionBoneless::class.java)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    intent.putExtra("cuentaID", cuentaID)
                    context!!.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            } else if (option.text.equals("Rollos")) {
                vista.setOnClickListener {
                    var intent = Intent(context, EspecificacionRollo::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("decripcion", platillo.descripcion)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    intent.putExtra("cuentaID", cuentaID)
                    context!!.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            } else if (platillo.image == R.drawable.chickenmongolia) {
                vista.setOnClickListener {
                    var intent = Intent(context, EspecificacionChickeMongolia::class.java)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    intent.putExtra("cuentaID", cuentaID)
                    context!!.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            } else if (platillo.image == R.drawable.teriyaki) {
                vista.setOnClickListener {
                    var intent = Intent(context, EspecificacionTeriyaki::class.java)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("numCuentas", numCuentas)
                    intent.putExtra("cuentaID", cuentaID)
                    context!!.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            } else {
                vista.setOnClickListener {
                    var intent = Intent(context, EspecificacionGenerica::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("decripcion", platillo.descripcion)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("mesa", numMesa)
                    intent.putExtra("cuenta", nombreCuenta)
                    intent.putExtra("tipo", tipoPlatillo)
                    intent.putExtra("numCuentas", numCuentas)
                    intent.putExtra("cuentaID", cuentaID)
                    context!!.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            }

            return vista
        }
    }
}