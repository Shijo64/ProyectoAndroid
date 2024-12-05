package mx.edu.potros.foodorder.Repositorys

import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Interfaces.OrdenDao

class OrdenRepository(private val ordenDao: OrdenDao) {

    suspend fun insertOrden(orden: Orden) {
        try {
            ordenDao.insert(orden)
        }catch (e: Exception){
            print(e.message)
        }
    }

    suspend fun getOrdenes(): List<Orden> {
        return ordenDao.getAll()
    }
}