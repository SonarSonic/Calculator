package sonar.calculator.mod.api.machines;

public interface IFlawlessGreenhouse extends IGreenhouse {

    /**
     * gets how many plants have been harvested
     */
    int getPlantsHarvested();

    /**
     * gets how many plants have been grown(bone-mealed)
     */
    int getPlantsGrown();
}
