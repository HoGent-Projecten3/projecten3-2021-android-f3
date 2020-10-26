package com.example.faith.api

import com.example.faith.utilities.NO_AUTH_HEADER_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

    @Singleton
    class MyServiceInterceptor @Inject constructor() : Interceptor {
        private var sessionToken: String? = ""
        fun setSessionToken(sessionToken: String?) {
            this.sessionToken = sessionToken
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val requestBuilder = request.newBuilder()
            if (request.header(NO_AUTH_HEADER_KEY) == null) {
                // needs credentials
                if (sessionToken == null) {
                    throw RuntimeException("Session token should be defined for auth apis")
                } else {
                    requestBuilder.addHeader("Authorization",
                            "Bearer $sessionToken")
                }
            }
            return chain.proceed(requestBuilder.build())
        }
    }
