package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.generators.GuiCrankedGenerator;
import sonar.calculator.mod.common.containers.ContainerCrankedGenerator;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;

import java.util.List;

public class TileEntityCrankedGenerator extends TileEntityEnergy implements IFlexibleGui {

	public boolean cranked;
	public int ticks;
	public int ticksforpower = 2;

	public TileEntityCrankedGenerator() {
		super.storage.setCapacity(CalculatorConfig.HAND_CRANK_STORAGE).setMaxTransfer(CalculatorConfig.HAND_CRANK_TRANSFER_RATE);
		super.energyMode=EnergyMode.SEND;
	}

	@Override
	public void update() {
		super.update();
		if (cranked()) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) this.world.getTileEntity(pos.offset(EnumFacing.UP));
			if (crank.angle > 0) {
				if (ticks == 0) {
					this.storage.modifyEnergyStored(CalculatorConfig.HAND_CRANK_PER_TICK);
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
		Block crank = this.world.getBlockState(pos.offset(EnumFacing.UP)).getBlock();
        return crank == Calculator.crankHandle;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			this.cranked = nbt.getBoolean("cranked");
			this.ticks = nbt.getInteger("ticks");
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			nbt.setBoolean("cranked", cranked());
			nbt.setInteger("ticks", ticks);
		}
		return nbt;
	}

    @Override
	public List<String> getWailaInfo(List<String> tooltip, IBlockState state) {
		tooltip.add(FontHelper.translate("crank.cranked") + ": " + (this.cranked ? FontHelper.translate("locator.true") : FontHelper.translate("locator.false")));
		return tooltip;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerCrankedGenerator(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiCrankedGenerator(player.inventory, this);
	}
}