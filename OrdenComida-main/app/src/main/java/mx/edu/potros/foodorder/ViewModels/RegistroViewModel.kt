package mx.edu.potros.foodorder.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite

class RegistroViewModel: ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult
    var correo = ""
    var contraseña = ""
    var contraseñaVerificada = ""

    fun createAccount() {
        viewModelScope.launch {
            val result = Appwrite.account.register(correo, contraseña)
            _loginResult.value = result != null
        }
    }
}