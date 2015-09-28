package sonar.calculator.mod.api;

import io.netty.buffer.ByteBuf;
import sonar.calculator.mod.utils.FluxNetwork;
import cpw.mods.fml.common.network.ByteBufUtils;

public interface ITeleport {

	/**assigned frequency*/
	public int teleporterID();

	/**tile dimension*/
	public int dimension();
	
	/**0=Public, 1=Private*/
	public int accessSetting();
	
	/**tile xCoord*/
	public int xCoord();

	/**tile yCoord*/
	public int yCoord();

	/**tile zCoord*/
	public int zCoord();

	/**user assigned name*/
	public String name();
	
}
