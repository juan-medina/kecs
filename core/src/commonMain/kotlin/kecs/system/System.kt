package kecs.system

import kecs.KEcs

abstract class System {
    abstract fun update(delta: Float, total: Float, ecs: KEcs)
}
