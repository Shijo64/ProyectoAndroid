package mx.edu.potros.foodorder.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.edu.potros.foodorder.Data.PlatilloOrden
import mx.edu.potros.foodorder.R

class DetalleOrdenAdapter(private val platillos: List<PlatilloOrden>, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return platillos.size
    }

    override fun getItem(position: Int): Any {
        return platillos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("DefaultLocale")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val platillo = platillos[position]

        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.detalle_platillo, parent, false)
        }

        val etPlatilloPrecio = view?.findViewById<TextView>(R.id.platillo_precio)
        val etPlatilloNombre = view?.findViewById<TextView>(R.id.platillo_nombre)
        val etCantidad = view?.findViewById<TextView>(R.id.cantidad)

        etPlatilloPrecio?.text = platillo.precio
        etPlatilloNombre?.text = platillo.nombrePlatillo
        etCantidad?.text = platillo.cantidad

        return view ?: throw NullPointerException("Expression 'view' must not be null")
    }
}