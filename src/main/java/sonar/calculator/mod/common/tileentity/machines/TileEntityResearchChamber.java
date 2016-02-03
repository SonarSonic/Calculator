package sonar.calculator.mod.common.tileentity.machines;

import gnu.trove.map.hash.THashMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IResearchStore;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.recipes.RecipeRegistry.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator.Dynamic;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.network.utils.ISyncTile;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityResearchChamber extends TileEntityInventory implements ISyncTile {

	public int ticks;
	public int researchSpeed = 100;
	public int lastResearch;
	public Map<Integer, Integer> unblocked = new THashMap<Integer, Integer>();
	public Random rand = new Random();
	public int maxRecipes, storedRecipes;

	public TileEntityResearchChamber() {
		super.slots = new ItemStack[2];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		//beginResearch();
		//this.markDirty();
	}

	public void beginResearch() {
		if (slots[0] == null) {
			ticks = 0;
			this.lastResearch = 0;
			return;
		}

		if ((CalculatorRecipes.instance().validInput(slots[0]) || CalculatorRecipes.instance().validOutput(slots[0])) || slots[0].getItem() == Calculator.circuitBoard
				&& slots[0].getItem() instanceof IStability && ((IStability) slots[0].getItem()).getStability(slots[0])) {
			if (ticks == 0) {
				ticks = 1;
			}

			if (ticks > 0) {
				if (ticks != researchSpeed) {
					ticks++;
				} else {
					if (!this.worldObj.isRemote) {
						addResearch(slots[0]);
						syncResearch();
					}
					ticks = -1;
				}
			}

		}
	}

	public void syncResearch() {
		if (slots[1] != null && slots[1].getItem() instanceof IResearchStore) {
			Map<Integer, Integer> storedResearch = ((IResearchStore) slots[1].getItem()).getResearch(slots[1]);
			Map<Integer, Integer> syncResearch = unblocked;

			for (Entry<Integer, Integer> recipes : storedResearch.entrySet()) {
				if (recipes.getValue() > syncResearch.get(recipes.getKey())) {
					syncResearch.put(recipes.getKey(), recipes.getValue());
				}
			}
			((IResearchStore) slots[1].getItem()).setResearch(slots[1], syncResearch, storedRecipes, maxRecipes);
			this.unblocked = syncResearch;
			this.storedRecipes = unblocked.size();
		//	this.maxRecipes = CalculatorRecipes.instance().getRecipesIDs().size();
		}
		sendResearch();
	}

	public void addResearch(ItemStack stack) {
		CalculatorRecipes.instance().unblockStack(unblocked, stack);
	}

	public void sendResearch() {
		for (int X = -3; X <= 3; X++) {
			for (int Y = -3; Y <= 3; Y++) {
				for (int Z = -3; Z <= 3; Z++) {
					TileEntity target = this.worldObj.getTileEntity(xCoord + X, yCoord + Y, zCoord + Z);
					if (target != null && target instanceof TileEntityCalculator.Dynamic) {
						TileEntityCalculator.Dynamic dynamic = (Dynamic) target;
						//dynamic.setUnblocked(unblocked);
					}
				}
			}
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type != SyncType.SYNC) {
			this.unblocked = CalculatorRecipes.instance().readFromNBT(nbt, "unblocked");
		}
		this.lastResearch = nbt.getInteger("Research");
		this.maxRecipes = nbt.getInteger("Max");
		this.storedRecipes = nbt.getInteger("Stored");

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type != SyncType.SYNC) {
			CalculatorRecipes.instance().writeToNBT(nbt, unblocked, "unblocked");
		}
		nbt.setInteger("Max", maxRecipes);
		nbt.setInteger("Stored", storedRecipes);

	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		super.setInventorySlotContents(i, itemstack);
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 1, 0);
	}

}
