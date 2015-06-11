package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import cofh.api.energy.EnergyStorage;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.IDropTile;

public class TileEntityCalculator extends TileEntityInventory {
	
	public static class Dynamic extends TileEntityCalculator implements IDropTile {
		public int[] unblocked;
		
		public Dynamic(){
		super.slots = new ItemStack[10];
		this.unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];
		}
		public void setUnblocked(int[] unblocked){
			this.unblocked=unblocked;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		public int[] getUnblocked(){
			return unblocked;
		}
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.unblocked = nbt.getIntArray("Unblocked");
			if (this.unblocked == null) {
				this.unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setIntArray("Unblocked", unblocked);
		}

		@Override
		public void readInfo(NBTTagCompound tag) {
			this.unblocked = tag.getIntArray("Unblocked");
			if (this.unblocked == null) {
				this.unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];
			}
			
		}

		@Override
		public void writeInfo(NBTTagCompound tag) {
			tag.setIntArray("Unblocked", unblocked);			
		}
		public void getResearch(){
			for(int X=-3; X<=3; X++){
				for(int Y=-3; Y<=3; Y++){
					for(int Z=-3; Z<=3; Z++){
						TileEntity target = this.worldObj.getTileEntity(xCoord+X, yCoord+Y, zCoord+Z);
						if(target!=null && target instanceof TileEntityResearchChamber){
							TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
							setUnblocked(chamber.unblocked);
						}
					}
				}
			}
		}
	}
	public static class Atomic extends TileEntityCalculator {
		public Atomic(){
		super.slots = new ItemStack[4];
		}
	}
}
