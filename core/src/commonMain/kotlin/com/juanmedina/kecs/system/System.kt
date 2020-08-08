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

package com.juanmedina.kecs.system

import com.juanmedina.kecs.world.World

/**
 * A system take care of update our world base on the entities of the our world.
 *
 * @constructor create a system, you must implement [com.juanmedina.kecs.system.System.update].
 */
abstract class System {
    /**
     * get notified by the world that this system needs update.
     *
     * @param delta time since last update in seconds.
     * @param total total time since the world was created.
     * @param world the ECS world that needs updates.
     */
    abstract fun update(delta: Float, total: Float, world: World)
}
