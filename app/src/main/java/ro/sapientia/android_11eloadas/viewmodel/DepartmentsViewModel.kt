package ro.sapientia.android_11eloadas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.model.Department
import ro.sapientia.android_11eloadas.repository.TrackerRepository


class DepartmentsViewModelFactory(
    private val repository : TrackerRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DepartmentsViewModel(repository) as T
    }
}

class DepartmentsViewModel(val repository: TrackerRepository) : ViewModel() {
    var departmentList = MutableLiveData<List<Department>>()

    fun getDepartments(){
        viewModelScope.launch {
            try {
                val response =repository.getDepartments(MyApplication.token)
                if(response.isSuccessful){
                    departmentList.value=response.body()
                }else{
                    Log.i("xxx-uvm",response.message())
                }
            }catch (e:Exception){
                Log.i("xxx",e.toString())
            }
        }
    }

}