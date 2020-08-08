[KECS](../../index.md) / [com.juanmedina.kecs.system](../index.md) / [System](index.md) / [update](./update.md)

# update

`abstract fun update(delta: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, total: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, world: `[`World`](../../com.juanmedina.kecs.world/-world/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

get notified by the world that this system needs update.

### Parameters

`delta` - time since last update in seconds.

`total` - total time since the world was created.

`world` - the ECS world that needs updates.