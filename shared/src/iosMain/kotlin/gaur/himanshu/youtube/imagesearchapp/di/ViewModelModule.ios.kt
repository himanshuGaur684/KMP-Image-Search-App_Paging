package gaur.himanshu.youtube.imagesearchapp.di

import gaur.himanshu.youtube.imagesearchapp.viewModel.ImageViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    single { ImageViewModel() }
}

object  SharedViewModelModule : KoinComponent{

    fun getImageViewModel():ImageViewModel = get()

}