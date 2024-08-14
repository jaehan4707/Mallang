package com.chill.mallang.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class RetryInterceptor
    @Inject
    constructor(
        private val maxRetryCount: Int,
        private val retryStatus: Int,
    ) : Interceptor {
        private var retryCount = 0

        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            var response: Response = chain.proceed(request)
            Log.d("jaehan","재요청 중")
            while (!response.isSuccessful && response.code == retryStatus && retryCount < maxRetryCount) {
                retryCount++
                response.close()
                response = chain.proceed(request)
            }
            return response
        }
    }
