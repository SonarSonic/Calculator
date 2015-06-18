package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.recipes.machines.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import sonar.core.common.tileentity.TileEntityInventorySender;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;

public class TileEntityGenerator extends TileEntityInventorySender implements ISidedInventory {

	public int itemLevel, burnTime;
	public int maxBurnTime;
	public int levelMax = 5000;
	public int requiredLevel = 400;
	public int energyMultiplier;
	public String direction;

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
			item();
			energy();
			addEnergy();
		}
		this.markDirty();
	}

	public void energy() {
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

	private void addEnergy() {

		TileEntity down = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity north = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity south = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity east = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity west = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		
		if (direction == "down") {
			if (SonarHelper.isEnergyHandlerFromSide(down, ForgeDirection.DOWN)) {
				this.storage.extractEnergy(SonarHelper.pushEnergy(down, ForgeDirection.UP, maxTransfer, false), false);
			}
		} else if (direction == "west") {
			if (SonarHelper.isEnergyHandlerFromSide(west, ForgeDirection.WEST)) {
				this.storage.extractEnergy(SonarHelper.pushEnergy(west, ForgeDirection.EAST, this.storage.extractEnergy(maxTransfer, true), false), false);
			}
		} else if (direction == "east") {
			if (SonarHelper.isEnergyHandlerFromSide(east, ForgeDirection.EAST)) {
				this.storage.extractEnergy(SonarHelper.pushEnergy(east, ForgeDirection.WEST, this.storage.extractEnergy(maxTransfer, true), false), false);
			}
		} else if (direction == "north") {
			if (SonarHelper.isEnergyHandlerFromSide(north, ForgeDirection.NORTH)) {
				this.storage.extractEnergy(SonarHelper.pushEnergy(north, ForgeDirection.SOUTH, this.storage.extractEnergy(maxTransfer, true), false), false);
			}
		} else if (direction == "south") {
			if (SonarHelper.isEnergyHandlerFromSide(south, ForgeDirection.SOUTH)) {
				this.storage.extractEnergy(SonarHelper.pushEnergy(south, ForgeDirection.NORTH, this.storage.extractEnergy(maxTransfer, true), false), false);
			}
		}

	}

	private boolean canAddEnergy() {
		if (storage.getEnergyStored() == 0) {
			return false;
		}
		if (direction == "none") {
			return false;
		}
		return true;
	}

	public void updateHandlers() {
		String d = getHandlers();
		if (d == null) {
			direction = "none";
		} else
			direction = d;

	}

	public String getHandlers() {
		TileEntity down = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity up = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		TileEntity north = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity south = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity east = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity west = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);

		if (down != null && SonarHelper.isEnergyHandlerFromSide(down, ForgeDirection.DOWN)) {
			return "down";
		} else if (up != null && SonarHelper.isEnergyHandlerFromSide(up, ForgeDirection.UP)) {
			return "up";
		} else if (west != null && SonarHelper.isEnergyHandlerFromSide(west, ForgeDirection.WEST)) {
			return "west";
		} else if (east != null && SonarHelper.isEnergyHandlerFromSide(east, ForgeDirection.EAST)) {
			return "east";
		} else if (north != null && SonarHelper.isEnergyHandlerFromSide(north, ForgeDirection.NORTH)) {
			return "north";
		} else if (south != null && SonarHelper.isEnergyHandlerFromSide(south, ForgeDirection.SOUTH)) {
			return "south";
		}

		return "none";
	}

	public void item() {

		ItemStack stack = this.slots[1];
		if (!(stack == null)) {
			if (itemValue(stack) > 0) {
				if (!(itemLevel + itemValue(stack) > levelMax)) {
					addItem(itemValue(stack));
					this.slots[1].stackSize--;
					if (this.slots[1].stackSize <= 0) {
						this.slots[1] = null;
					}
				}
			}
		}
	}

	public int itemValue(ItemStack stack) {
		return StarchExtractorRecipes.discharge().value(stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);

		this.direction = nbt.getString("facing");
		this.itemLevel = nbt.getInteger("ItemLevel");
		this.burnTime = nbt.getInteger("Burn");
		this.maxBurnTime = nbt.getInteger("MaxBurn");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);

		if (this.direction == null) {
			nbt.setString("facing", "none");
		} else
			nbt.setString("facing", this.direction);
		nbt.setInteger("ItemLevel", this.itemLevel);
		nbt.setInteger("MaxBurn", this.maxBurnTime);
		nbt.setInteger("Burn", this.burnTime);
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

	@Override
	public void readInfo(NBTTagCompound tag) {
		super.readInfo(tag);
		this.itemLevel = tag.getInteger("ItemLevel");
	}

	@Override
	public void writeInfo(NBTTagCompound tag) {
		super.writeInfo(tag);
		tag.setInteger("ItemLevel", this.itemLevel);
	}

	@Override
	public void onSync(int data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.SPECIAL1:
			this.itemLevel = data;
			break;
		case SyncType.SPECIAL2:
			this.maxBurnTime = data;
			break;
		case SyncType.BURN:
			this.burnTime = data;
			break;

		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, itemLevel);
		case SyncType.SPECIAL2:
			return new SyncData(true, maxBurnTime);
		case SyncType.BURN:
			return new SyncData(true, burnTime);
		}
		return super.getSyncData(id);
	}

	public static class StarchExtractor extends TileEntityGenerator {
		public StarchExtractor() {
			super.energyMultiplier = CalculatorConfig.starchRF;
		}

		public int itemValue(ItemStack stack) {
			return StarchExtractorRecipes.discharge().value(stack);
		}
	}

	public static class RedstoneExtractor extends TileEntityGenerator {
		public RedstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.redstoneRF;
		}

		public int itemValue(ItemStack stack) {
			return RedstoneExtractorRecipes.discharge().value(stack);
		}

	}

	public static class GlowstoneExtractor extends TileEntityGenerator {
		public GlowstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.glowstoneRF;
		}

		public int itemValue(ItemStack stack) {
			return GlowstoneExtractorRecipes.discharge().value(stack);
		}

	}

}
