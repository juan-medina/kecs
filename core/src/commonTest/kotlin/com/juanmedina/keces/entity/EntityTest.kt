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

import com.juanmedina.kecs.entity.Entity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EntityTest {
    data class Position(var x: Float, var y: Float)

    data class Velocity(val x: Float, val y: Float)

    enum class EntityState {
        InitialState,
        EndState
    }

    @Test
    fun `we can create a entity and get it components`() {
        val ent = Entity()
        ent.add(Position(0.0f, 0.0f))
        ent.add(Velocity(1.0f, 2.0f))

        val pos = ent.get<Position>()
        assertEquals(0.0f, pos.x)
        assertEquals(0.0f, pos.y)

        val vel = ent.get<Velocity>()
        assertEquals(1.0f, vel.x)
        assertEquals(2.0f, vel.y)
    }

    @Test
    fun `we can check if a entity has components`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))

        assertTrue(ent.hasComponent<Position>())
        assertFalse(ent.hasComponent<Velocity>())
    }

    @Test
    fun `we can check if a entity has components by class`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))

        assertTrue(ent.hasComponent(Position::class))
        assertFalse(ent.hasComponent(Velocity::class))
    }

    @Test
    fun `we can check if a entity has components by classes`() {
        val ent1 = Entity()
        ent1.add(Position(1.0f, 2.0f))
        ent1.add(Velocity(3.0f, 4.0f))

        assertTrue(ent1.hasComponents(Position::class, Velocity::class))

        val ent2 = Entity()
        ent2.add(Position(1.0f, 2.0f))

        assertTrue(ent2.hasComponents(Position::class))
        assertFalse(ent2.hasComponents(Position::class, Velocity::class))
    }

    @Test
    fun `we can change an entity component value`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))
        ent.add(Velocity(3.0f, 4.0f))

        val pos = ent.get<Position>()
        val vel = ent.get<Velocity>()

        pos.x += vel.x
        pos.y += vel.y

        ent.set(pos)

        val newPos = ent.get<Position>()
        assertEquals(4.0f, newPos.x)
        assertEquals(6.0f, newPos.y)
    }

    @Test
    fun `a new entity is empty`() {
        val ent = Entity()

        assertTrue(ent.isEmpty())
    }

    @Test
    fun `a entity with components is not empty`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))

        assertFalse(ent.isEmpty())
    }

    @Test
    fun `we can remove components by class`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))
        ent.add(Velocity(3.0f, 4.0f))

        assertTrue(ent.hasComponent<Position>())
        assertTrue(ent.hasComponent<Velocity>())

        ent.removeComponent<Velocity>()

        assertTrue(ent.hasComponent<Position>())
        assertFalse(ent.hasComponent<Velocity>())
    }

    @Test
    fun `we can remove components by value`() {
        val ent = Entity()
        ent.add(Position(1.0f, 2.0f))
        ent.add(Velocity(3.0f, 4.0f))

        assertTrue(ent.hasComponent<Position>())
        assertTrue(ent.hasComponent<Velocity>())

        val vel = ent.get<Velocity>()

        ent.removeComponent(vel)

        assertTrue(ent.hasComponent<Position>())
        assertFalse(ent.hasComponent<Velocity>())
    }

    @Test
    fun `we can work with enums`() {
        val ent = Entity()

        ent.add(Position(1.0f, 2.0f))
        ent.add(Velocity(3.0f, 4.0f))
        ent.add(EntityState.InitialState)

        var state = ent.get<EntityState>()

        assertEquals(EntityState.InitialState, state)

        ent.set(EntityState.EndState)

        state = ent.get()

        assertEquals(EntityState.EndState, state)
    }
}
