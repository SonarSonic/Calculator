package sonar.calculator.mod.api;

/**used on Tile Entities which can remove energy from a Flux Network*/
public interface IFluxPoint extends IFlux{

	/**set Max Transfer limit*/
	public int maxTransfer();		

	/**priority order - higher numbers receive energy first*/
	public int priority();	

	/**
	 * @param recieve the maximum which could be sent
	 * @param simulate should energy insertion by simulated
	 * @return amount that was accepted
	 */
	public int pushEnergy(int recieve, boolean simulate);		
	
}
