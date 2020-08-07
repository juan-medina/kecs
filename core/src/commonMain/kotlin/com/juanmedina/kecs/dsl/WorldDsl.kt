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

import com.juanmedina.kecs.World
import com.juanmedina.kecs.system.System

@Dsl
class WorldDsl {
    val ecs = World()
    fun ecs() = ecs
    inline operator fun <reified T : System> T.unaryPlus() {
        ecs.add(this)
    }
}

fun world(init: WorldDsl.() -> Unit) = WorldDsl().apply(init).ecs()