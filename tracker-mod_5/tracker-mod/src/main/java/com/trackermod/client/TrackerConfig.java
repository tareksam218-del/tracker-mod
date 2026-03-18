package com.trackermod.client;

public class TrackerConfig {

    public static final String[] LABELS = {
        "Shulker Boxes",
        "Chests & Barrels",
        "Hoppers",
        "Rails",
        "Mob Spawners",
        "Pistons"
    };

    // ARGB colors matching what's drawn as lines
    public static final int[] COLORS = {
        0xFFBF60D9,  // purple  - shulkers
        0xFFF2B233,  // gold    - chests
        0xFF8C8C8C,  // gray    - hoppers
        0xFF33D9D9,  // cyan    - rails
        0xFFFF4040,  // red     - spawners
        0xFF33D94D   // green   - pistons
    };

    // All on by default
    public static boolean[] enabled = {true, true, true, true, true, true};
}
