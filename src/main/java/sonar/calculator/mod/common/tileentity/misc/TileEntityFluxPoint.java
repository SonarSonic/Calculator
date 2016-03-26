package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.api.flux.IFluxPoint;
import sonar.calculator.mod.client.gui.misc.GuiFluxPoint;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.utils.IGuiTile;

public class TileEntityFluxPoint extends TileEntityFluxHandler implements IFluxPoint, IGuiTile {

	public int priority, maxTransfer = 128000;

	@Override
	public int maxTransfer() {
		return maxTransfer;
	}

	@Override
	public int priority() {
		return this.priority;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.priority = nbt.getInteger("priority");
			this.maxTransfer = nbt.getInteger("maxTransfer");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("priority", priority);
			nbt.setInteger("maxTransfer", maxTransfer);

		}
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerFlux(player.inventory, this, false);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiFluxPoint(player.inventory, this);
	}

}
