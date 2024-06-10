package dipl.structured.data

import androidx.annotation.WorkerThread
import dipl.structured.data.model.Project
import dipl.structured.data.model.ProjectDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class ProjectRepository(private val projectDao: ProjectDao, private val api:ApiRepository, private val scope:CoroutineScope, private val loginRepo:LoginRepository) {
//    //todo: find a way to bundle network requests when applicable
    @WorkerThread
    suspend fun getProj(id:String):Project?{
        //make some sort of age on cache
        var webProject:Project?=null
        val call=scope.launch{
            //if (api.user==null) return null //check if user is logged in? Somehow
            try{
                var response=api.service.getProjectInfo(id,api.user!!.accessToken)
                if (response.code()==401)//I suppose this should happen if we've timed out
                {
                    loginRepo.login(api.user!!.displayName,api.user!!.pwd)
                    response=api.service.getProjectInfo(id,api.user!!.accessToken)
                }
                if (response.isSuccessful) {
                    webProject =
                        response.body()!!///actually oops. Our backend returns a bunch of trash to be sorted first, not a project in reasonable form
                }
            }
            catch(ex:Exception){
                //??not encountered errors to process yet
            }
        }
        val dbProject=projectDao.getProject(id)//search an item by id
        call.join()
        if (webProject!=null&&dbProject!=webProject!!){//api result is prevalent
            //projectDao.putProject(id, webProject!!)
            return webProject
        }

        //actually, api should have priority, and queries should be parallel
        return null//no luck either way?
    }
    @WorkerThread
    suspend fun getProjects(id:String=loginRepo.user!!.userId): Flow<List<Project>?> {
        //a version with cache age --unapplicable, only if connection is restricted or unavailable
        var webProjects:List<Project>?=null
        val call=scope.launch{
            //if (api.user==null) return null //check if user is logged in? Somehow
            try{
                var response=api.service.getProjects(id,api.user!!.accessToken)
                if (response.code()==401)//I suppose this should happen if we've timed out
                {
                    loginRepo.login(api.user!!.displayName,api.user!!.pwd)
                    response=api.service.getProjects(id,api.user!!.accessToken)
                }
                if (response.isSuccessful) webProjects = response.body()!!///actually oops. Our backend returns a bunch of trash to be sorted first, not a project in reasonable form

            }
            catch(ex:Exception){
                //??not encountered errors to process yet
            }
        }
        val dbProjects=projectDao.getProjects(id)//search an item by id
        call.join()
        if (webProjects!=null&&dbProjects!=webProjects!!){//may need a better way to compare lists here
            //for each non-conforming
            //projectDao.putProject(id, webProjects!!)
            return flowOf(webProjects)
        }

        //actually, api should have priority, and queries should be parallel
        return flowOf(null)//no luck either way?
    }
}