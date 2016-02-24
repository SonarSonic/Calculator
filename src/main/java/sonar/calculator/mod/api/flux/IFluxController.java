package sonar.calculator.mod.api.flux;

public interface IFluxController extends IFluxPoint {

	public int getRecieveMode();

	public int getTransmitterMode();
	
	public int getSendMode();
	
	public int getProtectionMode();
	
	public boolean validPlayer(String player);
}
