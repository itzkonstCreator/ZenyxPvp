package ru.zenyx.pvp.settings;

public class KeybindSetting extends Setting<Integer> {
    private int value;

    public KeybindSetting(String name, String description, int defaultKey) {
        super(name, description, null, null, null, null);
        this.value = defaultKey;
    }

    @Override
    public Integer getValue() { return value; }

    @Override
    public void setValue(Integer value) { this.value = value; }
}
