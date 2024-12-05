package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import mx.edu.potros.foodorder.Enums.PlatilloEnum
import mx.edu.potros.foodorder.R

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        var nombreOrden: String? = ""
        var numeroMesa: String? = ""

        val ivEntradas: ImageView = findViewById(R.id.iv_entradas)
        val ivRollos: ImageView = findViewById(R.id.iv_rollos)
        val ivPlatillos: ImageView = findViewById(R.id.iv_platillos)
        val ivExtras: ImageView = findViewById(R.id.iv_extras)
        val ivPostres: ImageView = findViewById(R.id.iv_postres)
        val ivBebidas: ImageView = findViewById(R.id.iv_bebidas)

        val bundle = intent.extras

        if (bundle != null) {
            nombreOrden = bundle.getString("nombreOrden")
            numeroMesa = bundle.getString("numeroMesa")
        }

        /*var nombreCuenta: String? = ""
        var numCuentas: String? = ""
        var numMesa: String? = ""
        var cuentaID: String? = ""
        val tvNombreMesaCuenta: TextView = findViewById(R.id.tv_nombreMesaCuenta)

        val bundle = intent.extras

        if (bundle != null) {
            tvNombreMesaCuenta.setText("Cuenta de " + bundle.getString("cuenta") + "/Mesa" + bundle.getString("mesa"))

            nombreCuenta = bundle.getString("cuenta")
            numCuentas = bundle.getString("numCuentas")
            numMesa = bundle.getString("mesa")
            cuentaID = bundle.getString("cuentaID")
        }*/

        ivEntradas.setOnClickListener {
            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("tipo", PlatilloEnum.ENTRADAS.name)
            intent.putExtra("nombreOrden", nombreOrden)
            intent.putExtra("numeroMesa", numeroMesa)
            /*intent.putExtra("tipo", "entradas")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)*/
            startActivity(intent)
            finish()
        }

        /*ivRollos.setOnClickListener {
            var intent: Intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "rollos")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }

        ivPlatillos.setOnClickListener {
            var intent: Intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "platillos")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }

        ivExtras.setOnClickListener {
            var intent: Intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "extras")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }

        ivPostres.setOnClickListener {
            var intent: Intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "postres")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }

        ivBebidas.setOnClickListener {
            var intent: Intent = Intent(this, Catalogo::class.java)
            intent.putExtra("tipo", "bebidas")
            intent.putExtra("cuenta", nombreCuenta)
            intent.putExtra("mesa", numMesa)
            intent.putExtra("numCuentas", numCuentas)
            intent.putExtra("cuentaID", cuentaID)
            startActivity(intent)
            finish()
        }*/
    }
}