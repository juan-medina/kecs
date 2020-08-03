package kecs.entity

import kecs.dsl.EntityDsl
import kotlin.reflect.KClass

open class View() : MutableList<Entity> {
    constructor(entities: Iterable<Entity>) : this() {
        addAll(entities)
    }

    private val entities = arrayListOf<Entity>()

    fun add(init: EntityDsl.() -> Unit) = Entity.entity(init).let {
        add(it)
        it
    }

    fun view(vararg types: KClass<out Any>) = View(filter { it.hasComponents(*types) })

    inline fun <reified T : Any> components() = filter { it.hasComponent<T>() }.map { it.get<T>() }

    inline fun <reified T : Any> component() = filter { it.hasComponent<T>() }.map { it.get<T>() }.single()

    fun entity(vararg types: KClass<out Any>) = single { it.hasComponents(*types) }

    override val size: Int
        get() = entities.size

    override fun contains(element: Entity) = entities.contains(element)

    override fun containsAll(elements: Collection<Entity>) = entities.containsAll(entities)

    override fun get(index: Int): Entity = entities[index]

    override fun indexOf(element: Entity) = entities.indexOf(element)

    override fun isEmpty() = entities.isEmpty()

    override fun iterator(): MutableIterator<Entity> = entities.iterator()

    override fun lastIndexOf(element: Entity) = entities.lastIndexOf(element)

    override fun add(index: Int, element: Entity) = entities.add(index, element)

    override fun add(element: Entity) = entities.add(element)

    override fun addAll(index: Int, elements: Collection<Entity>) = entities.addAll(index, elements)

    override fun addAll(elements: Collection<Entity>) = entities.addAll(elements)

    override fun clear() = entities.clear()

    override fun listIterator() = entities.listIterator()

    override fun listIterator(index: Int) = entities.listIterator(index)

    override fun remove(element: Entity) = entities.remove(element)

    override fun removeAll(elements: Collection<Entity>) = entities.removeAll(elements)

    override fun removeAt(index: Int) = entities.removeAt(index)

    override fun retainAll(elements: Collection<Entity>) = entities.retainAll(elements)

    override fun set(index: Int, element: Entity) = entities.set(index, element)

    override fun subList(fromIndex: Int, toIndex: Int) = entities.subList(fromIndex, toIndex)
}
