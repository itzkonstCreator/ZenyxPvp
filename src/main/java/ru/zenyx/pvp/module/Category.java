package ru.zenyx.pvp.module;

/**
 * Categories shown both in the HUD overlay and the in-game ClickGUI.
 */
public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    HUD("HUD"),
    MISC("Misc");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
