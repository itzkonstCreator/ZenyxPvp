package ru.zenyx.pvp.settings;

public class ColorSetting extends Setting<Integer> {
    private int value;

    public ColorSetting(String name, String description, int defaultRgba) {
        super(name, description, null, null, null, null);
        this.value = defaultRgba;
    }

    @Override
    public Integer getValue() { return value; }

    @Override
    public void setValue(Integer value) { this.value = value; }

    public int getRgba() { return value; }
    public void setRgba(int rgba) { this.value = rgba; }
}
