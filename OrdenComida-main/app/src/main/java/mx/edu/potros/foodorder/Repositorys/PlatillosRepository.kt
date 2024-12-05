package mx.edu.potros.foodorder.Repositorys

import mx.edu.potros.foodorder.Data.PlatilloOrden
import mx.edu.potros.foodorder.Interfaces.PlatillosDao

class PlatillosRepository(private val platillosDao: PlatillosDao) {

    suspend fun insertPlatillo(platillo: PlatilloOrden) {
        try {
            platillosDao.insert(platillo)
        }catch (e: Exception) {
            print(e.message)
        }
    }

    suspend fun getPlatillosByNombreOrden(nombreOrden: String): List<PlatilloOrden> {
        return platillosDao.getByName(nombreOrden)
    }
}