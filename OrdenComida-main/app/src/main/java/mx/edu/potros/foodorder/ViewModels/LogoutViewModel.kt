package mx.edu.potros.foodorder.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.potros.foodorder.Managers.Appwrite

class LogoutViewModel: ViewModel()  {

    fun logout() {
        viewModelScope.launch {
            Appwrite.account.logout()
        }
    }
}