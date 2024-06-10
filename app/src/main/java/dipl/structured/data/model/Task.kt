package dipl.structured.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.sql.Date
import java.sql.Timestamp

//class DateConverter{
//@TypeConverter
//fun toString(entity:Date)
//}

@Entity(tableName="task_list")
@TypeConverters(StringListConverter::class)
data class Task (
    @PrimaryKey val Id: String,
    @ColumnInfo(name="title") var Title: String,
    @ColumnInfo(name="descr") var Description: String?,
    @ColumnInfo(name="start") var Start: Timestamp?,
    @ColumnInfo(name="end") var End: Timestamp?,
    @ColumnInfo(name="created") val Created: Timestamp,
    @ColumnInfo(name="author") var AuthorId: String,
    @ColumnInfo(name="state") var State: Int
)
data class NewTask (
    var Title: String,
    var Description: String?,
    var Start: Date?,
    var End: Date?,
    val Created: Date,
    var AuthorId: String,
    var State: Int
)