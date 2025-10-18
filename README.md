# Auto Summon Mod

A Necesse mod that automatically summons using the rightmost staff in your hotbar when you're below your maximum summon count.

## Features

- **Automatic Summoning**: Automatically uses the rightmost summoning staff in your hotbar
- **Smart Detection**: Only summons when you're below your maximum summon count
- **Configurable Settings**: Adjust check frequency and other settings
- **Debug Output**: Optional debug messages to verify the mod is working

## Installation

1. Download the latest `AutoSummon-1.0.0-1.0.jar` from the releases
2. Place the JAR file in your Necesse mods folder
3. Start the game and the mod will be automatically loaded

## Configuration

You can modify the settings by editing the `CraftingRangeConfig.java` file before building:

- `SUMMON_CHECK_COOLDOWN`: How often to check for summoning in milliseconds (default: 250ms = 4 times per second)
- `SCAN_RIGHT_TO_LEFT`: Whether to scan hotbar from right to left for rightmost staff (default: true)
- `DEBUG_OUTPUT`: Enable/disable debug messages (default: true)

## How It Works

The mod patches the `PlayerMob.clientTick` method to:
1. Check if you're below your maximum summon count
2. Scan your hotbar from right to left (slots 9 to 0)
3. Find the first summoning staff that can be used
4. Automatically use that staff to summon a minion

## Building

To build the mod yourself:

1. Clone this repository
2. Run `./gradlew buildModJar`
3. The built JAR will be in `build/jar/`

## Requirements

- Necesse 1.0.0
- Java 8 or higher