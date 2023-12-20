package app.rodrigojuarez.dev.taptales.model

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [Tale::class], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taleDao(): TaleDao
}

var LocalAppDatabase = staticCompositionLocalOf<AppDatabase> {
    error("No Database")
}

class DBTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<String> {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromMap(list: List<String>): String {
        return gson.toJson(list)
    }
}
