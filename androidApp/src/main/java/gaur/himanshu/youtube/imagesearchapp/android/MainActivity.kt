package gaur.himanshu.youtube.imagesearchapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import gaur.himanshu.youtube.imagesearchapp.android.pagingStates.AppendError
import gaur.himanshu.youtube.imagesearchapp.android.pagingStates.AppendLoader
import gaur.himanshu.youtube.imagesearchapp.android.pagingStates.PrependError
import gaur.himanshu.youtube.imagesearchapp.android.pagingStates.PrependLoader
import gaur.himanshu.youtube.imagesearchapp.viewModel.ImageViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var query by rememberSaveable { mutableStateOf("") }
                    val viewModel = koinViewModel<ImageViewModel>()
                    val images = viewModel.images.collectAsLazyPagingItems()


                    Scaffold(topBar = {
                        TextField(
                            value = query, onValueChange = {
                                query = it
                                viewModel.updateQuery(query)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            placeholder = { Text("Search...") }
                        )
                    }) {
                        if(images.loadState.refresh is LoadState.Loading && query.isNotEmpty()){
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator()
                            }
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                        ) {

                            if (images.loadState.prepend is LoadState.Loading) {
                                item {
                                    PrependLoader(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }

                            if (images.loadState.prepend is LoadState.Error) {
                                item {
                                    PrependError(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        images.retry()
                                    }
                                }
                            }


                            items(
                                count = images.itemCount,
                                key = images.itemKey { it.id },
                                contentType = images.itemContentType { "ContentType" }) { index ->
                                AsyncImage(
                                    model = images.get(index)?.largeImageURL,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            if (images.loadState.append is LoadState.Loading) {
                                item {
                                    AppendLoader(modifier = Modifier.padding(12.dp))
                                }
                            }
                            if (images.loadState.append is LoadState.Error) {
                                item {
                                    AppendError(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        images.retry()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, q: String, onValueChange: (String) -> Unit) {

}

