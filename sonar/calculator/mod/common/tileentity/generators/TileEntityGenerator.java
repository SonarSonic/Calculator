package sonar.calculator.mod.common.tileentity.generators;

import ic2.api.energy.tile.IEnergySink;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.recipes.machines.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.core.common.tileentity.TileEntityInventorySender;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGenerator extends TileEntityInventorySender implements ISidedInventory {

	protected TileEntity[] handlers = new TileEntity[6];
	public int itemLevel, burnTime;
	public int maxBurnTime;
	public int levelMax = 5000;
	public int requiredLevel = 400;
	public int energyMultiplier;

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	public TileEntityGenerator() {
		super.storage = new EnergyStorage(1000000, 1000000);
		super.slots = new ItemStack[2];
		super.maxTransfer = 2000;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.worldObj.isRemote) {
			processItemLevel();
			generateEnergy();
			int maxTransfer = Math.min(this.maxTransfer, this.storage.getEnergyStored());
			this.storage.extractEnergy(maxTransfer - this.pushEnergy(maxTransfer, false), false);
		}
		this.markDirty();
	}

	public void generateEnergy() {
		ItemStack stack = this.getStackInSlot(0);
		if (!(stack == null)) {
			if (burnTime == 0 && TileEntityFurnace.isItemFuel(stack)) {
				if (!(this.storage.getEnergyStored() == this.storage.getMaxEnergyStored()) && this.itemLevel >= requiredLevel) {
					this.maxBurnTime = TileEntityFurnace.getItemBurnTime(stack);
					burnTime++;
					this.slots[0].stackSize--;

					if (this.slots[0].stackSize <= 0) {
						this.slots[0] = null;
					}
				}
			}

		}
		if (burnTime > 0 && !(burnTime == maxBurnTime)) {
			this.storage.receiveEnergy(energyMultiplier, false);
			burnTime++;
		}
		if (maxBurnTime != 0 && burnTime == maxBurnTime) {
			this.storage.receiveEnergy(energyMultiplier, false);
			burnTime = 0;
			this.removeItem(requiredLevel);
		}

	}

	public void processItemLevel() {
		ItemStack stack = this.slots[1];
		if (stack == null || !(itemValue(stack) > 0)) {
			return;
		}
		if (!(itemLevel + itemValue(stack) > levelMax)) {
			addItem(itemValue(stack));
			this.slots[1].stackSize--;
			if (this.slots[1].stackSize <= 0) {
				this.slots[1] = null;
			}
		}

	}

	public int pushEnergy(int recieve, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyReceiver) {
					recieve -= ((IEnergyReceiver) this.handlers[i]).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve, simulate);
				} else if (handlers[i] instanceof IEnergySink) {
					if (simulate) {
						recieve -= ((IEnergySink) this.handlers[i]).getDemandedEnergy() * 4;
					} else {
						recieve -= (recieve - (((IEnergySink) this.handlers[i]).injectEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve / 4, 128) * 4));
					}
				}
			}
		}
		return recieve;
	}

	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; i++) {
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, ForgeDirection.getOrientation(i));
			if (!(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)])) {
					this.handlers[i] = te;
				} else
					this.handlers[i] = null;
			}
		}
	}

	public void onLoaded() {
		super.onLoaded();
		this.updateAdjacentHandlers();
	}

	public int itemValue(ItemStack stack) {
		return StarchExtractorRecipes.discharge().value(stack);
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		this.itemLevel = nbt.getInteger("ItemLevel");
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.burnTime = nbt.getInteger("Burn");
			this.maxBurnTime = nbt.getInteger("MaxBurn");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		nbt.setInteger("ItemLevel", itemLevel);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("Burn", this.burnTime);
			nbt.setInteger("MaxBurn", this.maxBurnTime);
		}
	}

	public void addItem(int add) {
		itemLevel = itemLevel + add;
	}

	public void removeItem(int remove) {
		itemLevel = itemLevel - remove;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (TileEntityFurnace.isItemFuel(stack)) {
				return true;
			}
		}
		if (slot == 1) {
			if (itemValue(stack) > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		return slot == 0 ? slotsSides : (slot == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int slots) {

		return slots != 0 || slot != 1 || stack != null && stack.getItem() == Items.bucket;
	}

	public static class StarchExtractor extends TileEntityGenerator {
		public StarchExtractor() {
			super.energyMultiplier = CalculatorConfig.starchRF;
		}

		public int itemValue(ItemStack stack) {
			return StarchExtractorRecipes.discharge().value(stack);
		}

		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip) {
			currenttip.add(FontHelper.translate("generator.starch") + ": " + this.itemLevel + "%");
			return currenttip;
		}
	}

	public static class RedstoneExtractor extends TileEntityGenerator {
		public RedstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.redstoneRF;
		}

		public int itemValue(ItemStack stack) {
			return RedstoneExtractorRecipes.discharge().value(stack);
		}

		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip) {
			currenttip.add(FontHelper.translate("generator.redstone") + ": " + this.itemLevel + "%");
			return currenttip;
		}
	}

	public static class GlowstoneExtractor extends TileEntityGenerator {
		public GlowstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.glowstoneRF;
		}

		public int itemValue(ItemStack stack) {
			return GlowstoneExtractorRecipes.discharge().value(stack);
		}

		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip) {
			currenttip.add(FontHelper.translate("generator.glowstone") + ": " + this.itemLevel + "%");
			return currenttip;
		}
	}

}
