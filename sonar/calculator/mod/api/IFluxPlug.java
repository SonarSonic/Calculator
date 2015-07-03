package sonar.calculator.mod.api;

public interface IFluxPlug extends IFlux{

	/**
	 * 
	 * @return if this plug can control energy flow.
	 */
	public boolean isMaster();	
	
	/**used for getting maximum sending energy.
	 * @param export maximum send
	 * @param simulate
	 * @return 
	 */
	public int pullEnergy(int export, boolean simulate);
}
