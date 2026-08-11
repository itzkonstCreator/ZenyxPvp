# Zenyx PvP

A lightweight PvP client for Minecraft **1.21.4** built on Fabric. Includes HUD,
ClickGUI, combat / movement / render / player modules, and 2D + 3D ESP.

> ⚠️  Use only on servers that allow client modifications or in singleplayer.
> The authors are not responsible for bans on any server.

## Highlights

- **Combat**: KillAura, Reach, Hitbox, AutoClicker.
- **Movement**: Scaffold, Tower, NoFall, Anti-Knockback, Sprint, Speed, Fly,
  NoSlow, FastPlace.
- **Render**: ESP, Tracers, BlockESP, StorageESP, NameTags, Fullbright,
  NoHurtCam, Crosshair, JumpEffect.
- **Player**: AutoTotem, ChestStealer, NoWeather.
- **HUD**: FPS, BPS, Coords, Keystrokes, CPS, Armor, Compass — draggable with
  Shift + LMB.
- **ClickGUI**: in-game menu (default key: **Right Shift**).

## Building

```bash
./gradlew build      # produces build/libs/zenyx-pvp-<version>.jar
./gradlew runClient  # launches a 1.21.4 client for testing
```

Requires JDK 21.

## Project layout

```
src/main/java/ru/zenyx/pvp/
├── ZenyxPvp.java               Entry point (ClientModInitializer)
├── event/                      Event bus + event types
├── module/                     Category, Module base, ModuleManager
│   ├── combat/                 KillAura, Reach, Hitbox, AutoClicker
│   ├── movement/               Scaffold, Tower, NoFall, AntiKnockback, ...
│   ├── render/                 ESP, Tracers, BlockESP, Fullbright, ...
│   ├── player/                 AutoTotem, ChestStealer, NoWeather
│   └── hud/                    FPS, BPS, Coords, Keystrokes, HUD editor
├── settings/                   Boolean/Number/Mode/Color/Keybind settings
├── renderer/                   Renderer2D, Renderer3D
├── gui/                        ZenyxClickGui, CategoryFrame, ModuleButton
├── util/                       PlayerUtils, BlockUtils, MathUtils, ...
├── config/                     ConfigManager (JSON save/load)
└── mixin/                      Mixin entrypoints
```

## License

MIT — see `LICENSE`.
