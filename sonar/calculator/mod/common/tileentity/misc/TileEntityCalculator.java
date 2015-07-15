package sonar.calculator.mod.common.tileentity.misc;

import gnu.trove.map.hash.THashMap;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.recipes.crafting.RecipeRegistry;
import sonar.calculator.mod.common.recipes.crafting.RecipeRegistry.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityCalculator extends TileEntityInventory {

	public static class Dynamic extends TileEntityCalculator {
		public Map<Integer, Integer> unblocked = new THashMap<Integer, Integer>();

		public Dynamic() {
			super.slots = new ItemStack[10];
		}

		public void setUnblocked(Map<Integer, Integer> unblocked) {
			this.unblocked = unblocked;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		public Map<Integer, Integer> getUnblocked() {
			return unblocked;
		}

		public void readData(NBTTagCompound nbt, SyncType type) {
			super.readData(nbt, type);
			this.unblocked = CalculatorRecipes.instance().readFromNBT(nbt, "unblocked");

		}

		public void writeData(NBTTagCompound nbt, SyncType type) {
			super.writeData(nbt, type);
			CalculatorRecipes.instance().writeToNBT(nbt, unblocked, "unblocked");

		}

		public void getResearch() {
			for (int X = -3; X <= 3; X++) {
				for (int Y = -3; Y <= 3; Y++) {
					for (int Z = -3; Z <= 3; Z++) {
						TileEntity target = this.worldObj.getTileEntity(xCoord + X, yCoord + Y, zCoord + Z);
						if (target != null && target instanceof TileEntityResearchChamber) {
							TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
							setUnblocked(chamber.unblocked);
						}
					}
				}
			}
		}
	}

	public static class Atomic extends TileEntityCalculator {
		public Atomic() {
			super.slots = new ItemStack[4];
		}
	}
}
