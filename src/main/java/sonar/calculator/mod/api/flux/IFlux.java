package sonar.calculator.mod.api.flux;

import sonar.core.api.BlockCoords;

/**extended by IFluxPoint & IFluxPlug you must use them if you wish to send and receive energy from the network*/
public interface IFlux {

	
	public BlockCoords getCoords();	

	/** @return Tile Entity X Coordinate */
	public int networkID();

	/**
	 * @return owners name
	 */
	public String masterName();


}
