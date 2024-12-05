package mx.edu.potros.foodorder.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite
import mx.edu.potros.foodorder.Models.Mesa

class HomeViewModel: ViewModel() {
    private val _mesas = MutableLiveData<List<Mesa>>()
    val mesas: LiveData<List<Mesa>> get() = _mesas

    fun cargarMesas() {
        viewModelScope.launch {
            _mesas.value = Appwrite.database.getMesas()
        }
    }
}