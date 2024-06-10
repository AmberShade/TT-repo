package dipl.structured.data

import android.content.Context
import android.util.Log
import dipl.structured.R
import dipl.structured.data.model.LoggedInUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(context: Context, private val scope: CoroutineScope) {
    @Serializable
    data class LoginResponse(val access_token:String, val username:String, val id:String)
    @Serializable
    data class LoginRequest(val login:String, val password: String)

    private var BaseUrl:String
    private val retrofit:Retrofit
    val service: LoginApiService

    init{
        Log.d("parameter","Login address: "+context.getString(R.string.login_address))
        BaseUrl=context.getString(R.string.login_address)
        retrofit=Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BaseUrl).build()
        service=retrofit.create(LoginApiService::class.java)
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        //check if network is available first
        try {
        var userdata:Response<String>?
        runBlocking {userdata=service.login(Json.encodeToString(LoginRequest(username, password)))}
            if (userdata!!.code()==401) {
                return Result.Error(Exception("Incorrect username/password"))//wrong login/pass code here
            }
            else if (userdata!!.isSuccessful) {
                val info = Json.decodeFromString<LoginResponse>(userdata!!.body()!!)
                return Result.Success(LoggedInUser(info.id,username,info.access_token,password))
            }
            else return Result.Error(Exception("Failure of unknown nature, error code ${userdata!!.code()}"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in: " + e.message, e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        //no backend support, just remove stored user data
    }

//    companion object {
//        private val BASE_URL:String=R.string.login_address//"http://176.116.170.251/login/"//"@data_consts/login_address"//"http://176.116.170.251/login/"//on address changes keep track of address in login method
//        private val retrofit=
//            Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()
//
//        val service: LoginApiService by lazy {retrofit.create(LoginApiService::class.java)}
//    }
    interface LoginApiService{//not sure I want it async, let's see if it allows me to make network requests in general scope

        //@Multipart
        @Headers("Content-Type:application/json")
        @POST("login")
        suspend fun login(@Body body:String):Response<String>//@Part("login") login: String, @Part("password") pass:String): Response<String>
    }
}