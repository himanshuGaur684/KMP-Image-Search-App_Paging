package gaur.himanshu.youtube.imagesearchapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform