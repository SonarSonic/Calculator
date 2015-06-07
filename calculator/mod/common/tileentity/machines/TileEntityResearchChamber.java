package sonar.calculator.mod.common.tileentity.machines;

import java.util.Random;

import cofh.api.energy.EnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;

public class TileEntityResearchChamber extends TileEntityInventory {

	public int ticks;
	public int researchSpeed = 100;
	public int lastResearch;
	public Random rand = new Random();

	public TileEntityResearchChamber() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (slots[0] == null) {
			lastResearch = 0;
			ticks = 0;
		}

			if (slots[0]!=null && (CalculatorRecipes.recipes().getID(slots[0]) != 0 || slots[0]
					.getItem() == Calculator.circuitBoard
					&& slots[0].getItem() instanceof IStability
					&& ((IStability) slots[0].getItem()).getStability(slots[0]))
					&& lastResearch == 0) {
				if (ticks == 0) {
					ticks = 1;
				}

				if (ticks > 0) {
					if (ticks != researchSpeed) {
						ticks++;
					} else {
						addResearch(slots[0]);
					}
				}

			}
		
		this.markDirty();
	}

	public void addResearch(ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() != Calculator.circuitBoard) {
				this.lastResearch = CalculatorRecipes.recipes().getID(stack);
				this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType,2, lastResearch);
			}else{
				stack.stackTagCompound.setInteger("Stable", 0);				
				this.lastResearch = rand.nextInt(CalculatorRecipes.recipes().getIDList().size()-1);
				this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 2, lastResearch);
			}
		}
	}

	public void removeResearch() {
		lastResearch = 0;
		this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 2, 0);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastResearch = nbt.getInteger("Research");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Research", lastResearch);
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if (action == 2) {
			this.lastResearch = param;
		}
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		super.setInventorySlotContents(i, itemstack);
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 1, 0);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
}
