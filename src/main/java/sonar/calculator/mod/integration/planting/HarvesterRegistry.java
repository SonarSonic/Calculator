package sonar.calculator.mod.integration.planting;

import sonar.core.utils.helpers.RegistryHelper;

public class HarvesterRegistry extends RegistryHelper<IHarvester> {

	@Override
	public void register() {
		
	}

	@Override
	public String registeryType() {
		return "Harvesters";
	}

}
