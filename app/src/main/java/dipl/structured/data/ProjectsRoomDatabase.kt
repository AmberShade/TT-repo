package dipl.structured.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dipl.structured.data.model.Project
import dipl.structured.data.model.ProjectDao
import kotlinx.coroutines.CoroutineScope

@Database(entities= arrayOf(Project::class),version=1, exportSchema = false)
abstract class ProjectsRoomDatabase:RoomDatabase() {
    abstract fun Dao():ProjectDao
    //todo : merge databases to a single one with multiple tables
    companion object{
        @Volatile
        private var INSTANCE:ProjectsRoomDatabase?=null
        fun getDatabase(context: Context, scope: CoroutineScope):ProjectsRoomDatabase{
            return INSTANCE?:synchronized(this){
                val instance=
                    Room.databaseBuilder(context.applicationContext,ProjectsRoomDatabase::class.java,"projects_database").addCallback(ProjectsDatabaseCallback(scope)).build()
                INSTANCE=instance
                instance}
        }
    }
    private class ProjectsDatabaseCallback(private val scope: CoroutineScope):Callback(){
        //in case I need stuff done on this db. May be removed later

    }
}