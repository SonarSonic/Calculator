package sonar.calculator.mod.api;

import java.util.List;

import net.minecraft.tileentity.TileEntity;

public interface IFlux {

	/**
	 * @return Tile Entity X Coordinate
	 */
	public int xCoord();

	/**
	 * @return Tile Entity Y Coordinate
	 */
	public int yCoord();

	/**
	 * @return Tile Entity Z Coordinate
	 */
	public int zCoord();

	/**
	 * @return Tile Entity Network
	 */
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
