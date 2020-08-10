[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](index.md) / [entities](./entities.md)

# entities

`protected val entities: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`Entity`](../-entity/index.md)`>`

[Entities](../-entity/index.md) storage.

`fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> entities(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Entity`](../-entity/index.md)`>`

Return a set [entities](../-entity/index.md) that has a component of the giving type.

`inline fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> entities(receiver: (`[`Entity`](../-entity/index.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Send to a receiver a set of [entities](../-entity/index.md) that has a component of the
giving type.

### Parameters

`receiver` - function that will receive the entities.