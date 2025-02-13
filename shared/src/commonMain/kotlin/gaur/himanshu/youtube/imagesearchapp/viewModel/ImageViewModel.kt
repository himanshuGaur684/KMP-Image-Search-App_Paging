package gaur.himanshu.youtube.imagesearchapp.viewModel

import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.PagingDataEvent
import androidx.paging.PagingDataPresenter
import androidx.paging.cachedIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.launch
import gaur.himanshu.youtube.imagesearchapp.model.Hit
import gaur.himanshu.youtube.imagesearchapp.repository.ImagesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
class ImageViewModel(
) : ViewModel(), KoinComponent {

    private val imagesRepository: ImagesRepository by inject()

    private val _query = MutableStateFlow(viewModelScope, "")

    @OptIn(FlowPreview::class)
    val images = _query
        .filter { it.isNotEmpty() }
        .debounce(500)
        .flatMapLatest { q -> imagesRepository.getImagesPagingData(q).flow.cachedIn(viewModelScope.coroutineScope) }

    fun updateQuery(q: String) {
        _query.update { q }
    }

    @NativeCoroutinesState
    val iosUiState = MutableStateFlow(viewModelScope, IosUiState())

    private val hitsPresenter = object : PagingDataPresenter<Hit>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<Hit>) {
            updateSnapshotList(event)
        }
    }

    @NativeCoroutinesState
    val hitsSnapshotList =
        MutableStateFlow<ItemSnapshotList<Hit>>(viewModelScope, hitsPresenter.snapshot())

    init {
        viewModelScope.launch {
            images.collectLatest { result ->
                hitsPresenter.collectFrom(result)
            }
        }

        viewModelScope.launch {
            hitsPresenter.loadStateFlow.filterNotNull().collect { state ->
                iosUiState.value = IosUiState(
                    isRefresh = state.refresh is LoadState.Loading,
                    isPrepend = state.prepend is LoadState.Loading,
                    isAppend = state.append is LoadState.Loading,
                 )
            }
        }

    }


    private suspend fun updateSnapshotList(event: PagingDataEvent<Hit>) {
        hitsSnapshotList.value = hitsPresenter.snapshot()
    }

    fun getElement(index: Int): Hit? {
        return hitsPresenter[index]
    }

}

data class IosUiState(
    val dataSnapshot: ItemSnapshotList<Hit>? = null,
    val isPrepend: Boolean = false,
    val isAppend: Boolean = false,
    val isRefresh: Boolean = false
)