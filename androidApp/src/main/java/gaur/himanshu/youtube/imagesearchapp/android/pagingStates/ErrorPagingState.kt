package gaur.himanshu.youtube.imagesearchapp.android.pagingStates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppendError(modifier: Modifier = Modifier,onClick:()->Unit) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Button(onClick = onClick) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun PrependError(modifier: Modifier = Modifier,onClick:()->Unit) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Button(onClick = onClick) {
            Text(text = "Retry")
        }
    }
}