package gaur.himanshu.youtube.imagesearchapp.client

import gaur.himanshu.youtube.imagesearchapp.model.PixabayResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    fun getClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 3000
                requestTimeoutMillis = 3000
                socketTimeoutMillis = 3000
            }
            install(DefaultRequest) {
                url {
                    host = "pixabay.com"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }


    // https://pixabay.com/api/?key=YOUR-API-KEY&q=yellow+flowers&page=1&per_page=10

    suspend fun getImages(q: String, page: Int, perPage: Int): PixabayResponse {
        return getClient().get {
            url {
                path("/api/")
                parameter("key", "your api key")
                parameter("q", q)
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }.body<PixabayResponse>()
    }

}