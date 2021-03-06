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

package com.juanmedina.keces.world

import com.juanmedina.keces.platform.test.TestUtils
import com.juanmedina.kecs.entity.Entity
import com.juanmedina.kecs.system.System
import com.juanmedina.kecs.world.World
import kotlin.math.truncate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WorldTests {
    data class Position(val x: Float, val y: Float)

    data class Velocity(val x: Float, val y: Float)

    class RecordTimeSystem : System() {
        val deltas = arrayListOf<Float>()
        var updates = 0
        var average = 0.0f
        var total = 0.0f

        override fun update(delta: Float, total: Float, world: World) {
            updates++
            deltas.add(delta)
            this.total = total
            average = total / updates
        }
    }

    @Test
    fun `we should record time correctly`() {
        val ecs = World()
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

    @Test
    fun `we should get string from world`() {
        val ecs = World()
        val rts = RecordTimeSystem()
        ecs.add(rts)

        val entity1 = Entity()
        entity1.add(Position(0.0f, 0.0f))
        entity1.add(Velocity(1.0f, 2.0f))
        ecs.add(entity1)

        val entity2 = Entity()
        entity2.add(Position(0.0f, 0.0f))
        entity2.add(Velocity(1.0f, 2.0f))
        ecs.add(entity2)

        val str = ecs.toString()

        assertTrue("""World\(.*.*\)""".toRegex().matches(str))
        assertTrue(".*entities=.*".toRegex().matches(str))
        assertTrue(".*systems=.*".toRegex().matches(str))
    }
}
