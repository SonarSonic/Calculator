package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.utils.DischargeValues;
import sonar.core.utils.SlotBlockedInventory;
import sonar.core.utils.SonarAPI;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerAnalysingChamber extends ContainerSync {
	
	private TileEntityAnalysingChamber entity;
	
	public ContainerAnalysingChamber(InventoryPlayer inventory,
			TileEntityAnalysingChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 16, 6));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(entity, 2, 35, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 3, 53, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 4, 71, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 5, 89, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 6, 107, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 7, 125, 34));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int num) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(num);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			System.out.print(num >= 2 && num<8);
			if (num >= 2 && num<8) {
				if (!mergeItemStack(itemstack1, 8, 43, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			 else if ((num != 1) && (num != 0)) {
				if (itemstack.getItem() == Calculator.circuitBoard) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				}
				else if ((itemstack.getItem() instanceof IEnergyContainerItem)) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}
			} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof ISpecialElectricItem) {
				if (!mergeItemStack(itemstack1, 1, 2, false)) {
					return null;
				}
			} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
				if (!mergeItemStack(itemstack1, 1, 2, false)) {
					return null;
				} else if ((num >= 8) && (num < 34)) {
					if (!mergeItemStack(itemstack1, 34, 43, false)) {
						return null;
					}
				} else if ((num >= 34) && (num < 43)
						&& (!mergeItemStack(itemstack1, 8, 34, false))) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 8, 43, false)) {
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

			slot.onPickupFromSlot(p_82846_1_, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
