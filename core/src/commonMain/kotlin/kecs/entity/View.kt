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

package kecs.entity

import kotlin.reflect.KClass

open class View() : MutableCollection<Entity> {
    constructor(entities: Iterable<Entity>) : this() {
        addAll(entities)
    }

    private val entities = arrayListOf<Entity>()

    fun view(vararg types: KClass<out Any>) = View(filter { it.hasComponents(*types) })

    inline fun <reified T : Any> components() = filter { it.hasComponent<T>() }.map { it.get<T>() }

    inline fun <reified T : Any> component() = filter { it.hasComponent<T>() }.map { it.get<T>() }.single()

    inline fun <reified T : Any> hasComponent() = any { it.hasComponent<T>() }

    fun entity(vararg types: KClass<out Any>) = single { it.hasComponents(*types) }

    override val size: Int
        get() = entities.size

    override fun add(element: Entity) = entities.add(element)

    override fun contains(element: Entity) = entities.contains(element)

    override fun containsAll(elements: Collection<Entity>) = entities.containsAll(entities)

    override fun isEmpty() = entities.isEmpty()

    override fun iterator(): MutableIterator<Entity> = entities.iterator()

    override fun addAll(elements: Collection<Entity>) = entities.addAll(elements)

    override fun clear() = entities.clear()

    override fun remove(element: Entity) = entities.remove(element)

    override fun removeAll(elements: Collection<Entity>) = entities.removeAll(elements)

    override fun retainAll(elements: Collection<Entity>) = entities.retainAll(elements)
}
