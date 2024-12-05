package mx.edu.potros.foodorder.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlatilloOrden(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var nombreOrden: String,
    var numeroMesa: String,
    var cantidad: String,
    var nombrePlatillo: String,
    var precio: String
)
