package kecs.platform

import kotlin.system.getTimeMillis

actual class Platform {
    actual companion object {
        actual fun getSystemSeconds() = getTimeMillis() / 1000.0f
    }
}
