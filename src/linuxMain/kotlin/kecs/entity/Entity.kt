package kecs.entity

import kotlin.reflect.KClass

actual class Entity {
    val components = hashMapOf<KClass<*>, Any>()

    actual inline fun <reified T : Any> add(component: T) {
        components[T::class] = component
    }

    actual inline fun <reified T : Any> set(component: T) = add(component)

    actual inline fun <reified T : Any> hasComponent(): Boolean {
        return components.containsKey(T::class)
    }

    actual inline fun <reified T : Any> get(): T {
        return components.getValue(T::class) as T
    }

    override fun toString(): String {
        var out = "\n"
        components.forEach {
            out += "    $it\n"
        }
        return out
    }
}
