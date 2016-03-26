package sonar.calculator.mod.integration.planting;

import sonar.calculator.mod.integration.planting.vanilla.Harvester;
import sonar.core.helpers.RegistryHelper;

public class HarvesterRegistry extends RegistryHelper<IHarvester> {

	@Override
	public void register() {
		registerObject(new Harvester());
	}

	@Override
	public String registeryType() {
		return "Harvesters";
	}

}
