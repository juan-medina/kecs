package keces.dsl

import kecs.KEcs
import kecs.system.System
import kotlin.test.Test
import kotlin.test.assertEquals

class DslTests {
    data class Velocity(val x: Float, val y: Float)

    data class Position(var x: Float, var y: Float) {
        operator fun plusAssign(velocity: Velocity) {
            x += velocity.x
            y += velocity.y
        }
    }

    class MoveSystem : System() {
        override fun update(delta: Float, total: Float, ecs: KEcs) {
            ecs.filter {
                it.hasComponent<Velocity>() and it.hasComponent<Position>()
            }.forEach {
                val vel = it.get<Velocity>()
                val pos = it.get<Position>().copy()

                pos += vel

                it.set(pos)
            }
        }
    }

    @Test
    fun `we could use the DSL with a created systems`() {
        val world = KEcs.ecs {
            +MoveSystem()
        }

        val ent1 = world.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        val ent2 = world.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.5f, 2.5f)
        }

        val ent3 = world.add {
            +Position(0.0f, 0.0f)
        }

        world.update()
        world.update()

        assertEquals(2.0f, ent1.get<Position>().x)
        assertEquals(4.0f, ent1.get<Position>().y)

        assertEquals(3.0f, ent2.get<Position>().x)
        assertEquals(5.0f, ent2.get<Position>().y)

        assertEquals(0.0f, ent3.get<Position>().x)
        assertEquals(0.0f, ent3.get<Position>().y)
    }

    @Test
    fun `we could use the DSL with an anonymous system`() {
        val world = KEcs.ecs {
            +object : System() {
                override fun update(delta: Float, total: Float, ecs: KEcs) {
                    val vel = Velocity(1.0f, 1.0f)
                    ecs.filter {
                        it.hasComponent<Position>()
                    }.forEach {
                        it.get<Position>() += vel
                    }
                }
            }
        }

        val ent1 = world.add {
            +Position(0.0f, 0.0f)
        }

        val ent2 = world.add {
            +Position(1.0f, 1.0f)
        }

        val ent3 = world.add {
            +Position(2.0f, 2.0f)
        }

        world.update()
        world.update()

        assertEquals(2.0f, ent1.get<Position>().x)
        assertEquals(2.0f, ent1.get<Position>().y)

        assertEquals(3.0f, ent2.get<Position>().x)
        assertEquals(3.0f, ent2.get<Position>().y)

        assertEquals(4.0f, ent3.get<Position>().x)
        assertEquals(4.0f, ent3.get<Position>().y)
    }
}
