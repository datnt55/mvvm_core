package com.library.core.model.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> T): ResponseResult<T> {
        return try {
            ResponseResult.Success(call.invoke())
        } catch (e: Exception) {
            error(e.message)
        }
    }

    private fun <T> error(msg: String?): ResponseResult<T> {
        return ResponseResult.Error(msg!!)
    }

}

fun <T> resultLiveData(scope: CoroutineScope, call: suspend () -> ResponseResult<T>): LiveData<ResponseResult<T>> {
    return liveData(scope.coroutineContext) {
        emit(ResponseResult.Loading)

        withContext(Dispatchers.IO) {
            emit(call.invoke())
        }
    }
}