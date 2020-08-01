package kecs

import kecs.dsl.KEcsDsl
import kecs.entity.Entity
import kecs.platform.Platform
import kecs.system.System

class KEcs {
    @Suppress("ClassName")
    companion object dsl{
        fun ecs(init: KEcsDsl.() -> Unit) = KEcsDsl().apply(init).ecs()
    }
    private val systems = arrayListOf<System>()
    private val entities = arrayListOf<Entity>()
    private var current = 0.0f
    private var total = 0.0f

    fun update() {
        val new = Platform.getSystemSeconds()
        if (current == 0.0f) {
            current = new
        }
        val delta = new - current
        total += delta
        current = new
        systems.forEach {
            it.update(delta, total, entities.filter(it::filter))
        }
    }

    fun add(system: System) {
        systems.add(system)
    }

    fun add(entity: Entity) {
        entities.add(entity)
    }
}
