package ru.zenyx.pvp.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting<String> {
    private final List<String> modes;
    private int index;

    public ModeSetting(String name, String description, String defaultMode, String... modes) {
        super(name, description, null, null, null, null);
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
        if (index == -1) index = 0;
    }

    @Override
    public String getValue() {
        return modes.get(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (index < 0 || index >= modes.size()) return;
        this.index = index;
    }

    public boolean is(String mode) {
        return getValue().equals(mode);
    }

    public void cycle() {
        index = (index + 1) % modes.size();
    }

    public List<String> getModes() {
        return modes;
    }

    @Override
    public void setValue(String value) {
        int idx = modes.indexOf(value);
        if (idx != -1) index = idx;
    }
}
