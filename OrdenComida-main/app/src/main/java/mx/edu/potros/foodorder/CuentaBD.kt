package mx.edu.potros.foodorder

data class CuentaBD(var nombre: String? = null, var platillos: ArrayList<PlatilloCuenta>? = ArrayList()) {
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
