package kecs.entity

expect class Entity {

    inline infix fun <reified T : Any> add(component: T)

    inline fun <reified T : Any> set(component: T)

    inline fun <reified T : Any> hasComponent(): Boolean

    inline fun <reified T : Any> get(): T

    inline operator fun <reified T : Any> T.unaryPlus()
}
