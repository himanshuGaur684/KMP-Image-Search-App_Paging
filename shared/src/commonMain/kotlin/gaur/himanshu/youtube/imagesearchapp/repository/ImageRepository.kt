package gaur.himanshu.youtube.imagesearchapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.model.Hit
import gaur.himanshu.youtube.imagesearchapp.pagingSource.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val ktorClient: KtorClient) {

    fun getImages(q: String): Pager<Int,Hit> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 1,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                ImagePagingSource(ktorClient, q)
            }
        )
    }

}