package net.belta.features.impl.settings;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

public class BooleanSetting extends Setting {
    @Getter
    @Setter
    private boolean enabled;

    public BooleanSetting(String name, boolean enabled) {
        super(name);
        this.enabled = enabled;
    }

    public BooleanSetting(String name, boolean enabled, Predicate<Boolean> isHidden) {
        super(name, isHidden);
        this.enabled = enabled;
    }
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
