package ru.zenyx.pvp.settings;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Generic value holder used by every module setting (boolean, slider, mode,
 * colour, keybind, string). Holds a {@link Consumer} that is fired whenever the
 * value changes so the UI can react without polling.
 */
public abstract class Setting<T> {
    private final String name;
    private final String description;
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final Consumer<T> onChange;
    private final Runnable onChanged;

    protected Setting(String name, String description, Supplier<T> getter, Consumer<T> setter, Consumer<T> onChange, Runnable onChanged) {
        this.name = name;
        this.description = description;
        this.getter = getter;
        this.setter = setter;
        this.onChange = onChange;
        this.onChanged = onChanged;
    }

    public T getValue() { return getter.get(); }
    public void setValue(T value) {
        setter.accept(value);
        if (onChange != null) onChange.accept(value);
        if (onChanged != null) onChanged.run();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}
