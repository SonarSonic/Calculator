package sonar.calculator.mod.research;

import sonar.calculator.mod.research.types.CalculatorResearch;
import sonar.calculator.mod.research.types.RecipeResearch;
import sonar.core.helpers.SimpleIDRegistry;

public class ResearchRegistry extends SimpleIDRegistry<IResearch> {

	public void register() {
		register(new CalculatorResearch.Basic());
		register(new CalculatorResearch.Scientific());
		register(new CalculatorResearch.Atomic());
		register(new CalculatorResearch.Flawless());
		register(new RecipeResearch());
	}

}
