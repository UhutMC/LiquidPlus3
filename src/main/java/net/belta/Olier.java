package net.belta;

import net.belta.ui.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Olier.MODID, version = Olier.VERSION)
public class Olier {
    public static final String MODID = "belta";
    public static final String VERSION = "devbuild";
    public static final boolean devMode = false;
    public static Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        Fonts.bootstrap();
        //code
    }
}
