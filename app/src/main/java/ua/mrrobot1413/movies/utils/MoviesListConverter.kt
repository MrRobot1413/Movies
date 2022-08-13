package ua.mrrobot1413.movies.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.mrrobot1413.movies.data.storage.model.PopularMovie

class MoviesListConverter {
    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun stringToList(data: String?): List<PopularMovie>? {
        if (data == null) {
            return null
        }

        val listType = object : TypeToken<List<PopularMovie>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<PopularMovie>): String {
        return gson.toJson(someObjects)
    }
}