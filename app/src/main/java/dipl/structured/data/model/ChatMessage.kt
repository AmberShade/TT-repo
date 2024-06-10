package dipl.structured.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "messages_table")
data class ChatMessage (
    @PrimaryKey val id:String,
    @ColumnInfo(name="author_id") var authorId:String,
    @ColumnInfo(name="task_id") var taskId:String,
    @ColumnInfo(name="timestamp") var timestamp: Timestamp,
    @ColumnInfo(name="message") var message: String,
    @ColumnInfo(name="reaction") var reaction: Int//may need to make a separate entity for reacions and put a list of them here
)