package kecs.dsl

import kecs.entity.View

fun View.add(init: EntityDsl.() -> Unit) = entity(init).let {
    add(it)
    it
}
