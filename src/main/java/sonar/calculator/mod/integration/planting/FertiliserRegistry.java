package sonar.calculator.mod.integration.planting;

import sonar.calculator.mod.integration.planting.vanilla.Fertiliser;
import sonar.core.utils.helpers.RegistryHelper;

public class FertiliserRegistry extends RegistryHelper<IFertiliser> {

	@Override
	public void register() {
		registerObject(new Fertiliser());
	}

	@Override
	public String registeryType() {
		return "Fertilisers";
	}

}
