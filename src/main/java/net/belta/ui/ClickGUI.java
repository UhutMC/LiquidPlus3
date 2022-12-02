package net.belta.ui;

import net.belta.Olier;
import net.belta.features.impl.Category;
import net.belta.features.impl.Module;
import net.belta.features.impl.settings.ModeSetting;
import net.belta.features.impl.settings.NumberSetting;
import net.belta.features.impl.settings.Setting;
import net.belta.features.impl.settings.StringSetting;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClickGUI extends GuiScreen {
    public static ArrayList<Panel> panels;
    private Module binding;
    private Panel draggingPanel;
    private NumberSetting draggingSlider;
    private StringSetting settingString;
    private float startX;
    private float startY;
    public int guiScale;
    private static int background;

    public ClickGUI() {
        ClickGUI.panels = new ArrayList<Panel>();
        int pwidth = 100;
        int pheight = 20;
        int px = 100;
        final int py = 10;
        for (Category category : Category.values()) {
            ClickGUI.panels.add(new Panel(category, (float)px, (float)py, (float)pwidth, (float)pheight));
            px += pwidth + 10;
        }
    }
    public void handleInput() throws IOException {
        for (Panel panel : ClickGUI.panels) {
            panel.prevScrolling = panel.scrolling;
        }
        super.handleInput();
    }
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            if (scroll > 1) {
                scroll = 1;
            }
            if (scroll < -1) {
                scroll = -1;
            }
            final int mouseX = Mouse.getX() * this.width / this.mc.displayWidth;
            final int mouseZ = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
            this.handle(mouseX, mouseZ, scroll, 0.0f, Handle.SCROLL);
        }
    }
    public boolean isHovered(final int mouseX, final int mouseY, final double x, final double y, final double height, final double width) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
    private int getTotalSettingsCount(Panel panel) {
        int count = 0;
        for (Category category : Category.values()) {
            for (Module module : category.getModuleList()) {
                ++count;
                for (Setting setting : module.getSettings()) {
                    ++count;
                }
                ++count;
            }
        }
        return count;
    }


    public void handle(int mouseX, int mouseY, int key, float partialTicks, Handle handle) {
        int color = Color.DARK_GRAY.getRGB();
        float scale = 2.0f/ Olier.mc.gameSettings.guiScale;
        int prevScale = this.mc.gameSettings.guiScale;
        GL11.glPushMatrix();
        if (prevScale > 2) {
            mc.gameSettings.guiScale = 2;
            mouseX /= (int)scale;
            mouseY /= (int)scale;
            GL11.glScaled((double)scale,(double)scale,(double)scale);
        }
        //TO ADD: BLUR

        for (Panel panel : ClickGUI.panels) {
            switch (handle) {
                case DRAW: {
                    if (this.draggingPanel == panel) {
                        panel.x = this.startX + mouseX;
                        panel.y = this.startY + mouseY;
                    }
                }
                case CLICK: {
                    if (!this.isHovered(mouseX, mouseY, panel.x, panel.y, panel.height, panel.width)) {
                        break;
                    }
                    if (key == 1) {
                        this.startX = panel.x - mouseX;
                        this.startY = panel.y - mouseY;
                        this.draggingPanel = panel;
                        this.draggingSlider = null;
                        break;
                    }
                    if (key == 0) {
                        if (panel.extended) {
                            panel.scrolling = -this.getTotalSettingsCount(panel);
                        }
                        else {
                            panel.scrolling = 0;
                        }
                        panel.extended = !panel.extended;
                        break;
                    }
                    break;
                }
                case RELEASE: {
                    this.draggingPanel = null;
                    this.draggingSlider = null;
                    break;
                }
            }
            float y = panel.y + panel.height + 3.0f;
            int moduleHeight = 15;
            y += moduleHeight * (panel.prevScrolling + (panel.scrolling - panel.prevScrolling));
            final List<Module> list = panel.category.getModuleList().stream().sorted(Comparator.comparingDouble(module -> Fonts.robotoMediumBold.getStringWidth(module.moduleName))).collect(Collectors.toList());
            for (Module module : list) {
                Label_0942: {
                    switch (handle) {
                        case DRAW: {
                            if (y < panel.y) break;
                            RenderUtils.drawRect(panel.x, y, panel.x + panel.width, y + moduleHeight, module.isToggled() ? Color.CYAN.getRGB() : color);
                            Fonts.robotoMediumBold.drawSmoothCenteredString(module.getModuleName(), panel.x + panel.width / 2.0f, y + moduleHeight / 2.0f - Fonts.robotoMedium.getHeight() / 2.0f, Color.WHITE.getRGB());
                            break;
                        }
                        case CLICK: {
                            if (!this.isHovered(mouseX, mouseY, panel.x, y, moduleHeight, panel.width)) {
                                break;
                            }
                            if (y < panel.y + panel.height + 3.0f) {
                                break;
                            }
                            switch (key) {
                                case 1: {
                                    module.extended = !module.extended;
                                    break Label_0942;
                                }
                                case 0: {
                                    module.toggle();
                                    break Label_0942;
                                }
                            }
                            break;
                        }
                    }
                }
                y+=moduleHeight;
                if (!module.extended) continue;
                for (Setting setting : module.settings) {
                    if ((handle == Handle.DRAW && y >= panel.y) || (handle == Handle.CLICK && y >= panel.y + panel.height + 3.0f)) {
                        if (setting instanceof ModeSetting) {
                            switch (handle) {
                                case DRAW: {
                                    RenderUtils.drawRect(panel.x, y, panel.x + panel.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                    Fonts.robotoMediumBold.drawSmoothString(setting.name, panel.x + 2.0f, y + moduleHeight / 2.0f - Fonts.robotoMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                    Fonts.robotoMediumBold.drawSmoothString(((ModeSetting)setting).getSelected(), panel.x + panel.width - Fonts.robotoMediumBold.getStringWidth(((ModeSetting)setting).getSelected()) - 2.0, y + moduleHeight / 2.0f - Fonts.robotoMediumBold.getHeight() / 2.0f, new Color(143, 143, 143, 255).getRGB());
                                    break;
                                }
                                case CLICK: {
                                    if (!this.isHovered(mouseX, mouseY, panel.x + panel.width - Fonts.robotoMediumBold.getStringWidth(((ModeSetting)setting).getSelected()) - 2.0, y, moduleHeight, Fonts.robotoMediumBold.getStringWidth(((ModeSetting)setting).getSelected()))) {
                                        break;
                                    }
                                    ((ModeSetting)setting).next();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    enum Handle
    {
        DRAW,
        CLICK,
        RELEASE,
        SCROLL;
    }
    public static class Panel
    {
        public Category category;
        public float x;
        public float y;
        public float width;
        public float height;
        public boolean dragging;
        public boolean extended;
        public int scrolling;
        public int prevScrolling;

        public Panel(final Category category, final float x, final float y, final float width, final float height) {
            this.category = category;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.extended = true;
            this.dragging = false;
        }
    }
}
