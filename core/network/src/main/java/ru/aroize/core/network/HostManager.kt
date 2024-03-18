package ru.aroize.core.network

interface HostManager {
    fun provide(): String
    fun set(host: String)
}

class HostManagerImpl: HostManager {

    private var host = BASE_HOST

    override fun provide(): String = host

    override fun set(host: String) {
        this.host = host
    }

    companion object {
        private const val BASE_HOST = "https://api.pexels.com/"
    }
}