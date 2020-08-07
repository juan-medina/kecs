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
import com.juanmedina.kecs.platform.Platform
import com.juanmedina.kecs.system.System

class World : View() {
    private val systems = arrayListOf<System>()
    private var current = 0L
    private var total = 0.0f

    fun update() {
        val new = Platform.getSystemMillis()
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

    fun add(system: System) {
        systems.add(system)
    }
}