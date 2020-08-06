package keces.entity

import kecs.dsl.add
import kecs.dsl.entity
import kecs.entity.View
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ViewTest {
    data class Position(val x: Float, val y: Float)

    data class Velocity(val x: Float, val y: Float)

    @Test
    fun `we can create a view and add an existing entity`() {
        val view = View()

        val ent1 = entity {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add(ent1)

        val ent2 = view.single { it == ent1 }

        val pos = ent2.get<Position>()
        val vel = ent2.get<Velocity>()

        assertEquals(0.0f, pos.x)
        assertEquals(0.0f, pos.y)

        assertEquals(1.0f, vel.x)
        assertEquals(2.0f, vel.y)
    }

    @Test
    fun `we can create a view with and add entity with a lambda`() {
        val view = View()

        val ent1 = view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        val pos = ent1.get<Position>()
        val vel = ent1.get<Velocity>()

        assertEquals(0.0f, pos.x)
        assertEquals(0.0f, pos.y)

        assertEquals(1.0f, vel.x)
        assertEquals(2.0f, vel.y)
    }

    @Test
    fun `we can range`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        assertEquals(3, view.size)

        view.forEach {
            val pos = it.get<Position>()
            val vel = it.get<Velocity>()

            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we could get a single by component class`() {
        val view = View()
        val ent1 = view.add {
            +Velocity(1.0f, 2.0f)
        }

        val ent2 = view.add {
            +Position(1.0f, 2.0f)
        }

        val ent3 = view.single { it.hasComponent<Velocity>() }

        assertEquals(ent1, ent3)
        assertNotEquals(ent1, ent2)
    }

    @Test
    fun `we can filter on component classes`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
        }

        val window = view.filter {
            it.hasComponent<Position>() and it.hasComponent<Velocity>()
        }

        assertEquals(2, window.size)

        window.forEach {
            val pos = it.get<Position>()
            val vel = it.get<Velocity>()

            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we can view on component classes`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
        }

        val window = view.view(Position::class, Velocity::class)

        assertEquals(2, window.size)

        window.forEach {
            val pos = it.get<Position>()
            val vel = it.get<Velocity>()

            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we can get components on classes`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
        }

        val velocities = view.components<Velocity>()

        assertEquals(2, velocities.size)

        velocities.forEach {
            assertEquals(1.0f, it.x)
            assertEquals(2.0f, it.y)
        }
    }

    @Test
    fun `we can get a single component`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
        }

        view.add {
            +Position(0.0f, 0.0f)
        }

        view.add {
            +Velocity(1.0f, 2.0f)
        }

        val velocity = view.component<Velocity>()

        assertEquals(1.0f, velocity.x)
        assertEquals(2.0f, velocity.y)
    }

    @Test
    fun `we can get a single entity on classes`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Velocity(2.0f, 3.0f)
        }

        val ent = view.entity(Velocity::class, Position::class)

        val pos = ent.get<Position>()
        val vel = ent.get<Velocity>()

        assertEquals(0.0f, pos.x)
        assertEquals(0.0f, pos.y)

        assertEquals(1.0f, vel.x)
        assertEquals(2.0f, vel.y)
    }

    @Test
    fun `we can check if we have a any entity with a component`() {
        val view = View()

        view.add {
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Velocity(2.0f, 3.0f)
        }

        assertTrue(view.hasComponent<Velocity>())
        assertFalse(view.hasComponent<Position>())
    }

    @Test
    fun `we can check if we have a single entity with a component`() {
        val view = View()

        view.add {
            +Velocity(1.0f, 2.0f)
        }

        assertTrue(view.hasComponent<Velocity>())
        assertFalse(view.hasComponent<Position>())
    }
}
