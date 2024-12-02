package mx.edu.potros.foodorder.Modelos

import mx.edu.potros.foodorder.CuentaBD

data class Mesa(var nombre: String? = null, var cuentas: ArrayList<CuentaBD>? = ArrayList())
