package com.quarx2k.cloudflare

import java.nio.charset.Charset
import org.springframework.util.StreamUtils
import java.io.IOException
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse


class RequestResponseLoggingInterceptor : ClientHttpRequestInterceptor {
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        logRequest(request, body)
        val response = execution.execute(request, body)
        logResponse(response)
        return response
    }

    private fun logRequest(request: HttpRequest, body: ByteArray) {
        println("===========================request begin================================================")
        println("URI         : " + request.uri)
        println("Method      : " + request.method)
        println("Headers     : " + request.headers)
        println("Request body: " + String(body, Charset.forName("UTF-8")))
        println("==========================request end================================================")

    }

    @Throws(IOException::class)
    private fun logResponse(response: ClientHttpResponse) {
        println("============================response begin==========================================")
        println("Status code  : " + response.statusCode)
        println("Status text  : " + response.statusText)
        println("Headers      : " + response.headers)
        println("Response body: " + StreamUtils.copyToString(response.body, Charset.defaultCharset()))
        println("=======================response end=================================================")

    }
}