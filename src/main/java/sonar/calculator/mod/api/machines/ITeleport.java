package sonar.calculator.mod.api.machines;


public interface ITeleport {

	/**assigned frequency*/
	public int teleporterID();

	/**tile dimension*/
	public int dimension();
	
	/**tile xCoord*/
	public int xCoord();

	/**tile yCoord*/
	public int yCoord();

	/**tile zCoord*/
	public int zCoord();

	/**user assigned name*/
	public String name();
	
}
