package sonar.calculator.mod.api;

public interface IFluxPoint extends IFlux{

	/**set Max Transfer limit*/
	public int maxTransfer();		

	/**priority order - higher numbers receive energy first*/
	public int priority();	

	/**
	 * @param recieve the maximum which could be sent
	 * @param simulate should energy insertion by simulated
	 * @return
	 */
	public int pushEnergy(int recieve, boolean simulate);		
	
}
