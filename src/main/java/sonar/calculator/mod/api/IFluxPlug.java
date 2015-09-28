package sonar.calculator.mod.api;

/**used on Tile Entities which can add energy to a Flux Network*/
public interface IFluxPlug extends IFlux{

	/** @return if this plug can control energy flow. This can only be used by a Flux Plug*/
	public boolean isMaster();	
	
	/**used for getting maximum sending energy.
	 * @param export maximum send
	 * @param simulate
	 * @return amount that was used up
	 */
	public int pullEnergy(int export, boolean simulate);
}
