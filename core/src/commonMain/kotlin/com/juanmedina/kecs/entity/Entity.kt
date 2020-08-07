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

package com.juanmedina.kecs.entity

import kotlin.reflect.KClass

class Entity {
    val components = hashMapOf<KClass<*>, Any>()

    inline infix fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    inline fun <reified T : Any> set(component: T) = add(component)

    inline fun <reified T : Any> get() = components.getValue(T::class) as T

    inline fun <reified T : Any> hasComponent() = components.containsKey(T::class)

    @Suppress("UNUSED_PARAMETER")
    inline fun <reified T : Any> hasComponent(type: KClass<out T>) = hasComponent<T>()

    fun hasComponents(vararg types: KClass<out Any>) = components.keys.containsAll(types.toList())

    fun isEmpty() = components.size == 0

    inline fun <reified T : Any> removeComponent() {
        components.remove(T::class)
    }

    fun <T : Any> removeComponent(obj: T) {
        val candidates = components.filter {
            it.value == obj
        }
        if (candidates.size == 1) {
            val found = candidates.getValue(candidates.keys.first())
            components.remove(found::class)
        }
    }
}
