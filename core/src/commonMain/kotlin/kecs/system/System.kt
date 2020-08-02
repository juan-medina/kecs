package kecs.system

import kecs.entity.Entity
import kotlin.reflect.KClass

abstract class System {
    abstract fun update(delta: Float, total: Float, entities: List<Entity>)

    val componentsClasses: ArrayList<KClass<out Any>> = arrayListOf()

    inline fun <reified T : Any> register() {
        componentsClasses.add(T::class)
    }

    fun filter(entity: Entity) = entity.components.keys.containsAll(componentsClasses)
}
