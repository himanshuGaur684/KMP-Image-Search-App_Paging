package gaur.himanshu.youtube.imagesearchapp.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import gaur.himanshu.youtube.imagesearchapp.viewModel.ImageViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val viewModel = koinViewModel<ImageViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val images = viewModel.images.collectAsLazyPagingItems()
                    var query by rememberSaveable { mutableStateOf("") }


                    Scaffold(
                        topBar = {
                            TextField(
                                value = query, onValueChange = {
                                    query = it
                                    viewModel.updateQuery(query)
                                }, colors = TextFieldDefaults.colors(
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ), modifier = Modifier.fillMaxWidth()
                            )
                        }
                    ) {

                        if(images.loadState.refresh is LoadState.Loading && query.isNotEmpty()){
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator()
                            }
                        }


                        if(images.loadState.refresh is LoadState.Error){
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Button(onClick = {
                                    images.refresh()
                                }) {
                                    Text("Refresh")
                                }
                            }
                        }

                        if(images.loadState.refresh !is LoadState.Loading && images.loadState.refresh !is LoadState.Error){
                            LazyColumn(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxSize()
                            ) {

                                if(images.loadState.prepend is LoadState.Error){
                                    item {
                                        Box(Modifier
                                            .fillMaxWidth()
                                            .height(55.dp), contentAlignment = Alignment.Center){
                                            Button(onClick = {
                                                images.retry()
                                            }) {
                                                Text("retry")
                                            }
                                        }
                                    }
                                }

                                if(images.loadState.prepend is LoadState.Loading){
                                    item{
                                        Box(Modifier
                                            .fillMaxWidth()
                                            .height(55.dp), contentAlignment = Alignment.Center){
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                items(
                                    count = images.itemCount,
                                    key = images.itemKey { it.id }
                                ) { index ->
                                    val image = images.get(index)

                                    image?.let {
                                        AsyncImage(
                                            model = image.largeImageURL,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(vertical = 4.dp)
                                                .fillMaxWidth()
                                                .height(300.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }

                                if(images.loadState.append is LoadState.Loading){
                                    item{
                                        Box(Modifier
                                            .fillMaxWidth()
                                            .height(55.dp), contentAlignment = Alignment.Center){
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                if(images.loadState.append is LoadState.Error){
                                    item {
                                        Box(Modifier
                                            .fillMaxWidth()
                                            .height(55.dp), contentAlignment = Alignment.Center){
                                            Button(onClick = {
                                                images.retry()
                                            }) {
                                                Text("retry")
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
    }
}

