package kecs.dsl

import kecs.KEcs
import kecs.system.System

@Dsl
class KEcsDsl {
    val ecs = KEcs()
    fun ecs() = ecs
    inline operator fun <reified T : System> T.unaryPlus() {
        ecs.add(this)
    }
}
