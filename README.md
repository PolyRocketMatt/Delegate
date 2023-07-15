![State](https://img.shields.io/badge/State-ALPHA-red?style=for-the-badge)
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/PolyRocketMatt/Delegate/build_deploy.yml?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-%2368AD63?style=for-the-badge)
![Kotlin](https://img.shields.io/badge/Java-16-%233e7fa8?logo=java&style=for-the-badge)

<p align="center">
    <img width="128" height="128" src="img/delegate.png" />
</p>

<h1 align="center">Delegate</h1>

Delegate is a general-purpose, platform-agnostic command framework designed for different Minecraft servers.
The core idea behind Delegate is the use of fluent interfaces to automatically parse commands,
resolve command contexts and execute actions.

## Why choose Delegate?

In the past, many command frameworks have tried to solve the problem of allowing developers to
create commands in a simple and intuitive way. However, most of these frameworks either:

- become convoluted very quickly
- support not enough platforms for the developer's needs

Delegate solves this problem by providing a framework that builds commands for a large set of
platforms in a single way. Furthermore, Delegate is designed to be as simple as possible, while
still allowing for complex commands to be created. Finally, Delegate is fully unit/integration 
tested to ensure that it works on all platforms.

## Main Features

- Simple, powerful command creation/execution
- Autonomous command handling
- Automatic tab completion
- Context resolver without the need to casting or parsing
- Platform-agnostic ([Supported Platforms](#supported-platforms))
- Brigadier integration
- Runtime-based command registration and execution
- Optional use of annotations
- Full test coverage with using unit tests and integration tests
- Many more...

*Note*: Some features are only available on certain platforms. Please check the 
[feature matrix](#feature-matrix) below  to see which features are available on which platform.

## Supported Platforms

Currently, Delegate supports the following platforms:

- [x] Bukkit
- [x] Spigot
- [x] Paper
- (WIP) Velocity
- (WIP) Sponge
- (WIP) BungeeCord

## Getting Started

## Quickstart Example

```java
public class ExamplePlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        //  Creates and Register a new Delegate command in one step!
        Delegate.create("hello", "Broadcasts a message to the server")
                .withString("name", "The name of the person you want to say hello to")
                .withConsumerAction((commander, args) -> Bukkit.getServer().broadcastMessage("Hello, %s".formatted(args.get("name"))))
                .build();
    }
    
}
```

When you are finished creating the definition of the command, the `build()` method
can be called. This will construct and register the command behind the scenes. More 
information can be found on the [Delegate Gitbook](https://rocketpencil-studios.gitbook.io/delegate/).

## Feature Matrix

The feature matrix describes which major features are available for which platform and
which are currently planned.

|     Feature      | Spigot | Paper | Velocity | Sponge | BungeeCord | Waterfall | 
|:----------------:|:------:|:-----:|:--------:|:------:|:----------:|:---------:|
| Fluent Interface |   ✔    |   ✔   |   🏗️    |  🏗️   |     ❌      |     ❌     |
| Context Resolver |   ✔    |   ✔   |   🏗️    |  🏗️   |     ❌      |     ❌     |
|    Brigadier     |   ✔    |   ✔   |   🏗️    |  🏗️   |     ❌      |     ❌     |
|  Tab Completion  |   ✔    |   ✔   |   🏗️    |  🏗️   |     ❌      |     ❌     |


## Documentation
Documentation on how to get started is available on the [Delegate Gitbook](https://rocketpencil-studios.gitbook.io/delegate/).

Since Delegate is still in development, the documentation is still a work in progress. A JavaDoc will be available soon.

## Latest Changes

### Version 0.0.1
- First version of Delegate. Currently, in the process of writing unit tests to ensure compatibility with all platforms.

---
<h5 align="center">Icons provided by <a href="https://www.flaticon.com/">Flaticon</a></h5>

---