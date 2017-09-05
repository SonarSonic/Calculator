package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.core.api.SonarAPI;
import sonar.core.api.energy.EnergyMode;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.IMachineSides;
import sonar.core.utils.MachineSideConfig;
import sonar.core.utils.MachineSides;

import java.util.ArrayList;

public class TileEntityAdvancedPowerCube extends TileEntityPowerCube implements IGuiTile, IMachineSides {

	public MachineSides sides = new MachineSides(MachineSideConfig.INPUT, this, MachineSideConfig.NONE);

	public TileEntityAdvancedPowerCube() {
		syncList.addParts(sides);
		super.storage.setCapacity(100000).setMaxTransfer(64000);
		super.maxTransfer = 100000;
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

    @Override
	public void update() {
		super.update();
		if (this.isClient()) {
			return;
		}
		this.addEnergy();
		// this.markDirty();
	}

	public void addEnergy() {
		ArrayList<EnumFacing> facing = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		if (facing.isEmpty()) {
			return;
		}
		int transfer = maxTransfer / facing.size();
		for (EnumFacing dir : facing) {
			TileEntity entity = SonarHelper.getAdjacentTileEntity(this, dir);
            SonarAPI.getEnergyHelper().transferEnergy(this, entity, dir, dir, transfer);
		}
	}

	@Override
	public EnergyMode getModeForSide(EnumFacing side) {
		if (side != null) {
			return sides.getSideConfig(side).isInput() ? EnergyMode.RECIEVE : sides.getSideConfig(side).isOutput() ? EnergyMode.SEND : EnergyMode.BLOCKED;
		}
		return super.getModeForSide(side);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiPowerCube(player.inventory, this);
	}

	@Override
	public MachineSides getSideConfigs() {
		return sides;
	}
}
