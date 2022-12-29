package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.CreateTaskRequest
import ro.sapientia.android_11eloadas.model.CreateTaskResponse
import ro.sapientia.android_11eloadas.model.CreateTaskResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class CreateTaskViewModelFactory(
    private val repository: TrackerRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateTaskViewModel(repository) as T
    }

}


class CreateTaskViewModel(val repository: TrackerRepository): ViewModel() {

    var createTaskResult: MutableLiveData<CreateTaskResult> = MutableLiveData()
    var responseMessage : MutableLiveData<String> = MutableLiveData()

    fun addTask(request : CreateTaskRequest){
        viewModelScope.launch {
            try{
                val response = repository.createTask(MyApplication.token,request)
                if(response.isSuccessful){
                    responseMessage.value = response.body()?.message
                    createTaskResult.value=CreateTaskResult.SUCCESS
                    Log.i("xxx",response.body().toString())
                }else{
                    responseMessage.value = response.body()?.message
                    createTaskResult.value=CreateTaskResult.INVALID_CREDENTIALS
                    Log.i("xxx","Invalid request "+response.errorBody()?.string()+" "+responseMessage.value)
                }

            }catch (e:Exception){
                createTaskResult.value = CreateTaskResult.UNKNOWN_ERROR
                Log.i("xxx",e.toString())
            }
        }
    }
}