package sonar.calculator.mod.api.items;

import net.minecraft.item.ItemStack;

/**
 * implemented on Locator Modules
 */
public interface ILocatorModule {

    /**
     * the username the module is located to
     */
    String getPlayer(ItemStack stack);
}
