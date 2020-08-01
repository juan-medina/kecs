package kecs.platform

expect class Platform {
    companion object {
        fun getSystemSeconds(): Float
    }
}
