package dipl.structured.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dipl.structured.data.model.Task
import dipl.structured.data.model.TaskDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TasksRoomDatabase:RoomDatabase() {
    abstract fun Dao():TaskDao
    companion object{
        @Volatile
        private var INSTANCE:TasksRoomDatabase?=null
        fun getDatabase(context:Context, scope:CoroutineScope):TasksRoomDatabase{
            return INSTANCE?:synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,TasksRoomDatabase::class.java,"tasks_database").addCallback(TasksDatabaseCallback(scope)).build()
            INSTANCE=instance
            instance}
        }
    }
    private class TasksDatabaseCallback(private val scope:CoroutineScope):RoomDatabase.Callback(){
        //in case I need stuff done on this db. May be removed later

    }

}