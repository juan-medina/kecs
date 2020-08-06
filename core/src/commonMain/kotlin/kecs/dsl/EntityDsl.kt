package kecs.dsl

import kecs.entity.Entity

@Dsl
class EntityDsl {
    val entity = Entity()
    fun entity() = entity
    inline operator fun <reified T : Any> T.unaryPlus() {
        entity.add(this)
    }
}

fun entity(init: EntityDsl.() -> Unit) = EntityDsl().apply(init).entity()
