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
