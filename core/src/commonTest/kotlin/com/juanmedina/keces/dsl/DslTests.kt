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

package com.juanmedina.keces.dsl

import com.juanmedina.kecs.KEcs
import com.juanmedina.kecs.dsl.add
import com.juanmedina.kecs.dsl.kecs
import com.juanmedina.kecs.system.System
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
            ecs.view(Velocity::class, Position::class).forEach {
                val vel = it.get<Velocity>()
                val pos = it.get<Position>().copy()

                pos += vel

                it.set(pos)
            }
        }
    }

    @Test
    fun `we could use the DSL with a created systems`() {
        val world = kecs {
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
        val world = kecs {
            +object : System() {
                override fun update(delta: Float, total: Float, ecs: KEcs) {
                    val vel = Velocity(1.0f, 1.0f)
                    ecs.components<Position>().forEach {
                        it += vel
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
