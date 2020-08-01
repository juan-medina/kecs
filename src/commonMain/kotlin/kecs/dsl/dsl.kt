package kecs.dsl

import kecs.KEcs
import kecs.entity.Entity

expect fun entity(init: Entity.() -> Unit) : Entity

expect fun ecs(init: KEcs.() -> Unit) : KEcs

