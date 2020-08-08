[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](index.md) / [triples](./triples.md)

# triples

`fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified K : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified V : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> triples(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Triple`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-triple/index.html)`<T, K, V>>`

Return a set of [Triple](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-triple/index.html) from the components of the given type.

This could be use for destructing declarations.

`inline fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified K : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified V : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> triples(receiver: (`[`Triple`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-triple/index.html)`<T, K, V>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Send a set of [Triple](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-triple/index.html) from the components of the given type to a receiver.

This could be use for destructing declarations.

### Parameters

`receiver` - function that will receive the triples.