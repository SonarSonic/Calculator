package sonar.calculator.mod.api;

import net.minecraft.tileentity.TileEntity;

public interface IFlux {
		
	public int xCoord();

	public int yCoord();
	
	public int zCoord();

	public int freq();		
	
	public int dimension();	
}
