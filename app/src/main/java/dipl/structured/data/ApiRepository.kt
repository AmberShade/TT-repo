package dipl.structured.data

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import dipl.structured.R
import dipl.structured.data.model.LoggedInUser
import dipl.structured.data.model.NewTask
import dipl.structured.data.model.Project
import dipl.structured.data.model.State
import dipl.structured.data.model.Task
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class ApiRepository(var user: LoggedInUser?, context: Context) {
        private val connManager:ConnectivityManager
        private var currentNetwork:Network?//not sure I need this separately
        var netState:NetworkCapabilities?=null

    private var BaseUrl:String
    private val retrofit:Retrofit
    val service:TaskApiService

        //todo: keep track of connection state, may aid in bundling requests. JobScheduler is a good hint
        init {
            BaseUrl=context.getString(R.string.server_address)
            retrofit=Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BaseUrl).build()
            service=retrofit.create(TaskApiService::class.java)
                this.connManager=getSystemService(context,ConnectivityManager::class.java)!!
                currentNetwork=connManager.activeNetwork
                //currentNetwork
                connManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network : Network) {
                                Log.e(TAG, "The default network is now: " + network)
                                //same as below
                        }

                        override fun onLost(network : Network) {
                                Log.e(TAG, "The application no longer has a default network. The last default network was " + network)
                                //netState=null//Need to check if it changes capabilities first
                        }

                        override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                                Log.e(TAG, "The default network changed capabilities: " + networkCapabilities)
                                netState=networkCapabilities

                        }
//Not sure I need this
//                        override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
//                                Log.e(TAG, "The default network changed link properties: " + linkProperties)
//                        }
                })
        }


//        companion object{
//                private val BASE_URL="${R.string.server_address}"//"http://176.116.170.251/api"//on address changes keep track of address in login method
//                private val retrofit=Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()
//
//                val service: TaskApiService by lazy {retrofit.create(TaskApiService::class.java)}
//        }
        //todo: find a way to bundle network requests when applicable

    interface TaskApiService{
//@Multipart
//        @POST("http://176.116.170.251/login")//moved to LoginDataSource
//        suspend fun login(@Part("login") login: String, @Part("password") pass:String):Response<String>

        @GET("statements")
        suspend fun getStates():Response<List<State>>

        //todo: on user session error attempt a relogin on existing credentials

        //get a certain task
        @GET("Taskitems/{id}")
        suspend fun getTask(@Path("id") id:String, @Header("Bearer") auth: String):Response<Task>//response to catch auth errors
        //get all tasks
        //not sure why I need it
//        @GET("Taskitems")
//        suspend fun getTasks():Response<List<Task>>
        @GET("Taskitems/{uId}/{pId}")//not implemented in backend, but the way that would work is awful
        suspend fun getProjTasks(@Path("uId") userId:String,@Path("pId") projId:String, @Header("Bearer") auth: String):Response<List<Task>>//response to catch auth errors


        //get a certain task
        @PUT("Taskitems/{id}")
        suspend fun putTask(@Path("id") id:String, item:Task, @Header("Bearer") auth: String)//figure how to get response here, too

        @POST("Taskitems")
        suspend fun putTask(item: NewTask, @Header("Bearer") auth: String)//find a better, centralized way of control for bearer header

        @DELETE("Taskitems/{id}")
        suspend fun putTask(@Path("id") id:String, @Header("Bearer") auth: String)


        //projects for current user
        @GET("Project/{id}")
        suspend fun getProjects(@Path("id") id:String, @Header("Bearer") auth: String):Response<List<Project>>

        //certain project
        @GET("Project/Detail/{id}")
        suspend fun getProjectInfo(@Path("id") id:String, @Header("Bearer") auth: String): Response<Project>
        //all projects, useless for now
        /*@GET("Project/")
        suspend fun getAllProjects():Response<List<Project>>*/

        //to be implemented later. No new projects at this time
//        @POST("Project")
//        suspend fun postProject(item:Project, @Header("Bearer") auth: String)
        @PUT("Project/{id}")//may remove this, too. Not editing projects at this time
        suspend fun putProject(@Path("id") id:String, item:Project, @Header("Bearer") auth: String)
        //no project removal at this time
//        @DELETE("Project/{id}")
//        suspend fun deleteProject(@Path("id") id:String, @Header("Bearer") auth: String)
    }
}