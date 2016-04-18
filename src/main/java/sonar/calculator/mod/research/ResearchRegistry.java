package sonar.calculator.mod.research;

import sonar.calculator.mod.research.types.CalculatorResearch;
import sonar.calculator.mod.research.types.RecipeResearch;
import sonar.core.helpers.RegistryHelper;

public class ResearchRegistry extends RegistryHelper<IResearch> {

	@Override
	public void register() {
		registerObject(new CalculatorResearch.Basic());
		registerObject(new CalculatorResearch.Scientific());
		registerObject(new CalculatorResearch.Atomic());
		registerObject(new CalculatorResearch.Flawless());
		registerObject(new RecipeResearch());
	}

	@Override
	public String registeryType() {
		return "Research";
	}

}
