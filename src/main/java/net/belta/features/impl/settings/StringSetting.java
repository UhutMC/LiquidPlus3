package net.belta.features.impl.settings;

import lombok.Getter;
import lombok.Setter;

public class StringSetting extends Setting {
    @Getter
    @Setter
    private String value;
    int length;
    public StringSetting(String name, String defaultValue, int length) {
        super(name);
        this.value = defaultValue;
        this.length = length;
    }
}
