package sonar.calculator.mod.api.machines;

import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;

public interface IGreenhouse {

    State getState();

    /**
     * get the current oxygen level
     */
    int getOxygen();

    /**
     * get the current carbon level
     */
    int getCarbon();

    /**
     * get the max gas level
     */
    int maxGasLevel();
	
    /**
     * get the greenhouse tier, 0 = basic, 1 = advanced, 2 = flawless
     */
    int getTier();
}
