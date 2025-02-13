package gaur.himanshu.youtube.imagesearchapp.viewModel

import android.widget.ImageView
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    viewModelOf(::ImageViewModel)
}