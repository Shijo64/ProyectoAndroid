package mx.edu.potros.foodorder.Models

data class Orden(
    val cuentaID: String,
    val numeroMesa: Int,
    val nombreCuenta: String,
    val platillos: List<PlatilloOrden>
)