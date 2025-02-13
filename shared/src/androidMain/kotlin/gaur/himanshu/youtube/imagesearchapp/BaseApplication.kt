package gaur.himanshu.youtube.imagesearchapp

import android.app.Application
import gaur.himanshu.youtube.imagesearchapp.di.sharedModule
import gaur.himanshu.youtube.imagesearchapp.viewModel.viewModelModule
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule)
            modules(viewModelModule)
        }
    }
}