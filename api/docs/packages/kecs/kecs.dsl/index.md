[kecs](../index.md) / [kecs.dsl](./index.md)

## Package kecs.dsl

kotlin dsl for idiomatic creation of objects

### Types

| Name | Summary |
|---|---|
| [EntityDsl](-entity-dsl/index.md) | `class EntityDsl` |
| [KEcsDsl](-k-ecs-dsl/index.md) | `class KEcsDsl` |

### Annotations

| Name | Summary |
|---|---|
| [Dsl](-dsl/index.md) | `annotation class Dsl` |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `fun `[`View`](../kecs.entity/-view/index.md)`.add(init: `[`EntityDsl`](-entity-dsl/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Entity`](../kecs.entity/-entity/index.md) |
| [entity](entity.md) | `fun entity(init: `[`EntityDsl`](-entity-dsl/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Entity`](../kecs.entity/-entity/index.md) |
| [kecs](kecs.md) | `fun kecs(init: `[`KEcsDsl`](-k-ecs-dsl/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`KEcs`](../kecs/-k-ecs/index.md) |
