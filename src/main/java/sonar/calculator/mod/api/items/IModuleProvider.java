package sonar.calculator.mod.api.items;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.modules.IModule;

/**implemented on Items which contain modules*/
public interface IModuleProvider {

	public ArrayList<IModule> getModules(ItemStack stack);
}
