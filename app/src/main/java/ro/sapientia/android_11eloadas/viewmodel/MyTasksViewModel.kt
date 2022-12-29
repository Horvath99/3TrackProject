package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.MyTaskItem
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class MyTasksViewModelFactory(
    private val repository: TrackerRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyTasksViewModel(repository) as T
    }
}

class MyTasksViewModel(val repository: TrackerRepository) : ViewModel() {
    var tasksList = MutableLiveData<List<MyTaskItem>>()

    fun getMyTasks(){
        viewModelScope.launch {
            try {
                val response = repository.getMyTasks(MyApplication.token)
                if(response.isSuccessful){
                    tasksList.value=response.body()
                }
                else{
                    Log.i("xxx-uvm",response.message())
                }
            }catch (e: Exception){
                Log.i("xxx",e.toString())
            }
        }
    }
}