package sonar.calculator.mod.integration.planting;

import sonar.core.utils.helpers.RegistryHelper;

public class FertiliserRegistry extends RegistryHelper<IFertiliser> {

	@Override
	public void register() {

	}

	@Override
	public String registeryType() {
		return "Fertilisers";
	}

}
