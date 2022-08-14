package ua.mrrobot1413.movies.utils

import kotlinx.coroutines.flow.*
import ua.mrrobot1413.movies.data.network.model.Result

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Result.loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Result.success(it) }
        } catch (throwable: Throwable) {
            query().map { Result.error(it, throwable.message) }
        }
    } else {
        query().map { Result.success(it) }
    }

    emitAll(flow)
}