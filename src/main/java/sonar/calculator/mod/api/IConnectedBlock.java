package sonar.calculator.mod.api;

/**used on blocks which can connect to Connected Block types*/
public interface IConnectedBlock {
	/**
	 * @return list of possible connections, see ConnectedBlock for connection types
	 */
	public int[] getConnections();	
}
