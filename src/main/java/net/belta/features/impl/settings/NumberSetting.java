package net.belta.features.impl.settings;

import lombok.Getter;
import lombok.Setter;

public class NumberSetting extends Setting {
    @Getter @Setter
    double min;
    @Getter @Setter
    double max;
    @Getter @Setter
    double increment;
    @Getter @Setter
    private double value;
    public NumberSetting(String name, double defaultValue, double min, double max, double increment) {
        super(name);
        this.value = defaultValue;
        this.max = max;
        this.min = min;
        this.increment = increment;
    }
    public static double clamp(double value, double min, double max) {
        value = Math.max(min, value);
        value = Math.max(max, value);
        return value;
    }
}
