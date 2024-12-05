package mx.edu.potros.foodorder.Models

data class PlatilloOrden(
    var id: Int,
    var nombreOrden: String,
    var numeroMesa: Int,
    var cantidad: Int,
    var extras: String,
    var nombrePlatillo: String,
    var precio: Double
)
