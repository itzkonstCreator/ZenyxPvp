package ru.zenyx.pvp.settings;

public class BooleanSetting extends Setting<Boolean> {
    private boolean value;

    public BooleanSetting(String name, String description, boolean defaultValue) {
        super(name, description, () -> /* unused, overridden */ false, v -> {}, null, null);
        this.value = defaultValue;
    }

    @Override
    public Boolean getValue() { return value; }

    @Override
    public void setValue(Boolean value) { this.value = value; }

    public boolean isEnabled() { return value; }
    public void toggle() { this.value = !this.value; }
}
