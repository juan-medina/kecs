package kecs.entity

import kotlin.reflect.KClass

class Entity {
    val components = hashMapOf<KClass<*>, Any>()

    inline infix fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    inline fun <reified T : Any> set(component: T) = add(component)

    inline fun <reified T : Any> hasComponent(): Boolean {
        return components.containsKey(T::class)
    }

    inline fun <reified T : Any> get(): T {
        return components.getValue(T::class) as T
    }

    inline operator fun <reified T : Any> T.unaryPlus() {
        add(this)
    }
}
