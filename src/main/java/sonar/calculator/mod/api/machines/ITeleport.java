package sonar.calculator.mod.api.machines;

import sonar.core.utils.BlockCoords;


public interface ITeleport {

	/**assigned frequency*/
	public int teleporterID();

	/**user assigned name*/
	public String name();

	/**teleporter coordinates*/
	public BlockCoords getCoords();
}
