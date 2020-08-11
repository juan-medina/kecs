/*
 * Copyright (C) 2020 Juan Medina
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.juanmedina.keces.entity

import com.juanmedina.kecs.dsl.add
import com.juanmedina.kecs.dsl.entity
import com.juanmedina.kecs.entity.View
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ViewTest {
    data class Position(val x: Float, val y: Float)

    data class Velocity(val x: Float, val y: Float)

    data class Player(val name: String)

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
    fun `we can view on using a receiver`() {
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

        view.view(Position::class, Velocity::class) {
            val (pos, vel) = it.pair<Position, Velocity>()

            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we can view on using a pairs`() {
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

        val window = view.pairs<Position, Velocity>()

        assertEquals(2, window.size)

        window.forEach { (pos, vel) ->
            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we can view on using a pairs with a receiver`() {
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

        view.pairs<Position, Velocity> { (pos, vel) ->
            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun `we can view on using a triples`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
            +Player("player1")
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
            +Player("player2")
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Player("player2")
        }

        val window = view.triples<Position, Player, Velocity>()

        assertEquals(2, window.size)

        window.forEach { (pos, player, vel) ->
            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)

            assertTrue((player.name == "player1") or (player.name == "player2"))
        }
    }

    @Test
    fun `we can view on using a trples with a receiver`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
            +Player("player1")
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
            +Player("player2")
        }

        view.add {
            +Position(0.0f, 0.0f)
            +Player("player2")
        }

        view.triples<Position, Velocity, Player> { (pos, vel, player) ->
            assertEquals(0.0f, pos.x)
            assertEquals(0.0f, pos.y)

            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)

            assertTrue((player.name == "player1") or (player.name == "player2"))
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
    fun `we can get a single entity on type`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Velocity(2.0f, 3.0f)
        }

        val ent = view.entity<Position>()

        val pos = ent.get<Position>()
        val vel = ent.get<Velocity>()

        assertEquals(0.0f, pos.x)
        assertEquals(0.0f, pos.y)

        assertEquals(1.0f, vel.x)
        assertEquals(2.0f, vel.y)
    }

    @Test
    fun `we can get a set of entities on type`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Velocity(1.0f, 2.0f)
        }

        val entities = view.entities<Velocity>()

        assertEquals(2, entities.size)

        entities.forEach {
            val vel = it.get<Velocity>()
            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
    }

    @Test
    fun ` we can get a set of entities on type in a receiver`() {
        val view = View()

        view.add {
            +Position(0.0f, 0.0f)
            +Velocity(1.0f, 2.0f)
        }

        view.add {
            +Velocity(1.0f, 2.0f)
        }

        view.entities<Velocity> {
            val vel = it.get<Velocity>()
            assertEquals(1.0f, vel.x)
            assertEquals(2.0f, vel.y)
        }
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

    @Test
    fun `we can get a String from a view`() {
        val view = View()

        view.add {
            +Velocity(1.0f, 2.0f)
            +Position(0.0f, 0.0f)
        }
        view.add {
            +Velocity(1.0f, 2.0f)
        }

        val str = view.toString()

        assertTrue("""View\(entities=.*\)""".toRegex().matches(str))
    }
}
