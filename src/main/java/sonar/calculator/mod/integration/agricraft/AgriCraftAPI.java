package sonar.calculator.mod.integration.agricraft;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;

public class AgriCraftAPI {
    private static AgriCraftAPI INSTANCE;

    protected AgriCraftAPI() {}

    public static AgriCraftAPI getInstance() {
        if(INSTANCE == null) {
            if(!isLoaded()) {
                INSTANCE = new AgriCraftAPI();
            } else {
                INSTANCE = new AgriCraftAPIwrapper();
            }
        }
        return INSTANCE;
    }

    public static boolean isLoaded() {
        return Loader.isModLoaded("AgriCraft");
    }

    /**
     * Create needed methods like this, and override them in the wrapper class where they are forwarded to the AgriCraft API
     * Methods here should just return a default value.
     */
    public boolean isPlantingDisabled(ItemStack stack) {
        return false;
    }
}
