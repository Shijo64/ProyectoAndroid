package mx.edu.potros.foodorder

import kotlinx.serialization.Serializable

@Serializable 
data class PlatilloCuenta(
    val cantidad: Int? = null,
    val extras: String? = null,
    val platillo: String? = null
)
