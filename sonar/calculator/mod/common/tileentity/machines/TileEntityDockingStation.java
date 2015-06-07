package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import cofh.api.energy.EnergyStorage;

public class TileEntityDockingStation extends TileEntityInventoryReceiver {
	
	public int type;		
	
	public TileEntityDockingStation() {
		super.storage = new EnergyStorage(CalculatorConfig.cubeEnergy,
				CalculatorConfig.cubeEnergy);
		super.slots = new ItemStack[2];
	}
	
	/**type 0= No Calculator, 1=Basic, 2=Scientific, 3=Atomic, 4=Flawless*/
	public void updateEntity(){
		super.updateEntity();	
		int lastType =type;
		int currentType = isCalculator(slots[0]);
		if(currentType>0){
			this.type=currentType;
		}
		else{
			this.type=0;
		}
		if(lastType!=this.type){
			this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
		this.markDirty();
	}
	public void setType(){
		
	}
	public static int isCalculator(ItemStack itemstack1) {
		if(itemstack1!=null){
		  if(itemstack1.getItem() == Calculator.itemCalculator){
			  return 1;
		  }
		  if(itemstack1.getItem() == Calculator.itemScientificCalculator){
			  return 2;
		  }
		  if(itemstack1.getItem() == Item.getItemFromBlock(Calculator.atomiccalculatorBlock)){
			  return 3;
		  }
		  if(itemstack1.getItem() == Calculator.itemFlawlessCalculator){
			  return 4;
		  }
		}
		return 0;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		this.type = nbt.getInteger("Type");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		nbt.setInteger("Type", this.type);

	}
	@Override
	public void onSync(int data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.SPECIAL1:
			this.type = data;
			break;
			
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, type);
		}
		return super.getSyncData(id);
	}
}
