package mx.edu.potros.foodorder.Models

import java.io.Serializable

data class Cuenta(
    var nombre: String,
    var mesaID: String,
    var numeroMesa: String,
    var platillos: List<PlatilloCuenta>
):Serializable
