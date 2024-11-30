package mx.edu.potros.foodorder

import kotlinx.serialization.Serializable

@Serializable
data class Platillo(
    val image: Int = 0,
    val nombre: String? = null,
    val descripcion: String? = null,
    val precio: Double = 0.0,
    val cantidad: Int? = null,
    val especificaciones: String? = null
)
