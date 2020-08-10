# Kotlin Entity Component System
Kotlin Cross-platform Entity Component System

![KECS](https://juan-medina.github.io/kecs/icon-wide.svg)

[![License: Apache2](https://img.shields.io/badge/license-Apache%202-blue.svg)](/LICENSE)
[![Docs](https://img.shields.io/badge/docs-latest-brightgreen.svg)](https://juan-medina.github.io/kecs/)
[![Download](https://api.bintray.com/packages/juan-medina/kecs/kecs/images/download.svg)](https://bintray.com/juan-medina/kecs/kecs/_latestVersion)
[![Download](https://img.shields.io/badge/try-playground-brightgreen)](https://juan-medina.github.io/kecs/playground/)


## Platforms
![Platform: JVM](https://img.shields.io/badge/platform%3A%20JVM-Ok-green)
![Platform: Linux x64](https://img.shields.io/badge/platform%3A%20Linux%20x64-Ok-green)
![Platform: Windows x64](https://img.shields.io/badge/platform%3A%20Windows%20x64-Ok-green)
![Platform: JS](https://img.shields.io/badge/platform%3A%20JS-Stand%20By-red)
![Platform: MAC](https://img.shields.io/badge/platform%3A%20Mac-Stand%20By-red)

## Info

KECS is a Cross-platform Entity Component System design to create concurrent applications, such games,
more simple and without the need of using multiple threads neither fibers nor corutines.

It allows separating data from behavior and get rid of deep object oriented inheritance.

Due to data-oriented design allow modern processors to highly optimize it for an over perform of more traditional
systems.

If you like to learn more about what is an ECS we try to give some clarification on [this section](https://juan-medina.github.io/kecs/ecs/).

## Installation

Currently, KECS is available in jcenter, first we need to add the repositories,
if we do not have them.

### Add jcenter to gradle
```groovy
repositories {
    jcenter()
}
```

### Add jcenter to maven

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <name>bintray</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>
```

This will install the multi-platform module for all the platforms in your system.

### Multi-platform Gradle project

If you are  creating a multi-platform project you need to add to the platforms that you need

```groovy
kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                implementation kotlin('stdlib-common')
                implementation 'com.juanmedina:kecs-jvm:1.0.1'
            }
        }
        linuxMain {
            dependencies {
                implementation 'com.juanmedina:kecs-linux:1.0.1'
            }
        }
        mingwMain {
            dependencies {
                implementation 'com.juanmedina:kecs-mingw:1.0.1'
            }
        }
    }
}
```

### Single-platform Gradle project

For just adding as dependency for a simple platform you could do this in gradle:

```groovy
dependencies {
    implementation 'com.juanmedina:kecs-jvm:1.0.1'
}
```

### Single-platform Maven project
If you use maven you need to include the dependencies of the platforms that you target:
```xml
<dependency>
    <groupId>com.juanmedina</groupId>
    <artifactId>kecs-jvm</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Basic Usage

This is a basic example, check the [user guide](https://juan-medina.github.io/kecs/guide/), the [advance example](https://juan-medina.github.io/kecs/example/), or the [API Documentation](https://juan-medina.github.io/kecs/packages/-k-e-c-s/)
for learning more about using KECS or try the [Playground](https://juan-medina.github.io/kecs/playground/) for practical experimentation.

```Kotlin
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

fun example() {
    val world = world {
        +MoveSystem()
    }

    val ent1 = world.add {
        +Position(0.0f, 0.0f)
        +Velocity(1.0f, 2.0f)
    }

    val ent2 = world.add {
        +Position(0.0f, 0.0f)
        +Velocity(1.5f, 2.5f)
    }

    val ent3 = world.add {
        +Position(0.0f, 0.0f)
    }

    while(...) {
        world.update()
    }
}
```

## License

```text
    Copyright (C) 2020 Juan Medina

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
