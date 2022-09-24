package ua.mrrobot1413.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel

class SearchMoviesPagingSource(
    private val api: Api,
    private val query: String
) : PagingSource<Int, MovieResponseModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseModel> {
        val page = params.key ?: 1
        return try {
            val response = api.searchMovies(query, page)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            println("Err ${exception.message}")
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MovieResponseModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}