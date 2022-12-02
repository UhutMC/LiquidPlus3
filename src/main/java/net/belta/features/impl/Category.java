package net.belta.features.impl;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public enum Category {
    COMBAT("Combat", 50, 300),
    PLAYER("Player", 130, 300),
    WORLD("World", 200, 300),
    MISC("Misc", 270, 300);
    @Getter
    private String categoryName;
    @Getter
    @Setter
    private int x,y;
    public final List<Module> MODULE_LIST = new ArrayList<Module>();
    Category(String categoryName, int x, int y) {
        this.categoryName = categoryName;
        this.x = x;
        this.y = y;
    }
    public void addModule(Module module) {
        MODULE_LIST.add(module);
    }
    public List<Module> getModuleList() {
        return MODULE_LIST;
    }
}
