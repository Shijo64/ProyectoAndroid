package mx.edu.potros.foodorder.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.Models.Mesa
import mx.edu.potros.foodorder.R

class MenuPrincipal : AppCompatActivity() {

    var mesas = emptyList<Mesa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val containerLayout: LinearLayout = findViewById(R.id.linear_layout)
        val btnCerrarSesion: Button = findViewById(R.id.btn_cerrarSesion)
        val btnOrdenar: ImageView = findViewById(R.id.btnOrdenar)

        cargarMesas(containerLayout)

        btnCerrarSesion.setOnClickListener{
            var intent = Intent(this, Salir::class.java)
            startActivity(intent)
            finish()
        }

        btnOrdenar.setOnClickListener{
            var intent = Intent(this, NuevaMesa::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun cargarMesas(containerLayout: LinearLayout) {
        lifecycleScope.launch {
            mesas = Appwrite.database.getMesas() as ArrayList<Mesa>
            cargarHorizontalScrollView(containerLayout)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun cargarHorizontalScrollView(containerLayout: LinearLayout) {
        val layoutParams = LinearLayout.LayoutParams(dpToPx(150f).toInt(), dpToPx(135f).toInt())
        layoutParams.setMargins(dpToPx(10f).toInt(), 0, dpToPx(10f).toInt(), 0)

        for (mesa in mesas) {
            val campoMesa = LinearLayout(this)
            campoMesa.layoutParams = layoutParams
            campoMesa.orientation = LinearLayout.VERTICAL
            campoMesa.setBackgroundResource(R.drawable.round_view)
            campoMesa.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,
                R.color.amarillento
            ))
            campoMesa.setPadding(dpToPx(10f).toInt(), 0, dpToPx(10f).toInt(), 0)


            val nombreMesa = TextView(this)
            nombreMesa.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            nombreMesa.gravity = Gravity.CENTER
            nombreMesa.text = "Mesa ${mesa.numeroMesa}"
            nombreMesa.setTypeface(ResourcesCompat.getFont(this, R.font.inter_bold))
            nombreMesa.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            nombreMesa.setTextColor(ContextCompat.getColor(this, R.color.naranja))

            val ordenLista = TextView(this)
            ordenLista.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            ordenLista.gravity = Gravity.CENTER
            ordenLista.text = getString(R.string.ordenLista)
            ordenLista.setTypeface(ResourcesCompat.getFont(this, R.font.inter_bold))
            ordenLista.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
            ordenLista.setTextColor(ContextCompat.getColor(this, R.color.white))

            campoMesa.addView(nombreMesa)
            campoMesa.addView(ordenLista)

            campoMesa.setOnClickListener {
                val intent = Intent(this, CuentaActivity::class.java)
                intent.putExtra("mesa", mesa.numeroMesa)
                startActivity(intent)
            }

            containerLayout.addView(campoMesa)
        }
    }

    private fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }
}