package kecs.platform

expect class Platform {
    companion object {
        fun getSystemMilliSeconds(): Long
    }
}
