package net.canway.cloud.cloud_client2.fallback

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

@Component
class MyFallbackProvider : FallbackProvider {
    override//这个方法表明为哪个微服务提供回退，* 表示为所有微服务提供回退
    fun getRoute(): String {
        return "*"
    }

    override fun fallbackResponse(route: String, cause: Throwable): ClientHttpResponse {

        return object : ClientHttpResponse {
            @Throws(IOException::class)
            override fun getStatusCode(): HttpStatus {
                return HttpStatus.BAD_REQUEST
            }

            @Throws(IOException::class)
            override fun getRawStatusCode(): Int {
                return HttpStatus.BAD_REQUEST.value()
            }

            @Throws(IOException::class)
            override fun getStatusText(): String {
                return HttpStatus.BAD_REQUEST.reasonPhrase
            }

            override fun close() {}

            @Throws(IOException::class)
            override fun getBody(): InputStream {
                return ByteArrayInputStream("fallback$route".toByteArray())
            }

            override fun getHeaders(): HttpHeaders {
                val headers = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_JSON
                return headers
            }
        }
    }
}