package ua.mrrobot1413.movies.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import java.util.*

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().isO3Language
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().isO3Language
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().isO3Language
    ): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse
}