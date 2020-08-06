### Introduction

Bellow we could see a advance example that tries to illustrate how to use an ECS to perform a concurrent task, in this
example an animal race.

This separate our *entities* *components* and *systems*, they concern and how they work with each other.

### Running the example

If you like to run this example, from the root path of this project you could run the gradle task for your platform:

```bash
> graddlew runSampleJvm
> graddlew runSampleLinuxDebug
> graddlew runSampleLinuxRelease
```

### Source Code

You could [browse this code on github](https://github.com/juan-medina/kecs/blob/main/sample/src/commonMain/kotlin/animalrace.kt){target=_blank}.

```kotlin
--8<-- "sample/src/commonMain/kotlin/animalrace.kt"

fun main() {
    animalRace()
}
```

