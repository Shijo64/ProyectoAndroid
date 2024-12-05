package mx.edu.potros.foodorder.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite

class LoginViewModel: ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = Appwrite.account.login(email, password)
            _loginResult.value = result != null
        }
    }

    fun checkIfLoggedIn() {
        viewModelScope.launch {
            val result = Appwrite.account.getLoggedIn()
            _loginResult.value = result != null
        }
    }
}