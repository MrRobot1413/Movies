package ua.mrrobot1413.movies.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.mrrobot1413.movies.data.storage.model.Movie

class MoviesListConverter {
    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun stringToList(data: String?): List<Movie>? {
        if (data == null) {
            return null
        }

        val listType = object : TypeToken<List<Movie>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Movie>): String {
        return gson.toJson(someObjects)
    }
}