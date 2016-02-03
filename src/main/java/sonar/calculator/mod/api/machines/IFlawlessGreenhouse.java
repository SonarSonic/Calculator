package sonar.calculator.mod.api.machines;

public interface IFlawlessGreenhouse extends IGreenhouse {

	/** gets how many plants have been harvested */
	public int getPlantsHarvested();

	/** gets how many plants have been grown(bone-mealed) */
	public int getPlantsGrown();
}
