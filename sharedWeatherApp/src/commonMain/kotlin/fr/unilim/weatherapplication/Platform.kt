package fr.unilim.weatherapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform