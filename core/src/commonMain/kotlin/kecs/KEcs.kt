package kecs

import kecs.dsl.KEcsDsl
import kecs.entity.View
import kecs.platform.Platform
import kecs.system.System

class KEcs : View() {
    @Suppress("ClassName")
    companion object dsl {
        fun ecs(init: KEcsDsl.() -> Unit) = KEcsDsl().apply(init).ecs()
    }

    private val systems = arrayListOf<System>()
    private var current = 0L
    private var total = 0.0f

    fun update() {
        val new = Platform.getSystemMillis()
        if (current == 0L) {
            current = new
        }
        val delta = (new - current) / 1000.0f
        total += delta
        current = new
        systems.forEach {
            it.update(delta, total, this)
        }
    }

    fun add(system: System) {
        systems.add(system)
    }
}
