import kecs.KEcs
import kecs.KEcs.dsl.ecs
import kecs.system.System
import kotlin.math.min
import kotlin.random.Random

const val NUM_DOGS = 4
const val MIN_DOG_SPEED = 40.0f
const val MAX_DOG_SPEED = 66.0f
const val END_YARDS = 100.0f
const val END_FEET = END_YARDS * 3.0f

data class Dog(val name: String)
enum class MovementStatus {
    Running,
    Stopped
}

data class Movement(
    val speed: Float = Random.nextDouble(MIN_DOG_SPEED.toDouble(), MAX_DOG_SPEED.toDouble()).toFloat(),
    var status: MovementStatus = MovementStatus.Running
)

data class Position(var feet: Float = 0.0f, var time: Float = 0.0f)

data class Winner(val name: String)

enum class RaceStatus {
    Running,
    End
}

class MovementSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        ecs.filter {
            it.hasComponent<Position>() and it.hasComponent<Movement>()
        }.forEach {
            val movement = it.get<Movement>()
            if (movement.status == MovementStatus.Running) {
                val position = it.get<Position>()
                val step = (movement.speed * delta)
                val feet = position.feet + step
                if (feet <= END_FEET) {
                    position.feet = min(position.feet + step, END_FEET)
                    position.time += delta
                } else {
                    position.feet = END_FEET
                    movement.status = MovementStatus.Stopped
                }
            }
        }
    }
}

class WinnerSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        if (ecs.none { it.hasComponent<Winner>() }) {
            ecs.filter {
                it.hasComponent<Position>() and it.hasComponent<Movement>() and it.hasComponent<Dog>()
            }.forEach {
                val position = it.get<Position>()
                val dog = it.get<Dog>()
                if (position.feet == END_FEET) {
                    ecs.add {
                        +Winner(dog.name)
                    }
                    return@update
                }
            }
        }
    }
}

class RaceSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        val status = ecs.single { it.hasComponent<RaceStatus>() }
        if (status.get<RaceStatus>() != RaceStatus.End) {
            val allStopped = ecs.filter {
                it.hasComponent<Dog>() and it.hasComponent<Movement>()
            }.all {
                val movement = it.get<Movement>()
                movement.status == MovementStatus.Stopped
            }
            if (allStopped) {
                status.set(RaceStatus.End)
            }
        }
    }
}

fun sample() {
    val world = ecs {
        +MovementSystem()
        +WinnerSystem()
        +RaceSystem()
    }

    world.add {
        +RaceStatus.Running
    }

    for (x in 1..NUM_DOGS) {
        world.add {
            +Dog(name = "dog $x")
            +Position()
            +Movement()
        }
    }

    println("$NUM_DOGS dogs running....")

    while (world.single { it.hasComponent<RaceStatus>() }.get<RaceStatus>() != RaceStatus.End) {
        world.update()
    }

    val winner = world.single { it.hasComponent<Winner>() }.get<Winner>()
    println("The Winner is ${winner.name}!")

    world.filter {
        it.hasComponent<Dog>() and it.hasComponent<Position>()
    }.sortedBy {
        it.get<Position>().time
    }.forEachIndexed { place, it ->
        val dog = it.get<Dog>()
        val pos = it.get<Position>()
        val twoDecimalsTime = (pos.time * 100).toInt() / 100.0f
        println("${place + 1}) ${dog.name} in ${twoDecimalsTime}s")
    }
}
