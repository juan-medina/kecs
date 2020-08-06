#Example

### An animal race

Bellow we could see a complex example that tries to illustrate how to use an ECS to perform a concurrent task, in this
example an animal race.

This separate our *entities* *components* and *systems*.

If you like to test this example, from the root path of this project you could run the gradle task for your platform:

```bash
> graddlew runSampleJvm
> graddlew runSampleLinuxDebug
> graddlew runSampleLinuxRelease
```

You could as well [check out the code](https://github.com/juan-medina/kecs/blob/main/sample/src/commonMain/kotlin/animalrace.kt){target=_blank}.

```kotlin
--8<-- "sample/src/commonMain/kotlin/animalrace.kt"

fun main() {
    animalRace()
}
```

