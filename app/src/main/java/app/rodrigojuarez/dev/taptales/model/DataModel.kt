package app.rodrigojuarez.dev.taptales.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tales")
data class Tale(
    @PrimaryKey
    val slug: String,
    val title: String,
    val readingTime: String,
    val paragraphs: List<String>
)
