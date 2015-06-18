package sonar.calculator.mod.api;

public interface IFluxPoint extends IFlux{

	public int maxTransfer();		

	public int priority();	

	public int pushEnergy(int recieve, boolean simulate);	
}
