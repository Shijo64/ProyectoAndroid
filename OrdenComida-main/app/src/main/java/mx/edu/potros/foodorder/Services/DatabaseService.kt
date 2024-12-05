package mx.edu.potros.foodorder.Services

import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.extensions.tryJsonCast
import io.appwrite.services.Databases
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Models.Cuenta
import mx.edu.potros.foodorder.Models.Mesa
import mx.edu.potros.foodorder.Data.PlatilloOrden

class DatabaseService(client: Client) {
    private val database = Databases(client)
    private val databaseID = "674d704100189047fe83"
    private val ordenesID = "674fb37300031ff925d4"
    private val platillosID = "6751d7d6001d0bc9c631"

    suspend fun crearOrden(numeroMesa: String, nombreOrden: String): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = ordenesID,
                documentId = ID.unique(),
                data = mapOf(
                    "nombreOrden" to nombreOrden,
                    "numeroMesa" to numeroMesa
                )
            )
            return document.id
        }catch (exception: AppwriteException){
            return exception.message.toString()
        }
    }

    suspend fun crearPlatillo(platillo: PlatilloOrden): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = platillosID,
                documentId = ID.unique(),
                data = mapOf(
                    "nombreOrden" to platillo.nombreOrden,
                    "numeroMesa" to platillo.numeroMesa,
                    "cantidad" to platillo.cantidad,
                    "nombrePlatillo" to platillo.nombrePlatillo,
                    "precio" to platillo.precio
                )
            )
            return document.id
        }catch (exception: AppwriteException){
            return exception.message.toString()
        }
    }
}