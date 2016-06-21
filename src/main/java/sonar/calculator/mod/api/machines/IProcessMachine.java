package sonar.calculator.mod.api.machines;

/** implemented on machines which have progress bars */
public interface IProcessMachine {

	/** current process */
	public int getCurrentProcessTime();

	/** current speed */
	public int getProcessTime();

	/** normal speed of the machine */
	public int getBaseProcessTime();
	
	/** current energy usage in RF per tick (can be less than 1) */
	public double getEnergyUsage();
	
}