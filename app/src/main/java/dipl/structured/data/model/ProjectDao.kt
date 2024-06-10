package dipl.structured.data.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProjectDao {
    //what are we doing with projects?
    @Query("Select * from project_table where id=:reqId")
    suspend fun getProject(reqId:String):Project?
    @Query("Select * from project_table where users like :usId ")
    suspend fun getProjects(usId:String):List<Project>?

    //suspend fun putProject(id:String, item:Project)
}