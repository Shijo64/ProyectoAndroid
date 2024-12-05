package mx.edu.potros.foodorder.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.potros.foodorder.Enums.PlatilloEnum
import mx.edu.potros.foodorder.Models.Platillo
import mx.edu.potros.foodorder.R

class CatalogoViewModel: ViewModel() {
    private val _platillos = MutableLiveData<List<Platillo>>()
    val platillos: LiveData<List<Platillo>> get() = _platillos

    fun cargarPlatillos(tipo: String?) {
        when (tipo) {
            PlatilloEnum.ENTRADAS.name -> {
                var boneless = Platillo(R.drawable.boneless, "Boneless", "Tiras de pechuga de pollo empanizadas y bañadas en salsa de su elección.", "140")
                _platillos.value = _platillos.value?.plus(boneless) ?: listOf(boneless)
                var chilesRellenos = Platillo(R.drawable.chilesrellenos, "Chiles rellenos", "Chile caribe empanizado relleno con camarón, surimi, rocino y queso phila.", "100")
                _platillos.value = _platillos.value?.plus(chilesRellenos) ?: listOf(chilesRellenos)
                var dedosDeQueso = Platillo(R.drawable.dedosdequeso, "Dedos de queso", "Tiras de queso phila o queso manchego.", "90")
                _platillos.value = _platillos.value?.plus(dedosDeQueso) ?: listOf(dedosDeQueso)
            }

            PlatilloEnum.ROLLOS.name -> {
                var boneless = Platillo(R.drawable.california, "California Tradicional", "Pepino, aguacate, phila y un ingrediente a elegir: marlin, tocino, pollo, plátano, chile toreado, camarón, surimi o tampico.", "110")
                _platillos.value = _platillos.value?.plus(boneless) ?: listOf(boneless)
                var mangoroll = Platillo(R.drawable.mangoroll, "Mango roll", "Rollo de pepino, aguacate, pollo y tocino con una mezcla de queso phila con trozos de piña por fuera del rollo y cubierto de una salsa de mango con chile.", "111")
                _platillos.value = _platillos.value?.plus(mangoroll) ?: listOf(mangoroll)
                var manchegoroll = Platillo(R.drawable.manchegoroll, "Manchego roll", "Rollo de pepino, aguacate, queso phila, tocino, res y gratinado con queso manchego.", "112")
                _platillos.value = _platillos.value?.plus(manchegoroll) ?: listOf(manchegoroll)
                var sushilitoroll = Platillo(R.drawable.sushilitoroll, "Suhilito roll", "Rollo de pepino, aguacate, queso phila, tocino, camarón, chiles toreados y un toque de salsa picosa por dentro del rollo.", "145")
                _platillos.value = _platillos.value?.plus(sushilitoroll) ?: listOf(sushilitoroll)
            }

            PlatilloEnum.PLATILLOS.name -> {
                var teriyaki = Platillo(R.drawable.teriyaki, "Teriyaki", "Verduras cebolla, brócoli, zanahoria, apio, calabaza con arroz, preparación e ingrediente a elegir, bañado con salsa teriyaki y ajonjolí espolvoreado.", "150")
                _platillos.value = _platillos.value?.plus(teriyaki) ?: listOf(teriyaki)
                var chickenmongolia = Platillo(R.drawable.chickenmongolia, "Chicken Mongolia", "Pollo, cebolla, pimientos verdes y rojos, chile de árbol, cacahuate, pollo capeado, salsa mongolia spicy.", "150")
                _platillos.value = _platillos.value?.plus(chickenmongolia) ?: listOf(chickenmongolia)
                var yakimeshi = Platillo(R.drawable.yakimeshi, "Yakimeshi especial", "Tazón de arroz frito preparado con verduras picadas finamente, res, pollo, tocino y tampico", "150")
                _platillos.value = _platillos.value?.plus(yakimeshi) ?: listOf(yakimeshi)
            }

            PlatilloEnum.EXTRAS.name -> {
                var tampico = Platillo(R.drawable.ordendetampico, "Orden de tampico", "", "35")
                _platillos.value = _platillos.value?.plus(tampico) ?: listOf(tampico)
            }

            PlatilloEnum.POSTRES.name -> {
                var paydequeso = Platillo(R.drawable.paydequeso, "Pay de queso", "", "60")
                _platillos.value = _platillos.value?.plus(paydequeso) ?: listOf(paydequeso)
                var flannapolitano = Platillo(R.drawable.flannapolitano, "Flan napolitano", "", "50")
                _platillos.value = _platillos.value?.plus(flannapolitano) ?: listOf(flannapolitano)
            }

            PlatilloEnum.BEBIDAS.name -> {
                var limonada = Platillo(R.drawable.limonada, "Limonada natural", "", "40")
                _platillos.value = _platillos.value?.plus(limonada) ?: listOf(limonada)
                var limonadamineral = Platillo(R.drawable.limonada, "Limonada mineral", "", "45")
                _platillos.value = _platillos.value?.plus(limonadamineral) ?: listOf(limonadamineral)
                var refresco = Platillo(R.drawable.refresco, "Refresco 600 ml", "", "35")
                _platillos.value = _platillos.value?.plus(refresco) ?: listOf(refresco)
            }
        }
    }
}
