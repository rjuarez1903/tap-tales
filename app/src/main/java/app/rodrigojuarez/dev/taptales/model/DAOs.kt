package app.rodrigojuarez.dev.taptales.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaleDao {
    @Query("SELECT * FROM tales")
    fun getAllTales(): LiveData<List<Tale>>

    @Query("SELECT * FROM tales WHERE title = :title")
    fun getTaleByTitle(title: String): Tale?

    @Query("SELECT * FROM tales WHERE slug = :slug")
    fun getTaleBySlug(slug: String): Tale?

    @Insert
    fun insertTale(tale: Tale)

    @Delete
    fun deleteTale(tale: Tale)
}