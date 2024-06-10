package dipl.structured

import android.app.Application
import dipl.structured.data.ApiRepository
import dipl.structured.data.LoginDataSource
import dipl.structured.data.LoginRepository
import dipl.structured.data.ProjectRepository
import dipl.structured.data.ProjectsRoomDatabase
import dipl.structured.data.TaskRepository
import dipl.structured.data.TasksRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TasksApplication: Application() {
    //a class to hold application-wide global things, replacer for a service
    val applicationScope = CoroutineScope(SupervisorJob())
    //var loginState:LoggedInUser?=null

    private val loginGate:LoginDataSource by lazy {LoginDataSource(applicationContext,applicationScope)}
    val loginRepo:LoginRepository by lazy {LoginRepository(loginGate)}

    private val api:ApiRepository by lazy {ApiRepository(loginRepo.user, applicationContext)}

    private val taskDb by lazy {TasksRoomDatabase.getDatabase(this,applicationScope)}
    val taskRepo by lazy {TaskRepository(taskDb.Dao(), api)}

    private val projectDb by lazy { ProjectsRoomDatabase.getDatabase(this,applicationScope)}
    val projectRepo by lazy {ProjectRepository(projectDb.Dao(),api, applicationScope,loginRepo)}
}