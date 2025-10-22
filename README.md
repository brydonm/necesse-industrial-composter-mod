# Industrial Composter Mod

A Necesse mod that adds an Industrial Composter - a larger, faster version of the standard compost bin.

## Features

- **Faster Processing**: Processes composting materials 6 times faster than the standard compost bin
- **1x1 Size**: Same footprint as the standard compost bin
- **Increased Capacity**: Can queue more crafts at once for efficient bulk processing
- **Settlement Compatible**: Works as a settlement workstation just like the standard compost bin

## Installation

1. Download the latest `IndustrialComposter-1.0.1-1.0.jar` from the releases
2. Place the JAR file in your Necesse mods folder
3. Start the game and the mod will be automatically loaded

## Crafting Recipe

The Industrial Composter can be crafted at a workstation with:
- 4x Compost Bin
- 10x Iron Bar
- 20x Stone

## How It Works

The Industrial Composter functions identically to the standard compost bin but with enhanced performance:
- **Processes items faster** by reducing process time to 1/6 of vanilla (`getProcessTime()` override)
- **Occupies 1x1 tile** - same size as standard compost bin
- **4 input slots** - process multiple items simultaneously (vs 1 for standard)
- **Allows queuing up to 10 crafts at once** (vs 5 for standard)
- **Custom industrial graphics** - distinct appearance from the standard compost bin

## Building

To build the mod yourself:

1. Clone this repository
2. Ensure the `gameDirectory` path in `build.gradle` points to your Necesse installation
3. Run `./gradlew buildModJar` (Mac/Linux) or `gradlew.bat buildModJar` (Windows)
4. The built JAR will be in `build/jar/`

## Requirements

- Necesse 1.0.1
- Java 8 or higher

## Technical Details

The mod consists of:
- `IndustrialComposterObject`: The GameObject definition with 2x2 multiTile configuration
- `IndustrialComposterObjectEntity`: Custom entity that overrides `getProcessTime()` to run faster
- Localization support for English
- Recipe integration with the vanilla workstation tech
