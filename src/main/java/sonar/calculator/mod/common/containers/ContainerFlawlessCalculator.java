package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.recipes.RecipeRegistry.FlawlessRecipes;
import sonar.calculator.mod.common.recipes.RecipeRegistry.ScientificRecipes;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.common.item.InventoryItem;
import sonar.core.recipes.RecipeHelperV2;

public class ContainerFlawlessCalculator extends Container implements ICalculatorCrafter {
	public final InventoryItem inventory;
	public final EntityPlayer player;
	public boolean isRemote;

	private static final int INV_START = 5, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerFlawlessCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		this.player = player;
		isRemote = player.getEntityWorld().isRemote;

		for (int k = 0; k < 4; k++) {
			addSlotToContainer(new SlotPortableCrafting(this, inventory, k, 17 + k * 32, 35, isRemote, Calculator.itemFlawlessCalculator));
		}

		addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1, 2, 3 }, 4, 145, 35, isRemote));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
		onItemCrafted();
	}

	@Override
	public void onItemCrafted() {
		inventory.setInventorySlotContents(3, RecipeHelperV2.getItemStackFromList(FlawlessRecipes.instance().getOutputs(player, inventory.getStackInSlot(0), inventory.getStackInSlot(1), inventory.getStackInSlot(2), inventory.getStackInSlot(3)), 0));
	}

	public void removeEnergy(int remove) {

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {

				if (par2 >= INV_START) {
					if (!this.mergeItemStack(itemstack1, 0, INV_START - 1, false)) {
						return null;
					}
				} else if (par2 >= INV_START && par2 < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return null;
		}
		return super.slotClick(slot, drag, click, player);
	}
}
