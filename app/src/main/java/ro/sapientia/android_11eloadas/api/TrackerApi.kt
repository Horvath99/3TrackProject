package ro.sapientia.android_11eloadas.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ro.sapientia.android_11eloadas.model.*
import ro.sapientia.android_11eloadas.util.Constants

interface TrackerApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET(Constants.GET_USERS_URL)
    suspend fun getUsers(@Header("token") token: String): Response<List<User>>

    @GET(Constants.GET_MY_USER_URL)
    suspend fun getMyUser(@Header("token") token: String) : Response<User>

    @GET(Constants.GET_MY_TASKS_URL)
    suspend fun getMyTasks(@Header("token") token: String): Response<List<MyTaskItem>>

    @GET(Constants.GET_DEPARTMENTS)
    suspend fun getDepartments(@Header("token") token : String): Response<List<Department>>

    @POST(Constants.CREATE_TASK)
    suspend fun createTask(@Header("token") token :String,@Body request: CreateTaskRequest) : Response<CreateTaskResponse>

    @POST(Constants.UPDATE_TASK)
    suspend fun updateTask(@Header("token") token : String,@Body request: UpdateTaskRequest):Response<UpdateTaskResponse>

    @POST(Constants.UPDATE_PROFILE)
    suspend fun updateProfile(@Header("token") token:String,@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>
}