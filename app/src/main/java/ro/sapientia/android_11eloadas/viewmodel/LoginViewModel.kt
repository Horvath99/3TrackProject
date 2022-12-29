package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.LoginRequest
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.model.LoginResult

class LoginViewModelFactory(
    private val repository: TrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}

class LoginViewModel(val repository: TrackerRepository) : ViewModel() {

    var loginResult: MutableLiveData<LoginResult> = MutableLiveData()

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = repository.login(request)
                if (response.isSuccessful) {

                    MyApplication.token = response.body()!!.token
                    MyApplication.deadline = response.body()!!.deadline

                    loginResult.value = LoginResult.SUCCESS
                    Log.i("xxx", response.body().toString())
                } else {
                    loginResult.value = LoginResult.INVALID_CREDENTIALS
                    Log.i("xxx", "Invalid credentials " + response.errorBody().toString()  )
                }
            } catch (e: Exception) {
                loginResult.value = LoginResult.UNKNOWN_ERROR
                Log.i("xxx", e.toString())
            }
        }
    }
}