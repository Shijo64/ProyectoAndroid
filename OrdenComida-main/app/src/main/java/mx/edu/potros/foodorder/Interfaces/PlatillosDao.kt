package mx.edu.potros.foodorder.Interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import mx.edu.potros.foodorder.Data.PlatilloOrden

@Dao
interface PlatillosDao {
    @Insert
    fun insert(platillo: PlatilloOrden)

    @Delete
    fun delete(platillo: PlatilloOrden)

    @Query("SELECT * FROM PlatilloOrden")
    fun getAll(): List<PlatilloOrden>

    @Query("SELECT * FROM PlatilloOrden WHERE nombreOrden = :nombreOrden")
    fun getByName(nombreOrden: String): List<PlatilloOrden>
}