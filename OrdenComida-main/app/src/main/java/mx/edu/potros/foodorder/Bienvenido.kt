package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.ImageView

class Bienvenido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenido)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAGS_CHANGED
        )

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}