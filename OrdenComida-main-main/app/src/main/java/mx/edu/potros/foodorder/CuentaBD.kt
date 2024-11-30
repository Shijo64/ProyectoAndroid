package mx.edu.potros.foodorder

import kotlinx.serialization.Serializable

@Serializable
data class CuentaBD(
    val nombre: String? = null,
    val platillos: List<PlatilloCuenta>? = ArrayList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CuentaBD
        if (nombre != other.nombre) return false
        return true
    }
    
    override fun hashCode(): Int {
        return nombre?.hashCode() ?: 0
    }
}
