package sonar.calculator.mod.research;

import sonar.core.helpers.LinkedRegistryHelper;

public class ResearchRegistry extends LinkedRegistryHelper<String, IResearch> {

	@Override
	public void register() {
		registerMap(secondary, primary);
	}

	@Override
	public String registeryType() {
		return "Research";
	}

}
