package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.UpdateProfileRequest
import ro.sapientia.android_11eloadas.model.UpdateProfileResult
import ro.sapientia.android_11eloadas.model.UpdateTaskResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class UpdateProfileViewModelFactory(
    private val repository: TrackerRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdateProfileViewModel(repository) as T
    }
}

class UpdateProfileViewModel(val repository: TrackerRepository):ViewModel() {
    var updateProfileResult : MutableLiveData<UpdateProfileResult> = MutableLiveData()
    var responseMessage : MutableLiveData<String> = MutableLiveData()

    fun updateProfile(request: UpdateProfileRequest){
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(MyApplication.token,request)
                if(response.isSuccessful){
                    responseMessage.value = response.body()?.message
                    updateProfileResult.value = UpdateProfileResult.SUCCESS
                    Log.i("xxx",response.body().toString())
                }else{
                    responseMessage.value=response.body()?.message
                    updateProfileResult.value = UpdateProfileResult.INVALID_CREDENTIALS
                    Log.i("xxx","Invalid credentials"+response.errorBody()?.string())
                }

            }catch (e:Exception){
                updateProfileResult.value=UpdateProfileResult.UNKNOWN_ERROR
                Log.i("xxx",e.toString())

            }
        }
    }
}