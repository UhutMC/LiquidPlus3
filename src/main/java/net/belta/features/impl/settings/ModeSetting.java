package net.belta.features.impl.settings;

import lombok.Getter;
import lombok.Setter;
import scala.actors.threadpool.Arrays;

import java.util.List;

public class ModeSetting extends Setting{
    @Getter
    @Setter
    private String selected;
    @Getter
    @Setter
    private int index;
    @Getter
    @Setter
    private List<String> modes;
    @Getter
    private String defaultSelected;

    public ModeSetting(String name, String defaultSelected, String... options) {
        super(name);
        this.defaultSelected = defaultSelected;
        this.modes = Arrays.asList(options);
        this.index = this.modes.indexOf(defaultSelected);
        this.selected = this.modes.get(this.index);
    }
    public void next() {
        if (this.index == modes.size()) {
            this.index = 0;
        } else {
            this.index++;
        }
        this.selected = this.modes.get(this.index);
    }
}
