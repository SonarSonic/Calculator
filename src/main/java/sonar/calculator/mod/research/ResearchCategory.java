package sonar.calculator.mod.research;

public enum ResearchCategory {
	/** no items will be uncraftable if you haven't researched, only new recipes will be allowed, this just marks your progress with Calculator*/
	
	/**covers mastering the Calculator, rewards may be given upon mastering each Calculator. 
	 * Mastering a Calculator requires a certain amount of crafts with a specific type*/
	CALCULATORS, 
	/**covers the discovery of additional recipes*/
	RECIPES, 
	/**covers mastering machinery, this may give rewards such as Upgrades for the machines, or even new machines**/
	MACHINES, 
	/**covers mastering Power Generation, specifically involves mastering the Calculator Locator*/
	GENERATORS, 
	/**involves usage of all the greenhouses and health and hunger stuff*/
	NUTRITION;
}
