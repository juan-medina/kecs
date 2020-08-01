package keces.platform.test

import  platform.posix.sleep

actual class TestUtils {
    actual companion object {
        actual fun sleep(seconds: Int) {
            sleep(seconds.toUInt())
        }
    }
}
