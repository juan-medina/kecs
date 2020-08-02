package kecs.platform

import java.time.Instant

actual class Platform {
    actual companion object {
        actual fun getSystemMillis(): Long {
            return Instant.now().toEpochMilli()
        }
    }
}
