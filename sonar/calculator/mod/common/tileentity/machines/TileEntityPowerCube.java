package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import cofh.api.energy.EnergyStorage;

public class TileEntityPowerCube extends TileEntityInventoryReceiver {

	public TileEntityPowerCube() {
		super.storage = new EnergyStorage(CalculatorConfig.cubeEnergy);
		super.slots = new ItemStack[2];
		super.maxTransfer = 1;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote){
		charge(0);
		discharge(1);
		}
		this.markDirty();
	}

}
