package ro.sapientia.android_11eloadas.repository

import retrofit2.Response
import retrofit2.Retrofit
import ro.sapientia.android_11eloadas.api.RetrofitInstance
import ro.sapientia.android_11eloadas.model.*

class TrackerRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getUsers(token: String): Response<List<User>> {
        return RetrofitInstance.api.getUsers(token)
    }

    suspend fun getUser(token: String): Response<User> {
        return RetrofitInstance.api.getMyUser(token)
    }
    suspend fun getMyTasks(token: String): Response<List<MyTaskItem>>{
        return RetrofitInstance.api.getMyTasks(token)
    }
    suspend fun getDepartments(token: String) : Response<List<Department>>{
        return RetrofitInstance.api.getDepartments(token)
    }
    suspend fun createTask(token: String,request: CreateTaskRequest) : Response<CreateTaskResponse>{
        return RetrofitInstance.api.createTask(token,request)
    }
    suspend fun updateTask(token: String,request: UpdateTaskRequest) : Response<UpdateTaskResponse>{
        return RetrofitInstance.api.updateTask(token,request)
    }
    suspend fun updateProfile(token : String,request: UpdateProfileRequest) : Response<UpdateProfileResponse>{
        return RetrofitInstance.api.updateProfile(token,request)
    }
}