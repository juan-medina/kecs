package kecs.entity

import kecs.dsl.EntityDsl
import kotlin.reflect.KClass

class Entity {
    @Suppress("ClassName")
    companion object dsl {
        fun entity(init: EntityDsl.() -> Unit) = EntityDsl().apply(init).entity()
    }

    val components = hashMapOf<KClass<*>, Any>()

    inline infix fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    inline fun <reified T : Any> set(component: T) = add(component)

    inline fun <reified T : Any> get() = components.getValue(T::class) as T

    inline fun <reified T : Any> hasComponent() = components.containsKey(T::class)

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
