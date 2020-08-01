package kecs.system

import kecs.entity.Entity
import kotlin.reflect.KClass

actual abstract class System {

    actual abstract fun update(delta: Float, total: Float, entities: List<Entity>)

    val componentsClasses: ArrayList<KClass<out Any>> = arrayListOf()

    actual inline fun <reified T : Any> register() {
        componentsClasses.add(T::class)
    }

    actual fun filter(entity: Entity) = entity.components.keys.containsAll(componentsClasses)

}
