package keces

import keces.platform.test.TestUtils
import kecs.KEcs
import kecs.KEcs.dsl.ecs
import kecs.entity.Entity
import kecs.entity.Entity.dsl.entity
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

        override fun update(delta: Float, total: Float, entities: List<Entity>) {
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

    data class Position(var x: Float, var y: Float)

    data class Velocity(val x: Float, val y: Float)

    class MoveSystem : System() {
        init {
            register<Position>()
            register<Velocity>()
        }

        override fun update(delta: Float, total: Float, entities: List<Entity>) {
            entities.forEach { entity ->
                val vel = entity.get<Velocity>()
                val pos = entity.get<Position>()

                pos.x += vel.x
                pos.y += vel.y

                entity.set(pos)
            }
        }
    }

    @Test
    fun `we could use the DSL`() {
        val world = ecs {
            +MoveSystem()
        }

        val obj1 = entity {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        world.add(obj1)

        val obj2 = entity {
            +Position(0.0f, 0.0f)
            +Velocity(1.5f, 2.5f)
        }

        world.add(obj2)

        val obj3 = entity {
            +Position(0.0f, 0.0f)
        }

        world.add(obj3)

        world.update()
        world.update()

        assertEquals(2.0f, obj1.get<Position>().x)
        assertEquals(4.0f, obj1.get<Position>().y)

        assertEquals(3.0f, obj2.get<Position>().x)
        assertEquals(5.0f, obj2.get<Position>().y)

        assertEquals(0.0f, obj3.get<Position>().x)
        assertEquals(0.0f, obj3.get<Position>().y)
    }
}
