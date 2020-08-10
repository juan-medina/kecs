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
 * A View represent a set of entities in [World][com.juanmedina.kecs.world.World].
 *
 * @constructor Creates a empty [View][com.juanmedina.kecs.entity.View].
 */
open class View() : MutableCollection<Entity> {
    /**
     * Creates a View based in an [Iterable] of [entities][com.juanmedina.kecs.entity.Entity].
     */
    private constructor(entities: Iterable<Entity>) : this() {
        addAll(entities)
    }

    /**
     * [Entities][com.juanmedina.kecs.entity.Entity] storage.
     */
    protected val entities = arrayListOf<Entity>()

    /**
     * Create a sub-view giving a set of component classes.
     *
     * @param types Set of Components class such Position::class.
     * @return a new create view with the components of the provided classes.
     */
    fun view(vararg types: KClass<out Any>) = View(filter { it.hasComponents(*types) })

    /**
     * Send to a receiver a set of [Entities][com.juanmedina.kecs.entity.Entity] for the given types
     *
     * @param types set of [KClass] that we need on the entities.
     * @param receiver function that will receive the entities.
     */
    fun view(vararg types: KClass<out Any>, receiver: (Entity) -> Unit) = view(*types).forEach(receiver)

    /**
     * Return a set of [Pair] from the components of the given type.
     *
     * This could be use for destructing declarations.
     */
    inline fun <reified T : Any, reified K : Any> pairs() = view(T::class, K::class).map { it.pair<T, K>() }

    /**
     * Send a set of [Pair] from the components of the given type to a receiver.
     *
     * This could be use for destructing declarations.
     *
     * @param receiver function that will receive the pairs.
     */
    inline fun <reified T : Any, reified K : Any> pairs(receiver: (Pair<T, K>) -> Unit) =
        pairs<T, K>().forEach(receiver)

    /**
     * Return a set of [Triple] from the components of the given type.
     *
     * This could be use for destructing declarations.
     */
    inline fun <reified T : Any, reified K : Any, reified V : Any> triples() = view(T::class, K::class).map {
        it.triple<T, K, V>()
    }

    /**
     * Send a set of [Triple] from the components of the given type to a receiver.
     *
     * This could be use for destructing declarations.
     *
     * @param receiver function that will receive the triples.
     */
    inline fun <reified T : Any, reified K : Any, reified V : Any> triples(receiver: (Triple<T, K, V>) -> Unit) =
        triples<T, K, V>().forEach(receiver)

    /**
     * Return the components in our view for a giving class.
     */
    inline fun <reified T : Any> components() = filter { it.hasComponent<T>() }.map { it.get<T>() }

    /**
     * Return a single component from a single [Entity][com.juanmedina.kecs.entity.Entity] that has a component of
     *  the giving type, or throws exception if there is more than one.
     */
    inline fun <reified T : Any> component() = filter { it.hasComponent<T>() }.map { it.get<T>() }.single()

    /**
     * Check if we have a any [Entity][com.juanmedina.kecs.entity.Entity] with a given component type.
     */
    inline fun <reified T : Any> hasComponent() = any { it.hasComponent<T>() }

    /**
     * Return a single [Entity][com.juanmedina.kecs.entity.Entity] that has a component of the giving types, or
     *  throws exception if there is more than one.
     *
     * @param types Component classes such Position::class
     */
    fun entity(vararg types: KClass<out Any>) = single { it.hasComponents(*types) }

    /**
     * Return a single [Entity][com.juanmedina.kecs.entity.Entity] that has a component of the giving type, or
     *  throws exception if there is more than one.
     */
    inline fun <reified T : Any> entity() = single { it.hasComponents(T::class) }

    /**
     * Return a set [entities][com.juanmedina.kecs.entity.Entity] that has a component of the giving type.
     */
    inline fun <reified T : Any> entities() = filter { it.hasComponents(T::class) }

    /**
     * Send to a receiver a set of [entities][com.juanmedina.kecs.entity.Entity] that has a component of the
     *  giving type.
     *
     *  @param receiver function that will receive the entities.
     */
    inline fun <reified T : Any> entities(receiver: (Entity) -> Unit) = filter { it.hasComponents(T::class) }
        .forEach(receiver)

    /**
     * Number of entities in our View.
     */
    override val size: Int
        get() = entities.size

    /**
     * Adds a [Entity][com.juanmedina.kecs.entity.Entity] to our view.
     *
     * @param element The Entity][com.juanmedina.kecs.entity.Entity] to add to our view.
     */
    override fun add(element: Entity) = entities.add(element)

    /**
     * Checks if a [Entity][com.juanmedina.kecs.entity.Entity] exists in our view.
     *
     * @param element The [Entity][com.juanmedina.kecs.entity.Entity] to check in our view.
     */
    override fun contains(element: Entity) = entities.contains(element)

    /**
     * Checks if a set of [entities][com.juanmedina.kecs.entity.Entity] exists in our view.
     *
     * @param elements The set of [entities][com.juanmedina.kecs.entity.Entity] to check in our view.
     */
    override fun containsAll(elements: Collection<Entity>) = entities.containsAll(entities)

    /**
     * Check if the view has not [entities][com.juanmedina.kecs.entity.Entity].
     */
    override fun isEmpty() = entities.isEmpty()

    /**
     * Obtain an [Iterator] of the [entities][com.juanmedina.kecs.entity.Entity] in our view.
     */
    override fun iterator(): MutableIterator<Entity> = entities.iterator()

    /**
     * Add a set of [entities][com.juanmedina.kecs.entity.Entity] to our view.
     *
     * @param elements [entities][com.juanmedina.kecs.entity.Entity] to add to our view.
     */
    override fun addAll(elements: Collection<Entity>) = entities.addAll(elements)

    /**
     * Clear all the [entities][com.juanmedina.kecs.entity.Entity] in our view.
     */
    override fun clear() = entities.clear()

    /**
     * Removes a specific [Entity][com.juanmedina.kecs.entity.Entity] in our view.
     *
     * @param element [Entity][com.juanmedina.kecs.entity.Entity] to be removed from the view.
     */
    override fun remove(element: Entity) = entities.remove(element)

    /**
     * Remove a set of [entities][com.juanmedina.kecs.entity.Entity] in our view.
     *
     * @param elements a set of [entities][com.juanmedina.kecs.entity.Entity] to be removed from the view.
     */
    override fun removeAll(elements: Collection<Entity>) = entities.removeAll(elements)

    /**
     * Remove all [entities][com.juanmedina.kecs.entity.Entity] in a view no matching the ones provided.
     *
     * @param elements [entities][com.juanmedina.kecs.entity.Entity] to retain in our view.
     */
    override fun retainAll(elements: Collection<Entity>) = entities.retainAll(elements)

    /**
     * Generates an String that represent the [world][com.juanmedina.kecs.entity.View].
     * @return [String] with the view details.
     */
    override fun toString(): String {
        return "View(entities=$entities)"
    }
}
