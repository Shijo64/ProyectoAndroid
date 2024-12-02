package mx.edu.potros.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.WindowCompat

class Bienvenido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_bienvenido)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}