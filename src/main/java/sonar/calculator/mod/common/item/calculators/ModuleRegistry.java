package sonar.calculator.mod.common.item.calculators;

import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.item.calculators.modules.*;
import sonar.core.helpers.SimpleIDRegistry;

public class ModuleRegistry extends SimpleIDRegistry<IModule> {

	public void register() {
		register(GuiModule.flawless);
		register(GuiModule.dynamic);
		register(GuiModule.crafting);
		register(GuiModule.storage);
		register(GuiModule.calculator);
		register(GuiModule.scientific);
		register(new EnergyModule());
		register(new GrenadeModule());
		register(new EnderModule());
		register(new JumpModule());
		register(new WarpModule());
	}
}
