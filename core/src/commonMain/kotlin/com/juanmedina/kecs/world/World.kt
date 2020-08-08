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

package com.juanmedina.kecs.world

import com.juanmedina.kecs.entity.View
import com.juanmedina.kecs.platform.getSystemMillis
import com.juanmedina.kecs.system.System

/**
 * Contains all the [entities][com.juanmedina.kecs.entity.Entity] and [systems][com.juanmedina.kecs.system.System] on
 *  our *ECS*.
 *
 * When we ask to the world to [update][com.juanmedina.kecs.world.World.update] all the
 *  [systems][com.juanmedina.kecs.system.System] get notified with the state of our world.
 *
 * Since it extend from [View][com.juanmedina.kecs.entity.View] allow to flexible query our
 *  [entities][com.juanmedina.kecs.entity.Entity].
 *
 * @constructor Creates an empty World.
 */
class World : View() {
    private val systems = arrayListOf<System>()
    private var current = 0L
    private var total = 0.0f

    /**
     * Perform a [world][com.juanmedina.kecs.world] update, triggering the
     *  [update][com.juanmedina.kecs.system.System.update] method in each of the
     *  [systems][com.juanmedina.kecs.system.System] added to the world.
     */
    fun update() {
        val new = getSystemMillis()
        if (current == 0L) {
            current = new
        }
        val delta = (new - current) / 1000.0f
        total += delta
        current = new
        systems.forEach {
            it.update(delta, total, this)
        }
    }

    /**
     * Adds a [systems][com.juanmedina.kecs.system.System] to our world.
     *
     * @param system a [System][com.juanmedina.kecs.system.System].
     */
    fun add(system: System) {
        systems.add(system)
    }
}
