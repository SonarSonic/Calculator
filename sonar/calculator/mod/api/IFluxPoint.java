package sonar.calculator.mod.api;

public interface IFluxPoint extends IFlux{

	/**set Max Transfer limit*/
	public int maxTransfer();		

	/**priority order - higher numbers receive energy first*/
	public int priority();	

	
	public int pushEnergy(int recieve, boolean simulate);		
	
}
