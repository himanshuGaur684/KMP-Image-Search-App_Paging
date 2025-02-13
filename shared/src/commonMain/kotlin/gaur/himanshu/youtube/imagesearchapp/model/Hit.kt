package gaur.himanshu.youtube.imagesearchapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Hit(
    val id: Int,
    val largeImageURL: String,
)