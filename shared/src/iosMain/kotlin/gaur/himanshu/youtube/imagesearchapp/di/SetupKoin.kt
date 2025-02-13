package gaur.himanshu.youtube.imagesearchapp.di

import gaur.himanshu.youtube.imagesearchapp.viewModel.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(){
    startKoin {
        modules(
            viewModelModule,
            sharedModule
        )
    }
}