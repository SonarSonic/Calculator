package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.utils.SlotResearch;
import sonar.calculator.mod.utils.SlotSync;
import sonar.core.inventory.ContainerSync;

public class ContainerResearchChamber extends ContainerSync {
	private TileEntityResearchChamber entity;

	public ContainerResearchChamber(EntityPlayer player, TileEntityResearchChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new SlotResearch(entity, 0, 55, 34, player));
		addSlotToContainer(new SlotSync(entity, 1, 104, 35, player));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if ((id != 1) && (id != 0)) {
				if(itemstack1.getItem() instanceof IResearchStore){
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}
				else if (RecipeRegistry.CalculatorRecipes.instance().validInput(itemstack1) && this.inventoryItemStacks.get(0) == null) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				}
			} else if ((id >= 2) && (id < 29)) {
				if (!mergeItemStack(itemstack1, 29, 38, false)) {
					return null;
				}
			} else if ((id >= 29) && (id < 38) && (!mergeItemStack(itemstack1, 2, 29, false))) {
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

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}

}
