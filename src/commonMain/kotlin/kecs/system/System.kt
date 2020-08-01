package kecs.system

import kecs.entity.Entity

expect abstract class System {

    abstract fun update(delta: Float, total: Float, entities: List<Entity>)

    inline fun <reified T : Any> register()

    fun filter(entity: Entity) : Boolean

}
