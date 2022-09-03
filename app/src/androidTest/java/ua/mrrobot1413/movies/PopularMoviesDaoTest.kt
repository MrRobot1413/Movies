package ua.mrrobot1413.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.PopularMoviesDao
import ua.mrrobot1413.movies.data.storage.model.PopularMovie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PopularMoviesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: PopularMoviesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.popularMoviesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovies() = runTest {
        val list = mutableListOf(PopularMovie(
            1, "Movie", false, "url"
        ))
        dao.insertMoviesList(list)

        dao.getMoviesListTable().collect {
            assertThat(it).contains(list)
        }
    }
}