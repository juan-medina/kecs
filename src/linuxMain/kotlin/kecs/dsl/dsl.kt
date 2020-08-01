package kecs.dsl

import kecs.KEcs
import kecs.entity.Entity

actual fun entity(init: Entity.() -> Unit) = Entity().apply(init)

actual fun ecs(init: KEcs.() -> Unit) = KEcs().apply(init)
