package sonar.calculator.mod.api.machines;

public interface ICalculatorLocator {

    /**
     * the user-name of who located the locator, may equal "None"
     */
    String getOwner();

    /**
     * the size of the locator, or number of rings of plugs surrounding it
     */
    int getSize();

    /**
     * returns if the locator is currently generating
     */
    boolean isActive();
	
    /**
     * returns percentage of how many plugs are stable
     */
    double getStabilityPercent();
}
