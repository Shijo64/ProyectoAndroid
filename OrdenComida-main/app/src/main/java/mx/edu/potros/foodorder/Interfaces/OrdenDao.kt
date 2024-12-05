package mx.edu.potros.foodorder.Interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import mx.edu.potros.foodorder.Data.Orden

@Dao
interface OrdenDao {
    @Insert
    fun insert(orden: Orden)

    @Delete
    fun delete(orden: Orden)

    @Query("SELECT * FROM Orden")
    fun getAll(): List<Orden>

    @Query("SELECT * FROM Orden WHERE numeroMesa = :numeroMesa")
    fun getByName(numeroMesa: String): List<Orden>
}