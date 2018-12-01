package sonar.calculator.mod.common.item.calculators;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.calculators.modules.*;
import sonar.core.helpers.SimpleRegistry;

public class ModuleItemRegistry extends SimpleRegistry<String, Item> {

	public static ModuleItemRegistry instance(){
		return Calculator.instance.moduleItems;
	}

	public static void register() {
		ModuleItemRegistry.instance().register(GuiModule.flawless.getName(), Calculator.flawless_assembly);
		ModuleItemRegistry.instance().register(GuiModule.dynamic.getName(), Calculator.atomic_assembly);
		ModuleItemRegistry.instance().register(GuiModule.crafting.getName(), Calculator.itemCraftingCalculator);
		ModuleItemRegistry.instance().register(GuiModule.storage.getName(), Calculator.itemStorageModule);
		ModuleItemRegistry.instance().register(GuiModule.calculator.getName(), Calculator.itemCalculator);
		ModuleItemRegistry.instance().register(GuiModule.scientific.getName(), Calculator.itemScientificCalculator);
		ModuleItemRegistry.instance().register(new EnergyModule().getName(), Calculator.itemEnergyModule);
		ModuleItemRegistry.instance().register(new GrenadeModule().getName(), Calculator.grenade);
		ModuleItemRegistry.instance().register(new EnderModule().getName(), Items.ENDER_PEARL);
		ModuleItemRegistry.instance().register(new WarpModule().getName(), Calculator.itemWarpModule);
		ModuleItemRegistry.instance().register(new JumpModule().getName(), Calculator.itemJumpModule);
	}
}
