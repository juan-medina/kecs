[KECS](../../index.md) / [com.juanmedina.kecs.entity](../index.md) / [View](./index.md)

# View

`open class View : `[`MutableCollection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-collection/index.html)`<`[`Entity`](../-entity/index.md)`>`

A View represent a set of entities in [World](../../com.juanmedina.kecs.world/-world/index.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Creates a empty [View](./index.md).`View()` |

### Properties

| Name | Summary |
|---|---|
| [size](size.md) | Number of entities in our View.`open val size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | Adds a [Entity](../-entity/index.md) to our view.`open fun add(element: `[`Entity`](../-entity/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [addAll](add-all.md) | Add a set of [entities](../-entity/index.md) to our view.`open fun addAll(elements: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`Entity`](../-entity/index.md)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [clear](clear.md) | Clear all the [entities](../-entity/index.md) in our view.`open fun clear(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [component](component.md) | Return a single component from a single [Entity](../-entity/index.md) that has a component of the giving type, or throws exception if there is more than one.`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> component(): T` |
| [components](components.md) | Return the components in our view for a giving class.`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> components(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<T>` |
| [contains](contains.md) | Checks if a [Entity](../-entity/index.md) exists in our view.`open fun contains(element: `[`Entity`](../-entity/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [containsAll](contains-all.md) | Checks if a set of [entities](../-entity/index.md) exists in our view.`open fun containsAll(elements: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`Entity`](../-entity/index.md)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [entity](entity.md) | Return a single [Entity](../-entity/index.md) that has a component of the giving types, or throws exception if there is more than one.`fun entity(vararg types: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<out `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`Entity`](../-entity/index.md) |
| [hasComponent](has-component.md) | Check if we have a any [Entity](../-entity/index.md) with a given component type.`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> hasComponent(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isEmpty](is-empty.md) | Check if the view has not [entities](../-entity/index.md).`open fun isEmpty(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [iterator](iterator.md) | Obtain an [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html) of the [entities](../-entity/index.md) in our view.`open fun iterator(): `[`MutableIterator`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterator/index.html)`<`[`Entity`](../-entity/index.md)`>` |
| [remove](remove.md) | Removes a specific [Entity](../-entity/index.md) in our view.`open fun remove(element: `[`Entity`](../-entity/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [removeAll](remove-all.md) | Remove a set of [entities](../-entity/index.md) in our view.`open fun removeAll(elements: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`Entity`](../-entity/index.md)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [retainAll](retain-all.md) | Remove all [entities](../-entity/index.md) in a view no matching the ones provided.`open fun retainAll(elements: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`Entity`](../-entity/index.md)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [view](view.md) | Create a sub-view giving a set of component classes.`fun view(vararg types: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<out `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`View`](./index.md) |

### Extension Functions

| Name | Summary |
|---|---|
| [add](../../com.juanmedina.kecs.dsl/add.md) | DSL for adding [entities](../-entity/index.md) using [EntityDsl](../../com.juanmedina.kecs.dsl/-entity-dsl/index.md)`fun `[`View`](./index.md)`.add(init: `[`EntityDsl`](../../com.juanmedina.kecs.dsl/-entity-dsl/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Entity`](../-entity/index.md) |

### Inheritors

| Name | Summary |
|---|---|
| [World](../../com.juanmedina.kecs.world/-world/index.md) | Contains all the [entities](../-entity/index.md) and [systems](../../com.juanmedina.kecs.system/-system/index.md) on our *ECS*.`class World : `[`View`](./index.md) |
