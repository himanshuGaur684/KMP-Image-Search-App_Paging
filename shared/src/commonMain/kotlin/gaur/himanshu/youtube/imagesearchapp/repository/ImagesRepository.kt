package gaur.himanshu.youtube.imagesearchapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.pagingSource.ImagePagingSource

class ImagesRepository(private val ktorClient: KtorClient) {

    fun getImagesPagingData(q:String) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 1, enablePlaceholders = true, initialLoadSize = 10),
        pagingSourceFactory = {
            ImagePagingSource(q,ktorClient)
        }
    )

}