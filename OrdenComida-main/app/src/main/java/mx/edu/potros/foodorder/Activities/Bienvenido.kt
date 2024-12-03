package mx.edu.potros.foodorder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat
import mx.edu.potros.foodorder.R

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