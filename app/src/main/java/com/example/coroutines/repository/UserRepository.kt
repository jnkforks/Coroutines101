package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import com.example.coroutines.threading.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val apiService: ApiService,
    private val dispatchers: DispatcherProvider
) {

    @ExperimentalCoroutinesApi
    fun userDetails(login: String): Flow<Result<UserDetails>> {
        return flow {
            val userDetails = apiService.userDetails(login)
            emit(Result.success(userDetails))
        }
            .catch { emit(Result.failure(it)) }
            .flowOn(dispatchers.io())
    }

// Retry if failed
//    @ExperimentalCoroutinesApi
//    fun userDetails(login: String): Flow<Result<UserDetails>> {
//        return flow {
//            val userDetails = apiService.userDetails(login)
//            emit(Result.success(userDetails))
//        }.retry(retries = 2) { t ->
//            (t is Exception).also {
//                if (it) delay(DELAY_ONE_SECOND)
//            }
//        }
//            .catch { emit(Result.failure(it)) }
//            .flowOn(dispatchers.io())
//    }
}
