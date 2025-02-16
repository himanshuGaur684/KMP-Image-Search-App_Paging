package gaur.himanshu.youtube.imagesearchapp.client

import gaur.himanshu.youtube.imagesearchapp.model.PixabayResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

    fun getClient():HttpClient{
        return HttpClient {
            install(ContentNegotiation){
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout){
                socketTimeoutMillis = 3000
                requestTimeoutMillis = 3000
                connectTimeoutMillis = 3000
            }

           // https://pixabay.com/api/?key=40308333-07c19e899666cb68334ed3a46&q=yellow+flowers&page=1&per_page=10
            install(DefaultRequest){
                url {
                    host = "pixabay.com"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                }
            }

        }
    }


    // https://pixabay.com/api/?key=40308333-07c19e899666cb68334ed3a46&q=yellow+flowers&page=1&per_page=10
    suspend fun getImages(q:String,perPage:Int,page:Int): PixabayResponse{
       val m= getClient()
            .get {
                url{
                    path("/api/")
                    parameter("key","40308333-07c19e899666cb68334ed3a46")
                     parameter("page",page)
                    parameter("per_page",perPage)
                    parameter("q",q)
                }
            }
            println(m.bodyAsText())
            return m.body<PixabayResponse>()
    }

}