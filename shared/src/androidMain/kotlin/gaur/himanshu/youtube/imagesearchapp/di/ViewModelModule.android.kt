package gaur.himanshu.youtube.imagesearchapp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import gaur.himanshu.youtube.imagesearchapp.viewModel.ImageViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    viewModelOf(::ImageViewModel)
}