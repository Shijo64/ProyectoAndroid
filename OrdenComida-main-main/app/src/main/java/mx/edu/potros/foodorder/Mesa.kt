package mx.edu.potros.foodorder

import kotlinx.serialization.Serializable

@Serializable
data class Mesa(
    val id: String? = null,
    val nombre: String? = null,
    val num_cuentas: String? = null,
    val cuentas: List<CuentaBD>? = ArrayList()
)
