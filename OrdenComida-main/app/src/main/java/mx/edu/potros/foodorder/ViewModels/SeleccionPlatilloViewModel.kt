package mx.edu.potros.foodorder.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Data.AppDatabase
import mx.edu.potros.foodorder.Data.PlatilloOrden
import mx.edu.potros.foodorder.Repositorys.PlatillosRepository

class SeleccionPlatilloViewModel(application: Application) : AndroidViewModel(application) {

    private val _platillos = MutableLiveData<List<PlatilloOrden>>()
    val platillos: LiveData<List<PlatilloOrden>> = _platillos
    private val platillosRepository: PlatillosRepository

    init {
        val platillosDao = AppDatabase.getInstance(application).platillosDao()
        platillosRepository = PlatillosRepository(platillosDao)
    }

    fun guardarPlatillo(platillo: PlatilloOrden) {
        viewModelScope.launch(Dispatchers.IO) {
            platillosRepository.insertPlatillo(platillo)
        }
    }

    fun getPlatillosByNombreOrden(nombreOrden: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _platillos.value = platillosRepository.getPlatillosByNombreOrden(nombreOrden)
        }
    }
}