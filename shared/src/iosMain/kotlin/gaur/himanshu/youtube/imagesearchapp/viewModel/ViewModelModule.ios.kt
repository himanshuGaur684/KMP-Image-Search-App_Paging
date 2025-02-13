package gaur.himanshu.youtube.imagesearchapp.viewModel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    single { ImageViewModel() }
}

object SharedViewModelModule : KoinComponent {
    val imageViewModel: ImageViewModel by inject<ImageViewModel>()
}