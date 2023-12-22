package app.rodrigojuarez.dev.taptales.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tales")
data class Tale(
    @PrimaryKey
    val slug: String,
    val title: String,
    val paragraphs: List<String>,
    var words: String,
    var date: LocalDateTime
)