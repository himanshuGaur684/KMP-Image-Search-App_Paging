package gaur.himanshu.youtube.imagesearchapp.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.model.Hit
import kotlinx.coroutines.delay

class ImagePagingSource(
    private val q: String,
    private val ktorClient: KtorClient
) : PagingSource<Int, Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        val page = params.key ?: 1
        val perPage = params.loadSize

        return try {
            val response = ktorClient.getImages(
                q = q,
                page = page,
                perPage = perPage
            )
            delay(6000)
            LoadResult.Page(
                data = response.hits,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.hits.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}