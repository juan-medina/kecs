package kecs.entity

import kecs.dsl.EntityDsl
import kotlin.reflect.KClass

class Entity {
    @Suppress("ClassName")
    companion object dsl{
        fun entity(init: EntityDsl.() -> Unit) = EntityDsl().apply(init).entity()
    }
    val components = hashMapOf<KClass<*>, Any>()

    inline infix fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    inline fun <reified T : Any> set(component: T) = add(component)

    inline fun <reified T : Any> get(): T {
        return components.getValue(T::class) as T
    }
}
