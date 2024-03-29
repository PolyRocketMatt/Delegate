![State](https://img.shields.io/badge/State-ALPHA-red?style=for-the-badge)
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/PolYRocketMatt/delegate/build_deploy.yml?color=68AD63&style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-%2368AD63?style=for-the-badge)
![Kotlin](https://img.shields.io/badge/Java-16-%233e7fa8?logo=java&style=for-the-badge)

<p align="center">
    <img width="128" height="128" src="img/delegate.png" />
</p>

<h1 align="center">Delegate</h1>

Delegate is a general-purpose command framework specifically designed for different Minecraft server platforms.
Developers can use this framework, designed with fluent interfaces in mind to automatically parse commands, resolve
contexts and execute actions.

## Why choose Delegate?

In the past, many command frameworks have tried to solve the problem of allowing developers to
create commands in a simple and intuitive way. However, most of these frameworks either
became convoluted very quickly, or simply didn't support enough platforms. Delegate
aims to solve these problems by allowing the creation of simple commands, but also allows
for the creation of complex commands with subcommands, tab completion, brigadier support
and more. Furthermore, Delegate is completely tested with unit tests and use-case tests, ensuring
the default implementations will work as intended on all platforms.

## Features

- Simple and powerful command creation/execution
- Autonomous command handling
- Platform-agnostic ([Supported Platforms](#supported-platforms))
- Brigadier integration
- Automatic tab completion
- Runtime-based command registration and execution
- Optional use of annotations
- Full test coverage with using unit tests and integration tests
- Many more...

*Note*: Some features are only available on certain platforms. Please check the 
[feature matrix](#feature-matrix) below  to see which features are available on which platform.

## Supported Platforms

Currently, Delegate supports the following platforms:

- Bukkit
- Spigot
- Paper
- Velocity

## Getting Started

## Examples

```java
public class ExamplePlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        //  Create / Register a new Delegate command
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