[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](index.md) / [entity](./entity.md)

# entity

`fun entity(vararg types: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<out `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`Entity`](../-entity/index.md)

Return a single [Entity](../-entity/index.md) that has a component of the giving types, or throws exception if there is more than one.

### Parameters

`types` - Component classes such Position::class