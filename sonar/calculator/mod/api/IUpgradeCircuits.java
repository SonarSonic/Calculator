package sonar.calculator.mod.api;


/**for use on tile entities which can accept circuits*/
public interface IUpgradeCircuits {
	
	/**use this to check if the block can accept upgrades in its current state
	 * this normally involves making sure it isn't in operation*/
	public abstract boolean canAddUpgrades();

	/**checks if you can add a certain type of circuit
	 * @param type Speed=0, Energy=1, Void=2*/
	public abstract boolean canAddUpgrades(int type);
	
	/**@param type you wish to get
	 * @param type Speed=0, Energy=1, Void=2*/
	public abstract int getUpgrades(int type);
	
	/**@param type circuit 
	 * @return max number installed*/
	public abstract int getMaxUpgrades(int type);
	
	/**@param type circuit type to increment*/
	public abstract void incrementUpgrades(int type, int increment);	

}
