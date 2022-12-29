package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.UpdateTaskRequest
import ro.sapientia.android_11eloadas.model.UpdateTaskResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class UpdateTaskViewModelFactory(
    private val repository: TrackerRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdateTaskViewModel(repository) as T
    }

}

class UpdateTaskViewModel(val repository: TrackerRepository): ViewModel() {
    var updateTaskResult: MutableLiveData<UpdateTaskResult> = MutableLiveData()
    var responseMessage: MutableLiveData<String> = MutableLiveData()

    fun updateTask(request: UpdateTaskRequest){
        viewModelScope.launch {
            try {
                val response = repository.updateTask(MyApplication.token,request)
                if(response.isSuccessful){
                    responseMessage.value=response.body()?.message
                    updateTaskResult.value=UpdateTaskResult.SUCCESS
                    Log.i("xxx",response.body().toString())
                }else{
                    responseMessage.value=response.body()?.message
                    updateTaskResult.value=UpdateTaskResult.INVALID_CREDENTIALS
                    Log.i("xxx","Invalid request "+response.errorBody()?.string()+" "+responseMessage.value)
                }
            }catch (e:Exception){
                updateTaskResult.value=UpdateTaskResult.UNKNOWN_ERROR
                Log.i("xxx",e.toString())
            }
        }
    }
}