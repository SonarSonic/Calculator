package sonar.calculator.mod.integration.planting;

import sonar.calculator.mod.integration.planting.vanilla.Planter;
import sonar.core.helpers.RegistryHelper;

public class PlanterRegistry extends RegistryHelper<IPlanter> {

	@Override
	public void register() {
		registerObject(new Planter());		
	}

	@Override
	public String registeryType() {
		return "Planters";
	}

}
