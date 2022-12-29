<p align="center">
    <img width="128" height="128" src="img/delegate.png" />
</p>

<h1 align="center">Delegate</h1>

Delegate is a simple yet powerful command delegation framework for Bukkit/Spigot/Paper plugins.
It allows for the creation, management and execution of commands.

## Why choose Delegate?

In the past many command frameworks have been created for various Minecraft server
implementations. Personally, I feel like most of these weren't exactly what I
was personally looking for. These command frameworks where either lacking features
or becoming convoluted very quickly. 

Delegate mitigates these problems by allowing the creation of simple commands, but 
also allows for the creation of complex commands with subcommands, tab completion,
brigadier support and more. Unlike most command frameworks, at it's core Delegate
does not use annotation based command creation (it is however possible to use 
annotations).

## Features

- Simple yet powerful command creation
- Autonomic command handling
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

Delegate is designed with [fluent interfaces](https://en.wikipedia.org/wiki/Fluent_interface) in mind.
This means that you can easily chain methods together to create a command. For example:

```java
public class ExamplePlugin extends JavaPlugin {
    
    @Override
    public void onLoad() {
        //  Create / Register a new Delegate command
        Delegate.getFactory().create("hello", "Broadcasts a message to the server")
                .withString("name", "The name of the person you want to say hello to")
                .withAction((str) -> Bukkit.getServer().broadcastMessage("Hello, %s".formatted(str)))
                .build();
    }
    
}
```

When you are finished creating the definition of the command, the `build()` method
can be called. This will construct and register the command behind the scenes. If you
do not wish to register the command, you can use the `construct()` method instead.

## Feature Matrix

|      Feature      | Paper  | Velocity | Sponge |
|:-----------------:|:------:|:--------:|:------:|
| Fluent Interface  |   ✔    |    ❌     |   ❌    |
|     Brigadier     |   ✔    |    ❌     |   ❌    |
|  Tab Completion   |   ✔    |    ❌     |   ❌    |


## Documentation

## Integration

<p align="center">
    <img width="48" height="48" src="img/ensemble.png" />
</p>

Delegate is integrated with [Ensemble](https://github.com/PolyRocketMatt/Ensemble). It ensures command execution/handling is
atomic per user. Furthermore, the orchestrating nature of Ensemble allows for the handling of thousands of commands per second,
without any performance degradation*.

(*: This depends on the actions associated with commands!)

---
<h5 align="center">Icons provided by [Flaticon](https://www.flaticon.com/)</h5>

---