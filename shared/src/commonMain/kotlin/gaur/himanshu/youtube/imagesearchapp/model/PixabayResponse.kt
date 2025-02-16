package gaur.himanshu.youtube.imagesearchapp.model

import kotlinx.serialization.Serializable

@Serializable
data class PixabayResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)