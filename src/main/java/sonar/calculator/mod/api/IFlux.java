package sonar.calculator.mod.api;

import java.util.List;

import net.minecraft.tileentity.TileEntity;

/**extended by IFluxPoint & IFluxPlug you must use them if you wish to send and receive energy from the network*/
public interface IFlux {

	/** @return Tile Entity X Coordinate */
	public int xCoord();


	/** @return Tile Entity Y Coordinate */
	public int yCoord();

	/** @return Tile Entity Z Coordinate */
	public int zCoord();


	/** @return Tile Entity X Coordinate */
	public int networkID();

	/**
	 * @return owners name
	 */
	public String masterName();

	/**
	 * @return Tile Entities containing dimension ID
	 */
	public int dimension();

}
