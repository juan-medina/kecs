package kecs

import kecs.entity.Entity
import kecs.system.System
import kotlin.system.getTimeMillis

actual class KEcs {

    private val systems = arrayListOf<System>()
    private val entities = arrayListOf<Entity>()
    private var current = 0L
    private var total = 0.0f

    actual fun update() {
        if (current == 0L) {
            current = getTimeMillis()
        }
        val new = getTimeMillis()
        val delta = (new - current) / 1000.0f
        total += delta
        current = new
        systems.forEach {
            it.update(delta, total, entities.filter(it::filter))
        }
    }

    actual fun add(system: System) {
        systems.add(system)
    }

    actual fun add(entity: Entity) {
        entities.add(entity)
    }
}
