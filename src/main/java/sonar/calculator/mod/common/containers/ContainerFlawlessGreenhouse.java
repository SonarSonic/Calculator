package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.energy.DischargeValues;
import sonar.core.integration.SonarAPI;
import sonar.core.inventory.ContainerSync;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerFlawlessGreenhouse extends ContainerSync {
	
	private TileEntityFlawlessGreenhouse entity;

	public ContainerFlawlessGreenhouse(InventoryPlayer inventory,
			TileEntityFlawlessGreenhouse entity) {
		super(entity);
		this.entity = entity;
		
		addSlotToContainer(new Slot(entity, 0, 26, 61));
			for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(entity, 1+j, 8 + j * 18, 88));
			}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						8 + j * 18, 110 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 168));
		}
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (!(slotID <= 12)) {
				 if (DischargeValues.getValueOf(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				}else if (itemstack1.getItem() instanceof IEnergyContainerItem) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				}else if (itemstack1.getItem() instanceof IPlantable) {
					if (!mergeItemStack(itemstack1, 1, 10, false)) {
						return null;
					}
				}

			} else if ((slotID >= 10) && (slotID < 37)) {
				if (!mergeItemStack(itemstack1, 36, 46, false)) {
					return null;
				}
			} else if ((slotID >= 37) && (slotID < 46)
					&& (!mergeItemStack(itemstack1, 10, 36, false))) {
				return null;

			} else if (!mergeItemStack(itemstack1, 10, 46, false)) {
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
