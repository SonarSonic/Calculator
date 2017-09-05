package sonar.calculator.mod.common.tileentity.misc;

import sonar.core.api.cabling.ISonarCable;
import sonar.core.common.tileentity.TileEntitySonar;

public class TileEntityPiping extends TileEntitySonar implements ISonarCable {

	public int registryID = -1;
	
    @Override
	public void validate(){
		super.validate();
	}
	
    @Override
	public void invalidate(){
		super.invalidate();
	}

	@Override
	public int registryID() {
		return registryID;
	}		
}
