package sonar.calculator.mod.api.machines;

public interface IGreenhouse {

	/** is the greenhouse complete */
	public boolean isCompleted();

	/** is the greenhouse being built */
	public boolean isBeingBuilt();

	/** is the greenhouse incomplete */
	public boolean isIncomplete();

	/** was the build process completed */
	public boolean wasBuilt();

	/** get the current oxygen level */
	public int getOxygen();

	/** get the current carbon level */
	public int getCarbon();

	/** get the max gas level */
	public int maxGasLevel();
	
	/** get the greenhouse tier, 0 = basic, 1 = advanced, 2 = flawless */
	public int getTier();
}
