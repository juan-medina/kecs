package keces.platform.test

actual class TestUtils {
    actual companion object {
        actual fun sleep(seconds: Int) {
            Thread.sleep(seconds.toLong() * 1000)
        }
    }
}
