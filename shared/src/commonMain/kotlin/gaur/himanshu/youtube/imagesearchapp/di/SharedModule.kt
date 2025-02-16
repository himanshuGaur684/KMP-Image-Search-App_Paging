package gaur.himanshu.youtube.imagesearchapp.di

import gaur.himanshu.youtube.imagesearchapp.client.KtorClient
import gaur.himanshu.youtube.imagesearchapp.repository.ImageRepository
import org.koin.dsl.module

val sharedModule = module {
    factory { KtorClient() }
    factory { ImageRepository(get()) }
}