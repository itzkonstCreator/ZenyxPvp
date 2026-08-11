package ru.zenyx.pvp.settings;

public class NumberSetting extends Setting<Float> {
    private final float min, max, step;
    private float value;

    public NumberSetting(String name, String description, float defaultValue, float min, float max, float step) {
        super(name, description, null, null, null, null);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public Float getValue() { return value; }

    @Override
    public void setValue(Float value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    public float getMin() { return min; }
    public float getMax() { return max; }
    public float getStep() { return step; }

    public float getPercent() {
        return (value - min) / (max - min);
    }
}
