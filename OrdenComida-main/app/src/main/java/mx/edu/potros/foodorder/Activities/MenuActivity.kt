package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import mx.edu.potros.foodorder.Enums.PlatilloEnum
import mx.edu.potros.foodorder.R

class MenuActivity : AppCompatActivity() {
    private var nombreOrden: String? = ""
    private var numeroMesa: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val ivEntradas: ImageView = findViewById(R.id.iv_entradas)
        val ivRollos: ImageView = findViewById(R.id.iv_rollos)
        val ivPlatillos: ImageView = findViewById(R.id.iv_platillos)
        val ivExtras: ImageView = findViewById(R.id.iv_extras)
        val ivPostres: ImageView = findViewById(R.id.iv_postres)
        val ivBebidas: ImageView = findViewById(R.id.iv_bebidas)
        var btnBack: Button = findViewById(R.id.btn_back)

        val bundle = intent.extras

        if (bundle != null) {
            nombreOrden = bundle.getString("nombreOrden")
            numeroMesa = bundle.getString("numeroMesa")
        }

        ivEntradas.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.ENTRADAS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        ivRollos.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.ROLLOS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        ivPlatillos.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.PLATILLOS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        ivExtras.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.EXTRAS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        ivPostres.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.POSTRES.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        ivBebidas.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.BEBIDAS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}