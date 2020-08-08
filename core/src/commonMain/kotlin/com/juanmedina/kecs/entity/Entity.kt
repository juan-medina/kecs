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

/**
 * An Entity represent a instance of an object in our world.
 *
 * An entity has a set of components, that are just simple data objects tha has the values
 * for our entity.
 *
 * @constructor creates an empty Entity.
 */
class Entity {

    /**
     * The components that this entity has
     *
     * We could have only one component per given type.
     */
    val components = hashMapOf<KClass<*>, Any>()

    /**
     * Add a component to this entity.
     *
     * @param component the component to add to the entity, we can have only one per the given type.
     */
    inline infix fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    /**
     * Sets the value component in this entity.
     *
     * @param component the component to set in the entity, we can have only one per the given type.
     */
    inline fun <reified T : Any> set(component: T) = add(component)

    /**
     * Gets a component from the entity of the given type.
     */
    inline fun <reified T : Any> get() = components.getValue(T::class) as T

    /**
     * Checks if an entity contains a component of the given type.
     */
    inline fun <reified T : Any> hasComponent() = components.containsKey(T::class)

    @Suppress("UNUSED_PARAMETER")
    /**
     * Checks if an entity contains a components of the given [KClass].
     *
     * @param type the [KClass] of the component.
     */
    inline fun <reified T : Any> hasComponent(type: KClass<out T>) = hasComponent<T>()

    /**
     * Checks if an entity contains a set of components of the given [KClass].
     *
     * @param types the [KClass] of the components.
     */
    fun hasComponents(vararg types: KClass<out Any>) = components.keys.containsAll(types.toList())

    /**
     * Checks if a entity is empty, so it has not component.
     */
    fun isEmpty() = components.size == 0

    /**
     * Removes a component of the given type from this entity.
     */
    inline fun <reified T : Any> removeComponent() {
        components.remove(T::class)
    }

    /**
     * Removes a component of from this entity giving the component reference.
     *
     * @param component a reference to the component to be removed.
     */
    fun <T : Any> removeComponent(component: T) {
        val candidates = components.filter {
            it.value == component
        }
        if (candidates.size == 1) {
            val found = candidates.getValue(candidates.keys.first())
            components.remove(found::class)
        }
    }
}
