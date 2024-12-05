package mx.edu.potros.foodorder.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import mx.edu.potros.foodorder.EspecificacionBoneless
import mx.edu.potros.foodorder.Enums.PlatilloEnum
import mx.edu.potros.foodorder.EspecificacionChickeMongolia
import mx.edu.potros.foodorder.Activities.SeleccionPlatilloActivity
import mx.edu.potros.foodorder.EspecificacionRollo
import mx.edu.potros.foodorder.EspecificacionTeriyaki
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R

class PlatillosAdapter(private val platillos: List<Platillo>, private val tipoPlatillo: String, private val numeroMesa: String, private val nombreCuenta: String, private val context: Context): BaseAdapter() {

    override fun getCount(): Int {
        return platillos.size
    }

    override fun getItem(position: Int): Any {
        return platillos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.comida, parent, false)
        }
        var platillo = platillos[position]

        var imagen = view?.findViewById<ImageView>(R.id.iv_comida)
        var nombre = view?.findViewById<TextView>(R.id.tv_nombre)
        var descripcion = view?.findViewById<TextView>(R.id.platillo_descripcion)
        var precio = view?.findViewById<TextView>(R.id.platillo_precio)

        imagen?.setImageResource(platillo.image)
        nombre?.setText(platillo.nombre)
        descripcion?.setText(platillo.descripcion)
        precio?.setText("$${platillo.precio}")

        when (tipoPlatillo) {
            PlatilloEnum.ENTRADAS.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            PlatilloEnum.ROLLOS.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            PlatilloEnum.PLATILLOS.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            PlatilloEnum.EXTRAS.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            PlatilloEnum.POSTRES.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            PlatilloEnum.BEBIDAS.name -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
            else -> {
                view?.setOnClickListener {
                    val intent = Intent(context, SeleccionPlatilloActivity::class.java)
                    intent.putExtra("nombre", platillo.nombre)
                    intent.putExtra("imagen", platillo.image)
                    intent.putExtra("descripcion", platillo.descripcion)
                    intent.putExtra("precio", platillo.precio)
                    intent.putExtra("numeroMesa", numeroMesa)
                    intent.putExtra("nombreOrden", nombreCuenta)
                    context.startActivity(intent)
                }
            }
        }

        return view?: throw NullPointerException("Expression 'view' must not be null")
    }
}