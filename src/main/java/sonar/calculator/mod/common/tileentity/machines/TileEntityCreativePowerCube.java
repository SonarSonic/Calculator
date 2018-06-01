package sonar.calculator.mod.common.tileentity.machines;

import cofh.redstoneflux.api.IEnergyConnection;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import sonar.core.api.energy.EnergyType;
import sonar.core.api.utils.ActionType;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.handlers.energy.EnergyTransferHandler;
import sonar.core.helpers.SonarHelper;

@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyConnection", modid = "redstoneflux")})
public class TileEntityCreativePowerCube extends TileEntitySonar implements IEnergyConnection {

	public int maxTransfer = Integer.MAX_VALUE;

    @Override
	public void update() {
        super.update();
		EnergyTransferHandler.INSTANCE_SC.getAdjacentHandlers(world, pos, SonarHelper.getEnumFacingValues()).forEach(h -> {
			if(h.canAddEnergy()){
				EnergyTransferHandler.INSTANCE_SC.addEnergy(h, maxTransfer, EnergyType.FE, ActionType.PERFORM);
			}
		});
	}
	
	@Override
    @Optional.Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}
}
