package mx.edu.potros.foodorder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.*

class MenuPrincipal : AppCompatActivity() {
    //private var realtimeSubscription: RealtimeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val containerLayout: LinearLayout = findViewById(R.id.linear_layout)
        val btnCerrarSesion: Button = findViewById(R.id.btn_cerrarSesion)
        val btnOrdenar: ImageView = findViewById(R.id.btnOrdenar)

        //setupRealtimeUpdates(containerLayout)
        cargarMesas(containerLayout)

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, Salir::class.java)
            startActivity(intent)
            finish()
        }

        btnOrdenar.setOnClickListener {
            val intent = Intent(this, NuevaMesa::class.java)
            startActivity(intent)
            finish()
        }
    }

    /*private fun setupRealtimeUpdates(containerLayout: LinearLayout) {
        lifecycleScope.launch {
            try {
                realtimeSubscription = SupabaseClient.client.realtime
                    .from("mesas")
                    .on(RealtimeChannel.PostgresChanges) { payload ->
                        when (payload.eventType) {
                            PostgresChangeEvent.INSERT -> {
                                val newMesa = payload.record.deserialize<Mesa>()
                                runOnUiThread {
                                    actualizarVistaConNuevaMesa(containerLayout, newMesa)
                                }
                            }
                            PostgresChangeEvent.UPDATE -> {
                                val updatedMesa = payload.record.deserialize<Mesa>()
                                runOnUiThread {
                                    actualizarVistaMesaModificada(containerLayout, updatedMesa)
                                }
                            }
                            PostgresChangeEvent.DELETE -> {
                                val deletedMesaId = payload.oldRecord?.get("id")?.toString()
                                runOnUiThread {
                                    eliminarVistaMesa(containerLayout, deletedMesaId)
                                }
                            }
                        }
                    }.subscribe()
            } catch (e: Exception) {
                Toast.makeText(
                    this@MenuPrincipal,
                    "Error conectando a tiempo real: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }*/

    private fun actualizarVistaConNuevaMesa(containerLayout: LinearLayout, mesa: Mesa) {
        val campoMesa = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(150f).toInt(),
                dpToPx(135f).toInt()
            ).apply {
                setMargins(dpToPx(10f).toInt(), 0, dpToPx(10f).toInt(), 0)
            }
            // Add your mesa views here
        }
        containerLayout.addView(campoMesa)
    }

    private fun actualizarVistaMesaModificada(containerLayout: LinearLayout, mesa: Mesa) {
        containerLayout.findViewWithTag<LinearLayout>(mesa.nombre)?.let { mesaLayout ->
            val ordenLista = mesaLayout.getChildAt(1) as TextView
            ordenLista.text = "Orden lista" // Update with actual status
        }
    }

    private fun eliminarVistaMesa(containerLayout: LinearLayout, mesaId: String?) {
        containerLayout.findViewWithTag<LinearLayout>(mesaId)?.let { view ->
            containerLayout.removeView(view)
        }
    }

    private fun cargarMesas(containerLayout: LinearLayout) {
        lifecycleScope.launch {
            try {
                val mesas = SupabaseClient.client.postgrest["mesas"]
                    .select()
                    .decodeList<Mesa>()

                runOnUiThread {
                    mesas.forEach { mesa ->
                        cargarHorizontalScrollView(containerLayout, mesa)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@MenuPrincipal,
                        "Error cargando mesas: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun cargarHorizontalScrollView(containerLayout: LinearLayout, mesa: Mesa) {
        val layoutParams = LinearLayout.LayoutParams(
            dpToPx(150f).toInt(), 
            dpToPx(135f).toInt()
        ).apply {
            setMargins(dpToPx(10f).toInt(), 0, dpToPx(10f).toInt(), 0)
        }

        val campoMesa = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            this.layoutParams = layoutParams
            background = ContextCompat.getDrawable(this@MenuPrincipal, R.drawable.round_button)
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MenuPrincipal, R.color.naranjaClaro))
            gravity = Gravity.CENTER
            tag = mesa.nombre
        }

        // Add views for mesa
        val nombreMesa = TextView(this).apply {
            text = "Mesa ${mesa.nombre}"
            setTextColor(ContextCompat.getColor(this@MenuPrincipal, R.color.white))
        }
        
        val ordenLista = TextView(this).apply {
            text = "Orden lista"  // Update with actual status
            setTextColor(ContextCompat.getColor(this@MenuPrincipal, R.color.white))
        }

        campoMesa.addView(nombreMesa)
        campoMesa.addView(ordenLista)

        campoMesa.setOnClickListener {
            val intent = Intent(this, Cuenta::class.java)
            intent.putExtra("mesa", mesa.nombre)
            startActivity(intent)
        }

        containerLayout.addView(campoMesa)
    }

    private fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            //realtimeSubscription?.unsubscribe()
        }
    }
}