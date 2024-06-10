package dipl.structured.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state_list")
data class State (
    @PrimaryKey val Id: Int,
    @ColumnInfo(name="title") var Title: String
)