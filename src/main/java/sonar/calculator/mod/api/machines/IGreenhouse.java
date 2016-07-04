package sonar.calculator.mod.api.machines;

import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;

public interface IGreenhouse {

	public State getState();

	/** get the current oxygen level */
	public int getOxygen();

	/** get the current carbon level */
	public int getCarbon();

	/** get the max gas level */
	public int maxGasLevel();
	
	/** get the greenhouse tier, 0 = basic, 1 = advanced, 2 = flawless */
	public int getTier();
}
