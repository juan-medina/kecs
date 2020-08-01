package kecs.platform

import kotlin.system.getTimeMillis

actual class Platform {
    actual companion object {
        actual fun getSystemMillis() = getTimeMillis()
    }
}
