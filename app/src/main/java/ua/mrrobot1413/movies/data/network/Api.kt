package ua.mrrobot1413.movies.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import java.math.BigInteger
import java.util.*

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): MoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): DetailedMovie

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(@Path("id") id: Int): MoviesResponse
}