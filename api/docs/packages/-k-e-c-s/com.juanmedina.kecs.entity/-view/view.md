[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](index.md) / [view](./view.md)

# view

`fun view(vararg types: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<out `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`View`](index.md)

Create a sub-view giving a set of component classes.

### Parameters

`types` - Set of Components class such Position::class.

**Return**
a new create view with the components of the provided classes.

`fun view(vararg types: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<out `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>, receiver: (`[`Entity`](../-entity/index.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Send to a receiver a set of [Entities](../-entity/index.md) for the given types

### Parameters

`types` - set of [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) that we need on the entities.

`receiver` - function that will receive the entities.