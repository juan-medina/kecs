package keces

import keces.platform.test.TestUtils
import kecs.KEcs
import kecs.system.System
import kotlin.math.truncate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class KEcsTests {
    class RecordTimeSystem : System() {
        val deltas = arrayListOf<Float>()
        var updates = 0
        var average = 0.0f
        var total = 0.0f

        override fun update(delta: Float, total: Float, ecs: KEcs) {
            updates++
            deltas.add(delta)
            this.total = total
            average = total / updates
        }
    }

    @Test
    fun `we should record time correctly`() {
        val ecs = KEcs()
        val rts = RecordTimeSystem()
        ecs.add(rts)

        val steps = 4

        for (x in 1..steps) {
            TestUtils.sleep(x)
            ecs.update()
        }

        assertEquals(steps, rts.updates, "we have not been call $steps times")
        assertNotEquals(rts.total, 0.0f, "we should have a total time")
        assertEquals(rts.deltas.sum(), rts.total, "sum of deltas should be equal to total")
        val approximately = (2..steps).sum().toFloat()
        assertEquals(truncate(rts.total), approximately, "we should record approximately time")
    }
}
