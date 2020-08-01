package kecs

import kecs.entity.Entity
import kecs.system.System

expect class KEcs {

    fun update()
    fun add(system: System)
    fun add(entity: Entity)

}
