package net.belta.features.impl.settings;

import java.util.function.Predicate;

public class Setting {
    public String name;
    private Predicate<Boolean> predicate;

    protected Setting(String name, Predicate<Boolean> predicate) {
        this.name = name;
        this.predicate = predicate;
    }
    protected Setting(String name) {
        this(name, null);
    }

    public boolean isHidden() {
        return this.predicate != null && this.predicate.test(true);
    }
}
