package mx.edu.potros.foodorder.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Models.Mesa
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

        val listText = view?.findViewById<TextView>(R.id.numeroMesa)
        listText?.text = "Mesa: " + ordenes[position].numeroMesa.toString()
        return view ?: throw NullPointerException("Expression 'view' must not be null")
    }
}