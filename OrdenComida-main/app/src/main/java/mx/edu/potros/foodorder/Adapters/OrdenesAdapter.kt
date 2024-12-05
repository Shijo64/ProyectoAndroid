package mx.edu.potros.foodorder.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Activities.DetalleOrdenActivity
import mx.edu.potros.foodorder.R

class OrdenesAdapter(private val context: Context, private val ordenes: List<Orden>): BaseAdapter() {
    override fun getCount(): Int {
        return ordenes.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_row_view, parent, false)
        }

        view?.setOnClickListener {
            val intent = Intent(context, DetalleOrdenActivity::class.java)
            intent.putExtra("nombreOrden", ordenes[position].nombreOrden)
            intent.putExtra("numeroMesa", ordenes[position].numeroMesa)
            context.startActivity(intent)
        }

        val etNumeroMesa = view?.findViewById<TextView>(R.id.numeroMesa)
        val etNombreOrden = view?.findViewById<TextView>(R.id.nombreOrden)

        etNumeroMesa?.text = "Mesa: " + ordenes[position].numeroMesa.toString()
        etNombreOrden?.text = "Orden: " + ordenes[position].nombreOrden

        return view ?: throw NullPointerException("Expression 'view' must not be null")
    }
}