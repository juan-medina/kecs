[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](index.md) / [pairs](./pairs.md)

# pairs

`fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified K : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> pairs(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<T, K>>`

Return a set of [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html) from the components of the given type.

This could be use for destructing declarations.

`inline fun <reified T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, reified K : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> pairs(receiver: (`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<T, K>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Send a set of [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html) from the components of the given type to a receiver.

This could be use for destructing declarations.

### Parameters

`receiver` - function that will receive the pairs.