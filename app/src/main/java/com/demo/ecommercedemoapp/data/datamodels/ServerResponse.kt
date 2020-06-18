package com.demo.ecommercedemoapp.data.datamodels



data class ServerResponse<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): ServerResponse<T> = ServerResponse(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ServerResponse<T> =
            ServerResponse(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ServerResponse<T> = ServerResponse(status = Status.LOADING, data = data, message = null)
    }
}