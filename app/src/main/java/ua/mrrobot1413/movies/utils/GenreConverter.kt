package ua.mrrobot1413.movies.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.mrrobot1413.movies.data.storage.model.Genre
import java.util.*

class GenreConverter {
    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun stringToList(data: String?): List<Genre> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Genre>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Genre>): String {
        return gson.toJson(someObjects)
    }
}