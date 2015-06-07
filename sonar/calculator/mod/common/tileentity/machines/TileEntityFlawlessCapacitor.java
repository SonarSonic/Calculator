package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import sonar.core.common.tileentity.TileEntitySender;
import sonar.core.utils.helpers.SonarHelper;

/**WIP*/
public class TileEntityFlawlessCapacitor extends TileEntitySender {

	public int[] output = new int[6];
	
	public TileEntityFlawlessCapacitor(){
		super.storage = new EnergyStorage(2000000000);
	}
	public void updateEntity(){
		handleEnergy();
	}
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setIntArray("outputs", output);
	}
	
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		this.output = tag.getIntArray("outputs");
		if(output==null){
			output=new int[6];
		}
	}
	public void handleEnergy() {		
		for(int i =0; i<output.length;i++){
			if(output[i]==1){
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				TileEntity tile = SonarHelper.getAdjacentTileEntity(this, dir);
				if(SonarHelper.isEnergyHandlerFromSide(tile, dir)){
					int removed = SonarHelper.pushEnergy(tile, dir, SonarHelper.pushEnergy(tile, dir, this.storage.getEnergyStored(), true), false);
					this.storage.extractEnergy(removed, false);
				}
			}
		}
		
	}
	public void incrementSide(int i){
		if(!this.worldObj.isRemote){
		if(i<output.length){
			if(output[i]==0){
				output[i]=1;
			}else{
				output[i]=0;
			}
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,boolean simulate) {
		if(output.length == 0 || output==null){
			output=new int[6];
		}
		if(output[from.ordinal()]==1){
			return 0;
		}
		return this.storage.receiveEnergy(maxReceive, simulate);
	}
	
	public int[] getOutputSides(){
		return output;
	}
	
	@Override
	public void readInfo(NBTTagCompound tag) {
		this.output = tag.getIntArray("output");		
		this.storage.setEnergyStored(tag.getInteger("energy"));
		if(output.length == 0 || output==null){
			output=new int[6];
		}
	}

	@Override
	public void writeInfo(NBTTagCompound tag) {
		tag.setIntArray("output", output);
		tag.setInteger("energy", this.storage.getEnergyStored());
	}
}
