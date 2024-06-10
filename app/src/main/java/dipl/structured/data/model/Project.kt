package dipl.structured.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.sql.Timestamp

class StringListConverter{
    @TypeConverter
    fun toString(entity:List<String>):String{
        return Json.encodeToString(entity.toTypedArray())
    }
    @TypeConverter
    fun fromString(serialized:String):List<String>{
        return Json.decodeFromString<Array<String>>(serialized).toList()
    }
    @TypeConverter
    fun toLong(entity:Timestamp):Long{
        return entity.time
    }
    @TypeConverter
    fun fromLong(serialized:Long):Timestamp{
        return Timestamp(serialized)
    }
}

@Entity(tableName = "project_table")
@TypeConverters(StringListConverter::class)
data class Project (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") var Title: String,
    @ColumnInfo(name = "descr") var Description: String?,//I believe project has to have creators and other complicated stuff
    @ColumnInfo(name = "users") var Users: List<String>,//their IDs
    @ColumnInfo(name = "tasks") var Tasks: List<String>,//is it actually properly serializing a list?
    @ColumnInfo(name = "updated") var updated: Timestamp//to count cache age
)