package sonar.calculator.mod.common.tileentity.generators;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.generators.GuiCrankedGenerator;
import sonar.calculator.mod.common.containers.ContainerCrankedGenerator;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.utils.IGuiTile;

public class TileEntityCrankedGenerator extends TileEntityEnergy implements IGuiTile {

	public boolean cranked;
	public int ticks;
	public int ticksforpower = 2;

	public TileEntityCrankedGenerator() {
		super.storage.setCapacity(1000).setMaxTransfer(200);
		super.maxTransfer = 32;
		super.energyMode=EnergyMode.SEND;
	}

	@Override
	public void update() {
		super.update();
		if (cranked()) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) this.getWorld().getTileEntity(pos.offset(EnumFacing.UP));
			if (crank.angle > 0) {
				if (ticks == 0) {
					this.storage.modifyEnergyStored(8);
				}
				ticks++;
				if (ticks == ticksforpower) {
					ticks = 0;
				}
			}
		} 
		addEnergy(EnumFacing.VALUES);
	}


	public boolean cranked() {
		Block crank = this.getWorld().getBlockState(pos.offset(EnumFacing.UP)).getBlock();
		if (crank != null && crank == Calculator.crankHandle) {
			return true;
		}
		return false;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			this.cranked = nbt.getBoolean("cranked");
			this.ticks = nbt.getInteger("ticks");
		}
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			nbt.setBoolean("cranked", cranked());
			nbt.setInteger("ticks", ticks);
		}
		return nbt;
	}

	public List<String> getWailaInfo(List<String> tooltip, IBlockState state) {
		tooltip.add(FontHelper.translate("crank.cranked") + ": " + (this.cranked ? FontHelper.translate("locator.true") : FontHelper.translate("locator.false")));
		return tooltip;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerCrankedGenerator(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiCrankedGenerator(player.inventory, this);
	}
}