package net.belta.features.impl;

import lombok.Getter;
import lombok.Setter;
import net.belta.features.impl.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class Module {
    @Getter
    @Setter
    private boolean isToggled;
    @Getter
    public String moduleName;
    @Getter
    public int toggleKey;
    public boolean extended;
    @Getter
    public Category moduleCategory;
    @Getter
    public final List<Setting> settings = new ArrayList<Setting>();;

    public Module(String moduleName, int toggleKey, Category moduleCategory) {
        //set values
        this.moduleName = moduleName;
        this.toggleKey = toggleKey;
        this.moduleCategory = moduleCategory;

        //add module to correct list (ENUM HOUSES MODULE LIST)
        moduleCategory.addModule(this);
    }
    public void onEnable() {}
    public void onDisable() {}

    public void toggle() {
        isToggled = !isToggled;
    }


    public void setToggled(boolean value) {
        isToggled = value;
        if (isToggled) this.onEnable();
        else this.onDisable();
    }
    public void addSettings(Setting ...settings) {
        for (Setting setting : settings) {
            this.getSettings().add(setting);
        }
    }

}
