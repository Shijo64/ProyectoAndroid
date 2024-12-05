package mx.edu.potros.foodorder.Data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Orden(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var nombreOrden: String,
    var numeroMesa: String
)