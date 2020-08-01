package kecs.dsl

import kecs.KEcs
import kecs.entity.Entity

fun entity(init: Entity.() -> Unit) = Entity().apply(init)

fun ecs(init: KEcs.() -> Unit) = KEcs().apply(init)
