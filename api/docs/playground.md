<script src="https://unpkg.com/kotlin-playground@1" data-selector="code" data-server="https://kotlin-compiler-server.herokuapp.com/"></script>

In this Playground you could experiment with KECS, you could run and edit the example bellow.

```kotlin
import com.juanmedina.kecs.dsl.add
import com.juanmedina.kecs.dsl.world
import com.juanmedina.kecs.system.System
import com.juanmedina.kecs.world.World

data class GameObject(val name: String)

data class Velocity(val x: Float, val y: Float)

data class Position(var x: Float, var y: Float) {
    operator fun plusAssign(velocity: Velocity) {
        x += velocity.x
        y += velocity.y
    }
}

class MoveSystem : System() {
    override fun update(delta: Float, total: Float, world: World) {
        world.pairs<Velocity, Position> { (vel, pos) ->
            pos += vel
        }
    }
}

fun main() {
    val world = world {
        +MoveSystem()
    }

    world.add {
        +GameObject("player1")
        +Position(0.0f, 0.0f)
        +Velocity(1.0f, 2.0f)
    }

    world.add {
        +GameObject("player2")
        +Position(0.0f, 0.0f)
        +Velocity(1.5f, 2.5f)
    }

    world.add {
        +GameObject("item1")
        +Position(0.0f, 0.0f)
    }

    world.update()
    
    world.pairs<GameObject, Position> { (obj, pos) ->
    	println("$obj $pos")    
    }    
}

```
