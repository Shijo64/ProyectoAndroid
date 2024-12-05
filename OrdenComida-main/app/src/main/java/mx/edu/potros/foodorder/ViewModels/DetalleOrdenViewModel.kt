package mx.edu.potros.foodorder.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Data.AppDatabase
import mx.edu.potros.foodorder.Data.Orden
import mx.edu.potros.foodorder.Data.PlatilloOrden
import mx.edu.potros.foodorder.Repositorys.OrdenRepository
import mx.edu.potros.foodorder.Repositorys.PlatillosRepository
import mx.edu.potros.foodorder.Singleton.Appwrite

class DetalleOrdenViewModel(application: Application) : AndroidViewModel(application) {
    private val _platillos = MutableLiveData<List<PlatilloOrden>>()
    val platillos: LiveData<List<PlatilloOrden>> = _platillos
    private val _totalOrden = MutableLiveData<Int>()
    val totalOrden: LiveData<Int> = _totalOrden
    private val _orden = MutableLiveData<Orden>()
    val orden: LiveData<Orden> = _orden
    private val _addOrdenResult = MutableLiveData<String>()
    val addOrdenResult: LiveData<String> = _addOrdenResult
    private val _addPlatillosResult = MutableLiveData<String>()
    val addPlatillosResult: LiveData<String> = _addOrdenResult

    private val platillosRepository: PlatillosRepository
    private val ordenRepository: OrdenRepository

    init {
        val platillosDao = AppDatabase.getInstance(application).platillosDao()
        val ordenDao = AppDatabase.getInstance(application).ordenDao()
        ordenRepository = OrdenRepository(ordenDao)
        platillosRepository = PlatillosRepository(platillosDao)
    }

    fun getPlatillosByNombreOrden(nombreOrden: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _platillos.postValue(platillosRepository.getPlatillosByNombreOrden(nombreOrden))
        }
    }

    fun getTotalOrden(platillos: List<PlatilloOrden>) {
        viewModelScope.launch(Dispatchers.IO) {
            var total = 0
            for (platillo in platillos) {
                total += platillo.precio.toInt()
            }
            _totalOrden.postValue(total)
        }
    }

    fun enviarOrden(numeroMesa: String, nombreOrden: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _addOrdenResult.postValue(Appwrite.database.crearOrden(numeroMesa, nombreOrden))
        }
    }

    fun enviarPlatillo(platillo: PlatilloOrden) {
        viewModelScope.launch(Dispatchers.IO) {
            _addPlatillosResult.postValue(Appwrite.database.crearPlatillo(platillo))
        }
    }
}