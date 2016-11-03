package sonar.calculator.mod.common.item.calculators;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.calculators.modules.EnderModule;
import sonar.calculator.mod.common.item.calculators.modules.EnergyModule;
import sonar.calculator.mod.common.item.calculators.modules.GrenadeModule;
import sonar.calculator.mod.common.item.calculators.modules.GuiModule;
import sonar.calculator.mod.common.item.calculators.modules.JumpModule;
import sonar.calculator.mod.common.item.calculators.modules.WarpModule;
import sonar.core.helpers.LinkedRegistryHelper;

public class ModuleItemRegistry extends LinkedRegistryHelper<String, Item> {

	@Override
	public void register() {
		registerMap(GuiModule.flawless.getName(), Calculator.endDiamond);
		registerMap(GuiModule.dynamic.getName(), Calculator.atomic_assembly);
		registerMap(GuiModule.crafting.getName(), Calculator.itemCraftingCalculator);
		registerMap(GuiModule.storage.getName(), Calculator.itemStorageModule);
		registerMap(GuiModule.calculator.getName(), Calculator.itemCalculator);
		registerMap(GuiModule.scientific.getName(), Calculator.itemScientificCalculator);
		registerMap(new EnergyModule().getName(), Calculator.itemEnergyModule);
		registerMap(new GrenadeModule().getName(), Calculator.grenade);
		registerMap(new EnderModule().getName(), Items.ENDER_PEARL);
		registerMap(new WarpModule().getName(), Calculator.itemWarpModule);
		registerMap(new JumpModule().getName(), Calculator.itemJumpModule);
	}

	@Override
	public String registeryType() {
		return "Module Item";
	}

}
