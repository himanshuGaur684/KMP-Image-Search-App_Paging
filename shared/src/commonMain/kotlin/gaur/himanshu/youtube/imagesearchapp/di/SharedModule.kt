package gaur.himanshu.youtube.imagesearchapp.di

import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.repository.ImagesRepository
import org.koin.dsl.module

val sharedModule = module {
    factory { KtorClient() }
    factory { ImagesRepository(get()) }
}