package net.belta.features.impl.combat;

import net.belta.features.impl.Category;
import net.belta.features.impl.Module;
import org.lwjgl.input.Keyboard;

public class Killaura extends Module {

    public Killaura() {
        super("Killaura", Keyboard.KEY_NONE, Category.COMBAT);
    }
}
