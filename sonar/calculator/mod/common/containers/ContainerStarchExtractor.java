package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;

public class ContainerStarchExtractor extends ContainerSync {
	
	private TileEntityGenerator.StarchExtractor entity;

	public ContainerStarchExtractor(InventoryPlayer inventory,
			TileEntityGenerator.StarchExtractor entity) {
		 super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 8, 38));
		addSlotToContainer(new Slot(entity, 1, 8, 14));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(p_82846_2_);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if ((p_82846_2_ != 1) && (p_82846_2_ != 0)) {
				if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (StarchExtractorRecipes.discharge().value(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if ((p_82846_2_ >= 3) && (p_82846_2_ < 30)) {
					if (!mergeItemStack(itemstack1, 29, 38, false)) {
						return null;
					}
				} else if ((p_82846_2_ >= 29) && (p_82846_2_ < 38)
						&& (!mergeItemStack(itemstack1, 2, 29, false))) {
					return null;
				}
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

	public int dischargeValue(Item item) {
		if (item == Items.redstone)
			return 1000;
		if (item == Items.coal)
			return 1000;
		if (item == Calculator.enriched_coal)
			return 3000;
		if (item == Calculator.firecoal)
			return 10000;
		if (item == Calculator.purified_coal)
			return 10000;
		return 0;
	}
}
