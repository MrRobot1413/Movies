package ua.mrrobot1413.movies.data.network.model

data class Result<out T>(
    val status: RequestStatus,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): Result<T> =
            Result(RequestStatus.SUCCESS, data, null)

        fun <T> error(data: T?, message: String?): Result<T> =
            Result(RequestStatus.ERROR, data, message)

        fun <T> loading(data: T?): Result<T> =
            Result(RequestStatus.LOADING, data, null)
    }
}