package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.User
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class MyUserViewModelFactory(
    private val repository: TrackerRepository
    ): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyUserViewModel(repository) as T
    }
}

class MyUserViewModel(val repository: TrackerRepository) : ViewModel() {
    var myUser = MutableLiveData<User>()
    var userList = MutableLiveData<List<User>>()
    fun getMyUser(){
        viewModelScope.launch {
            try{
                val response = repository.getUser(MyApplication.token)
                if(response.isSuccessful){
                    myUser.value=response.body()
                }
                else{
                    Log.i("xxx-uvm",response.message())
                }
            }catch (e: Exception){
                Log.i("xxx",e.toString())
            }
        }
    }



    fun readUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsers(MyApplication.token)
                if(response.isSuccessful) {
                    userList.value = response.body()
                } else{
                    Log.i("xxx-uvm", response.message())
                }
            } catch (e: java.lang.Exception) {
                Log.i("xxx", e.toString())
            }
        }
    }

}