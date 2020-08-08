## Basics

To learn how to use KECS let's start with the basics.

### Creating our world

The first thing that we need for using KECS is to create our world, a object
that will hold all of our entities, with their components, and systems.

For this we could just do :

```kotlin
val world = World()
```

### Adding entities
Now we need to add entities to our world.

```kotlin
val myEntity = Entity()
```

### Adding components
An entity is just an instance in our world, but we need to add components to it that
represent what that entity contains.

For example if we have this components.

 ```kotlin
 data class Position(var x : Float, var y: Float)

 data class Player(val name : String)
 ```

Now we could add to our entity the components that represent it.

```kotlin
myEntity.add(Postion(x = 0.0f, y = 0.0f))
myEntity.add(Player(name = "Jon"))
```

### Getting a component

When we have a component we could get a component it has using their type.

```kotlin
val player = myEntity.get<Player>()
println("the player is ${player.name}")
```

### Getting Destructuring

We could have Destructuring declarations to obtain components using pair or triple.

```kotlin
val (vel, pos) = myEntity.pair<Velocity, Position>()
val (vel, pos, player) = myEntity.triple<Velocity, Position, Player>()
```

### Adding the entity to our world

Now that our entity has what we need we could add it to our world.

```kotlin
world.add(myEntity)
```

### Creating a system

Now that we have an entity in our world we need to add system that add behavior to them,
in this example we will just move the position base on the delta time happen between updates.

```kotlin
class MoveSystem : System() {
    override fun update(delta: Float, total: Float, world: World) {
        world.entities<Position> {
            val pos = it.get<Position>()

            pos.x += 5.0f * delta
            pos.y += 5.0f * delta
        }
    }
}
```

### Update our world

Now that we have our entities, components and systems we could ask for our world to update.

```kotlin
while (...) {
    world.update()
}
```

This will invoke our System and update our entities according the logic it has.

## Using KECS DSL

Since we are using Kotlin we could provide a DSL that simplify some steps that
we did before.

### Creating our world

With the dsl we could create a world and add system to it.

```kotlin
val world = world {
    +MoveSystem()
}
```

### Creating entities

We could as well create entities with their components.

```kotlin
val myEntity = entity {
    +Postion(x = 0.0f, y = 0.0f)
    +Player(name = "Jon")
}

world.add(myEntity)
```

### Quick entity creation

However, we could create entities and add them to our world with in one step.

```kotlin
val myEntity = world.add {
    +Postion(x = 0.0f, y = 0.0f)
    +Player(name = "Jon")
}
```

## Creating Systems

We look at before at creating a System, but we didn't get in the details of how they work.

### Independently time update

If we look at the code of the System that we create before we could see the update function.

```kotlin
class MoveSystem : System() {
    override fun update(delta: Float, total: Float, world: World) {
        world.entities<Position> {
            val pos = it.get<Position>()

            pos.x += 5.0f * delta
            pos.y += 5.0f * delta
        }
    }
}
```
When KECS is invoking the system update it will send to them the delta time from last update, and
how long this has been running, this could be used to implement logic that is independent
of the time that has passed, such moving an object independently of the frame rate.

In this example we will move 5 units on x and y per second independently of how often / late
our system get called.

### Creating Anonymous Systems

So far we have created a System using a class, however we could creat Anonymous Systems.

```kotlin
val world = world {
    +object : System() {
        override fun update(delta: Float, total: Float, world: World) {
            world.entities<Position> {
                val pos = it.get<Position>()

                pos.x += 5.0f * delta
                pos.y += 5.0f * delta
            }
        }
    }
}
```

## Using Views

Our KECS world is actually a View, a class that hold entities and provide methods to use them.

### Creating Views

We could get a subview of a view just using the view function, giving the components that
we are interested on.
```kotlin
val view = world.view(Position::class, Velocity::class)
```

### Creating views from pairs

We can creat views from pairs

```kotlin
val view = world.pairs<Position, Velocity>()
view.forEach { (pos, vel) ->
    // do something with pos and vel
}
//or
val view = world.pairs<Position, Velocity> { (pos, vel) ->
    // do something with pos and vel
}
```

### Creating views from triples

We can creat views from pairs

```kotlin
val view = world.triples<Position, Velocity, Player>()
view.forEach { (pos, vel, player) ->
    // do something with pos, vel and player
}
//or
val view = world.pairs<Position, Velocity> { (pos, vel, player) ->
    // do something with pos, vel and player
}
```

This view will contain all the entities that has a Position and  Velocity component.

### Iterating views

Views implement the Iterable<Entity> interface, so we could use normal Iterators functions.

```kotlin
view.forEach {
...
}

view.sort {
...
}

view.groupBy {
...
}
```

### Obtaining a single Entity

Some times we may want to obtain a single Entity from a view.

```kotlin
val entity1 = view.entity(Player::class)
//or
val entity1 = view.entity<Player>()
```

### Obtaining a set of Entities

Some times we may want to obtain a set Entities from a view.

```kotlin
val entity1 = view.entitie(Player::class)
//or
val entity1 = view.entitie<Player>()
```

### Obtaining Pairs of Entities

Some times we may want to obtain a set of pairs from Entities int a view.

```kotlin
view.pairs<Player, Position> { (player, pos) ->
    // do something with player and pos
}
//or
val entities = view.pairs<Player, Position>()
entities.forEach { (player, pos) ->
    // do something with player and pos
}
```

### Obtaining Triples of Entities

Some times we may want to obtain a set of triples from Entities int a view.

```kotlin
view.triple<Player, Position, Velocity> { (player, pos, vel) ->
    // do something with player, pos and velocity
}
//or
val entities = view.pairs<Player, Position>()
entities.forEach { (player, pos, vel) ->
    // do something with player, pos and velocity
}
```

This will return the entity that has a Player component, it will fail if we have more than one.

### Obtaining a set of components

Other times we may want to get just the components from a set of entities, we could easily do that with.

```kotlin
val components = view.components<Position>()
```

This will return all the components on the view of that class. However, if you update
the components they will be not update since you are not modifying the entity.

### Obtaining a single component

In a very edge case scenario you may just want to get a single component, and you could
that with.

```kotlin
val component = view.component<Player>()
```

Note that this will fail if you have more than one entity with that component.
