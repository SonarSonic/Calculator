package sonar.calculator.mod.api.items;

import java.util.ArrayList;

import sonar.calculator.mod.api.modules.IModule;
import net.minecraft.item.ItemStack;

/**implemented on Items which contain modules*/
public interface IModuleProvider {

	public ArrayList<IModule> getModules(ItemStack stack);
}
