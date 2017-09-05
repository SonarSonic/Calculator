package sonar.calculator.mod.api.items;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.modules.IModule;

import java.util.ArrayList;

/**
 * implemented on Items which contain modules
 */
public interface IModuleProvider {

    ArrayList<IModule> getModules(ItemStack stack);
}
