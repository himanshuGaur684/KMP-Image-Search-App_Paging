package gaur.himanshu.youtube.imagesearchapp.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.model.Hit
import kotlinx.coroutines.delay

class ImagePagingSource(
    private val ktorClient: KtorClient,
    private val q: String
) : PagingSource<Int, Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
      return  try {

            val perPage = params.loadSize
            val page = params.key ?: 1
            val resposne = ktorClient.getImages(
                q = q,
                perPage = perPage,
                page = page
            )
          delay(3000)
          println("Page -> $page And PerPage -> $perPage and response -> ${resposne.hits}")
            LoadResult.Page(
                data = resposne.hits,
                nextKey = if(resposne.hits.isEmpty()) null else page.plus(1),
                prevKey = if(page==1) null else page.minus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}