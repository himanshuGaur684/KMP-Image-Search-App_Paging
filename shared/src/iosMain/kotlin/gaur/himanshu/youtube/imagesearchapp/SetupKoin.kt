package gaur.himanshu.youtube.imagesearchapp

import gaur.himanshu.youtube.imagesearchapp.di.sharedModule
import gaur.himanshu.youtube.imagesearchapp.di.viewModelModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            sharedModule, viewModelModule
        )
    }
}