[KECS](../../index.md) / [com.juanmedina.kecs.world](../index.md) / [World](./index.md)

# World

`class World : `[`View`](../../com.juanmedina.kecs.entity/-view/index.md)

Contains all the [entities](../../com.juanmedina.kecs.entity/-entity/index.md) and [systems](../../com.juanmedina.kecs.system/-system/index.md) on
our *ECS*.

When we ask to the world to [update](update.md) all the
[systems](../../com.juanmedina.kecs.system/-system/index.md) get notified with the state of our world.

Since it extend from [View](../../com.juanmedina.kecs.entity/-view/index.md) allow to flexible query our
[entities](../../com.juanmedina.kecs.entity/-entity/index.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Contains all the [entities](../../com.juanmedina.kecs.entity/-entity/index.md) and [systems](../../com.juanmedina.kecs.system/-system/index.md) on our *ECS*.`World()` |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | Adds a [systems](../../com.juanmedina.kecs.system/-system/index.md) to our world.`fun add(system: `[`System`](../../com.juanmedina.kecs.system/-system/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [toString](to-string.md) | Generates an String that represent the [world](../index.md).`fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [update](update.md) | Perform a [world](../index.md) update, triggering the [update](../../com.juanmedina.kecs.system/-system/update.md) method in each of the [systems](../../com.juanmedina.kecs.system/-system/index.md) added to the world.`fun update(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Extension Functions

| Name | Summary |
|---|---|
| [add](../../com.juanmedina.kecs.dsl/add.md) | DSL for adding [entities](../../com.juanmedina.kecs.entity/-entity/index.md) using [EntityDsl](../../com.juanmedina.kecs.dsl/-entity-dsl/index.md).`fun `[`View`](../../com.juanmedina.kecs.entity/-view/index.md)`.add(init: `[`EntityDsl`](../../com.juanmedina.kecs.dsl/-entity-dsl/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Entity`](../../com.juanmedina.kecs.entity/-entity/index.md) |
