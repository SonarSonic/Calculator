package sonar.calculator.mod.common.item.calculators;

import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.item.calculators.modules.*;
import sonar.core.helpers.RegistryHelper;

public class ModuleRegistry extends RegistryHelper<IModule> {

	@Override
	public void register() {
		registerObject(GuiModule.flawless);
		registerObject(GuiModule.dynamic);
		registerObject(GuiModule.crafting);
		registerObject(GuiModule.storage);
		registerObject(GuiModule.calculator);
		registerObject(GuiModule.scientific);
		registerObject(new EnergyModule());
		registerObject(new GrenadeModule());
		registerObject(new EnderModule());
		registerObject(new JumpModule());
		registerObject(new WarpModule());
	}

	@Override
	public String registeryType() {
		return "Flawless Calculator Modes";
	}
}
