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

## Supported Platforms

Currently, Delegate supports the following platforms:

- Bukkit
- Spigot
- Paper
- Velocity

## Getting Started

This is an example command, which will print a message to the requested player.

```java
public class ExampleCommand {
    
    public ExampleCommand() {
        //  TODO
    }
    
}
```

When you are finished creating the definition of the command, the `build()` method 
must be called. This will construct and register the command behind the scenes. Keep in 
mind that this process is completely platform-agnostic, which means that the procedure 
for creating commands is exactly the same for all platforms. 

## Examples

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