<p align="center">
    <img width="128" height="128" src="img/delegate.png" />
</p>

<h1 align="center">Delegate</h1>

Delegate is a simple yet powerful command delegation framework for Bukkit/Spigot/Paper plugins.
It allows for the creation, management and execution of commands.

## Why choose Delegate?

In the past, many command frameworks have been created for various Minecraft server
implementations. Personally, I felt like most of these weren't exactly what I
was personally looking for. These command frameworks where either lacking features
or becoming convoluted very quickly, or simply didn't support enough platforms.

Delegate mitigates these problems by allowing the creation of simple commands, but 
also allows for the creation of complex commands with subcommands, tab completion,
brigadier support and more. Unlike most command frameworks, at it's core Delegate
does not use annotation based command creation (it is however possible to use 
annotations). Furthermore, Delegate is available for pretty much every major
Minecraft server implementation.

## Features

- Simple yet powerful command creation
- Autonomous command handling
- Platform-agnostic ([Supported Platforms](#supported-platforms))
- Brigadier integration
- Automatic tab completion
- Runtime-based command registration and execution
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
        Delegate.getFactory().create("hello", "Broadcasts a message to the server")
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

---
<h5 align="center">Icons provided by <a href="https://www.flaticon.com/">Flaticon</a></h5>

---