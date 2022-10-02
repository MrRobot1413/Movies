package ua.mrrobot1413.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ua.mrrobot1413.movies.data.storage.AppDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.*
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.storage.dao.MoviesDao
import ua.mrrobot1413.movies.data.storage.model.Movie

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: MoviesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.moviesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovies() = runTest {
        val list = mutableListOf(
            Movie(
                1, 1, "Movie", false, "url", movieType = RequestType.UPCOMING
            )
        )
        dao.insertMoviesList(list)

        val result = dao.getMoviesListTable(RequestType.UPCOMING).first()
        assertThat(result).isEqualTo(list)
    }
}