package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.core.energy.DischargeValues;
import sonar.core.integration.SonarAPI;
import sonar.core.inventory.ContainerSync;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerCO2Generator extends ContainerSync {
	
	private TileEntityCO2Generator entity;

	public ContainerCO2Generator(InventoryPlayer inventory,
			TileEntityCO2Generator entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 80, 22));
		addSlotToContainer(new Slot(entity, 1, 28, 60));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if ((id != 0) && (id != 1)) {
				if (TileEntityFurnace.getItemBurnTime(itemstack1) > 0) {
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
			}
			else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof ISpecialElectricItem) {
				if (!mergeItemStack(itemstack1, 1, 2, false)) {
					return null;
				}
				} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
			}
			} else if ((id >= 2) && (id < 29)) {
				if (!mergeItemStack(itemstack1, 29, 38, false)) {
					return null;
				}
			} else if ((id >= 29) && (id < 38)
					&& (!mergeItemStack(itemstack1, 2, 29, false))) {
				return null;

			} else if (!mergeItemStack(itemstack1, 2, 38, false)) {
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
