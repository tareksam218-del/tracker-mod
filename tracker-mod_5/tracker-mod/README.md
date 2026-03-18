# Tracker Mod (Fabric 1.21)

Press **G** to toggle colored lines pointing to nearby:

| Target         | Color   |
|----------------|---------|
| Shulker Boxes  | Purple  |
| Chests / Barrels | Gold  |
| Hoppers        | Gray    |
| Rails (all types) | Cyan |
| Mob Spawners   | Red     |

Lines are drawn from your camera to every matching block within **128 blocks**.  
Lines fade with distance. A HUD message confirms ON/OFF.

---

## How to Build & Install

### Requirements
- Java 21 JDK
- Minecraft 1.21 with **Fabric Loader 0.15+** installed
- **Fabric API** mod in your mods folder

### Build
```bash
cd tracker-mod
./gradlew build          # Linux / Mac
gradlew.bat build        # Windows
```

The compiled `.jar` will be at:
```
build/libs/tracker-mod-1.0.0.jar
```

### Install
1. Copy `tracker-mod-1.0.0.jar` into your `.minecraft/mods/` folder.
2. Make sure `fabric-api-*.jar` is also in that folder.
3. Launch Minecraft with the Fabric profile.
4. Join a world and press **G** to toggle the tracker.

---

## Keybind
Default key is **G**. You can remap it in:  
**Options → Controls → Key Binds → Tracker Mod → Toggle Tracker**

## Notes
- Client-side only — works on any server (singleplayer or multiplayer).
- The scan range is 128 blocks. Very large ranges may cause a brief lag spike on first toggle.
- Works with trapped chests, large chests, barrels, all rail types, and all shulker box colors.
