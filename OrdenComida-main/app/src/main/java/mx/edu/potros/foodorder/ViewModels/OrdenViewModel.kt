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
import mx.edu.potros.foodorder.Repositorys.OrdenRepository

class OrdenViewModel(application: Application) : AndroidViewModel(application) {

    private val _ordenes = MutableLiveData<List<Orden>>()
    val ordenes: LiveData<List<Orden>> = _ordenes
    private val ordenRepository: OrdenRepository

    init {
        val ordenDao = AppDatabase.getInstance(application).ordenDao()
        ordenRepository = OrdenRepository(ordenDao)
    }

    fun getOrdenes() {
        viewModelScope.launch(Dispatchers.IO) {
            _ordenes.postValue(ordenRepository.getOrdenes())
        }
    }

    fun guardarOrden(orden: Orden) {
        viewModelScope.launch(Dispatchers.IO) {
            ordenRepository.insertOrden(orden)
        }
    }
}