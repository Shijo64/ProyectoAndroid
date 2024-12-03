package mx.edu.potros.foodorder.Services

import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.extensions.tryJsonCast
import io.appwrite.services.Databases
import mx.edu.potros.foodorder.Models.Cuenta
import mx.edu.potros.foodorder.Models.Mesa
import mx.edu.potros.foodorder.Models.Orden
import mx.edu.potros.foodorder.Models.PlatilloCuenta
import java.util.UUID

class DatabaseService(client: Client) {
    private val database = Databases(client)
    private val databaseID = "674d704100189047fe83"
    private val mesasID = "674d70510018561aa9a4"
    private val cuentasID = "674d712e00129339728f"
    private val platillosID = "674d7388001669c99bad"
    private val ordenesID = "674d712e00129339728f"

    suspend fun addMesa(numeroMesa: Int): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = mesasID,
                documentId = ID.unique().toString(),
                data = mapOf("numeroMesa" to numeroMesa),
            )
            return document.id
        }catch (exception: AppwriteException){
            print(exception)
            return exception.message.toString()
        }
    }

    suspend fun getMesas(): List<Mesa?> {
        try {
            val response = database.listDocuments(databaseID, mesasID)
            val mesas = response.documents.map { it.data.tryJsonCast<Mesa>() }
            return mesas
        }catch (exception: AppwriteException){
            print(exception)
            return emptyList()
        }
    }

    suspend fun crearCuenta(cuenta: Cuenta): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = cuentasID,
                documentId = ID.unique(),
                data = mapOf("nombre" to cuenta.nombre, "mesaID" to cuenta.mesaID),
            )

            return document.id
        }catch (exception: AppwriteException){
            print(exception)
            return exception.message.toString()
        }
    }

    suspend fun getCuentas(): List<Cuenta?> {
        try {
            val response = database.listDocuments(databaseID, cuentasID)
            val cuentas = response.documents.map { it.data.tryJsonCast<Cuenta>() }
            return cuentas
        }catch (exception: AppwriteException){
            print(exception)
            return emptyList()
        }
    }

    suspend fun agregarPlatillo(platillo: PlatilloCuenta): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = platillosID,
                documentId = ID.unique(),
                data = mapOf("nombre" to platillo.platillo,"cantidad" to platillo.cantidad,"extras" to platillo.extras,"idCuenta" to platillo.idCuenta,)
            )
            return document.id
        }catch (exception: AppwriteException){
            print(exception)
            return exception.message.toString()
        }
    }

    suspend fun crearOrden(orden: Orden): String {
        try {
            val document = database.createDocument(
                databaseId = databaseID,
                collectionId = ordenesID,
                documentId = ID.unique(),
                data = mapOf(
                    "cuentaID" to orden.cuentaID,
                    "numeroMesa" to orden.numeroMesa,
                    "nombreCuenta" to orden.nombreCuenta,
                    "platillos" to orden.platillos)
            )

            return document.id
        }catch (exception: AppwriteException){
            print(exception)
            return exception.message.toString()
        }
    }
}