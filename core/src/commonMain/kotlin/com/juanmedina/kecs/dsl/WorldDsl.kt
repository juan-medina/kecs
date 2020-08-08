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

package com.juanmedina.kecs.dsl

import com.juanmedina.kecs.system.System
import com.juanmedina.kecs.world.World

/**
 * DSL for creating a [World][com.juanmedina.kecs.world.World].
 **/
@KECSDsl
class WorldDsl internal constructor() {
    /**
     * the [World][com.juanmedina.kecs.world.World] that this DSL will return.
     */
    val world = World()

    /**
     * Ends the DSL and return the created [World][com.juanmedina.kecs.world.World].
     *
     * @return the created [World][com.juanmedina.kecs.world.World].
     */
    internal fun ecs() = world

    /**
     * Unary plus operator to use inside the DSL receiver.
     */
    inline operator fun <reified T : System> T.unaryPlus() {
        world.add(this)
    }
}

/**
 * DSL for creating a [World][com.juanmedina.kecs.world.World] using [WorldDsl][com.juanmedina.kecs.dsl.WorldDsl].
 *
 * @param init A lambda receiver that will get a [WorldDsl][com.juanmedina.kecs.dsl.WorldDsl].
 * @return a new created [World][com.juanmedina.kecs.world.World].
 */
fun world(init: WorldDsl.() -> Unit) = WorldDsl().apply(init).ecs()
