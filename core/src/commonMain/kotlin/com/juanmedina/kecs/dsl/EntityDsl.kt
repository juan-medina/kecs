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

import com.juanmedina.kecs.entity.Entity

/**
 * DSL for creating a [Entity][com.juanmedina.kecs.entity.Entity].
 **/
@KECSDsl
class EntityDsl internal constructor() {
    /**
     * the [Entity][com.juanmedina.kecs.entity.Entity] that this DSL will return.
     */
    val entity = Entity()

    /**
     * Ends the DSL and return the created [Entity][com.juanmedina.kecs.entity.Entity].
     *
     * @return the created [Entity][com.juanmedina.kecs.entity.Entity].
     */
    internal fun entity() = entity

    /**
     * Unary plus operator to use inside the DSL receiver.
     */
    inline operator fun <reified T : Any> T.unaryPlus() {
        entity.add(this)
    }
}

/**
 * DSL for creating [entities][com.juanmedina.kecs.entity.Entity] using [EntityDsl][com.juanmedina.kecs.dsl.EntityDsl].
 *
 * @param init A lambda receiver that will get a [EntityDsl][com.juanmedina.kecs.dsl.EntityDsl].
 * @return a new created [Entity][com.juanmedina.kecs.entity.Entity].
 */
fun entity(init: EntityDsl.() -> Unit) = EntityDsl().apply(init).entity()
