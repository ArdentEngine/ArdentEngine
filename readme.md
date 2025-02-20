# Ardent Engine

Ardent Engine is a free and open source general purpose game engine written in Java currently in its early stage of development.

## Main features (planned)

* Intuitive visual editor application.
* Support for multiple platforms.
* Efficient 2D and 3D rendering.
* Efficient 2D and 3D physics.

## Core pillars

### Modularity

The engine is designed to be modular.
This will allow components to be added, removed, or replaced without altering the engine's core module.

For example, it will be possible to change the rendering module from OpenGL to Vulkan and vice versa by changing a dependency.

### Ease of use

Ease of use is the primary focus of the engine's design.
Some systems were made to be similar to other popular engines users may be more familiar with.

Some classes deliberately avoid the usage of complex design patterns or hide their implementation to allow users with basic programming knowledge to work with the engine.

### Portability

The final goal will be to allow users to create the game once and build for several different platforms.
The current target is Desktop (any OS), Android, and Web.

### Efficiency

Most parts of the engine are designed with execution speed in mind.

Performance-critical parts of the engine will make use of native libraries for maximum efficiency where possible.
The engine will be responsible for loading the correct library for the current operating system to maintain the advantage of portability.

## Planned features

### Version 0.1

| Feature                            | Status      |
|------------------------------------|-------------|
| Node-based scene system            | In progress |
| 3D rendering of meshes             | In progress |
| 2D rendering of textures           | In progress |
| Resource manager                   | Done        |
| Keyboard input system              | Done        |
| Basic 3D lighting                  | Missing     |
| Basic materials and shaders system | Missing     |

### Version 0.2

* Nuklear bindings (for the editor application)
* Editor application
* Events system (similar to C# delegates)
* Rendering of 3D models
* Finished 3D lighting system
* Basic 2D lighting

### Version 0.3

* More complex 2D rendering (Tile maps, lines, polygons...)
* Audio module (OpenAL bindings)
* Support for multiple windows
* Gamepad input system
* Physics system using JNI (either Jolt or Bullet)

### Version 0.4

* Vulkan bindings

### Version 0.5

* Android support
* Support for other JVM languages (Scala, Kotlin...)

### Version 1.0 (Minimum viable product)

* Intellij plugin to embed the editor in the IDE?
* Web support?
* Other?
