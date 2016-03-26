package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.modules.WIPSmeltingModule;
import sonar.core.common.item.InventoryItem;
import sonar.core.energy.DischargeValues;
import sonar.core.integration.SonarLoader;
import sonar.core.inventory.ContainerCraftInventory;
import sonar.core.inventory.slots.SlotBlockedInventory;
import sonar.core.inventory.slots.SlotLimiter;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSmeltingModule extends ContainerCraftInventory {
	private WIPSmeltingModule module;
	private ItemStack item;
	public int lastCookTime;
	public int currentEnergy;

	public ContainerSmeltingModule(EntityPlayer player, InventoryPlayer inv, InventoryItem calc, ItemStack item) {
		super(player, inv, calc);
		this.module = (WIPSmeltingModule) item.getItem();
		this.item = item;
		addSlotToContainer(new Slot(calc, 0, 53, 24));
		addSlotToContainer(new Slot(calc, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(calc, 2, 107, 24));
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new SlotLimiter(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18, Calculator.itemStorageModule));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new SlotLimiter(inv, i, 8 + i * 18, 142, Calculator.itemStorageModule));
		}
	}

	public void addCraftingToCrafters(ICrafting icrafting) {
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.module.cookTime(item));
		icrafting.sendProgressBarUpdate(this, 200, this.module.getEnergyStored(item));
		icrafting.sendProgressBarUpdate(this, 201, this.module.getEnergyStored(item) >> 16);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.module.cookTime(item)) {
				icrafting.sendProgressBarUpdate(this, 0, this.module.cookTime(item));
			}
			icrafting.sendProgressBarUpdate(this, 200, this.module.getEnergyStored(item));
			icrafting.sendProgressBarUpdate(this, 201, this.module.getEnergyStored(item) >> 16);
		}

		this.lastCookTime = this.module.cookTime(item);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int value) {
		/*
		if (slot == 0) {
			this.module.syncCook = value;
		}
		if (slot == 200)
			this.currentEnergy = DischargeValues.upcastShort(value);

		if (slot == 201)
			this.module.syncEnergy = this.currentEnergy | value << 16;
			*/
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 2) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if ((slotID != 1) && (slotID != 0)) {
				if (module.recipe(itemstack1) != null) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (DischargeValues.getValueOf(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (itemstack1.getItem() instanceof IEnergyContainerItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (SonarLoader.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (SonarLoader.ic2Loaded() && itemstack1.getItem() instanceof ISpecialElectricItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if ((slotID >= 3) && (slotID < 30)) {
					if (!mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if ((slotID >= 30) && (slotID < 39) && (!mergeItemStack(itemstack1, 3, 30, false))) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}