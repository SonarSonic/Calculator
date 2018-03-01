package sonar.calculator.mod.common.tileentity.machines;

import cofh.api.energy.IEnergyConnection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.helpers.SonarHelper;

@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyConnection", modid = "redstoneflux")})
public class TileEntityCreativePowerCube extends TileEntitySonar implements IEnergyConnection {

	public int maxTransfer = Integer.MAX_VALUE;

    @Override
	public void update() {
        super.update();
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity tile = SonarHelper.getAdjacentTileEntity(this, face);
			if (tile != null)
				SonarAPI.getEnergyHelper().receiveEnergy(tile, maxTransfer, face, ActionType.PERFORM);
		}
	}
	
	@Override
    @Optional.Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}
}
