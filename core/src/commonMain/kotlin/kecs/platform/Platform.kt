package kecs.platform

expect class Platform {
    companion object {
        fun getSystemMillis(): Long
    }
}
