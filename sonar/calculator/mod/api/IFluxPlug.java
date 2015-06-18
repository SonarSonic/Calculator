package sonar.calculator.mod.api;

public interface IFluxPlug extends IFlux{

	public boolean isMaster();	
		
	public int pullEnergy(int export, boolean simulate);
}
