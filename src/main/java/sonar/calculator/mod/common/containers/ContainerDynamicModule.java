package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.common.item.InventoryItem;

public class ContainerDynamicModule extends Container implements ICalculatorCrafter {
	private final InventoryItem inventory;
	private boolean isRemote;

	private static final int INV_START = 10, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerDynamicModule(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		isRemote = player.getEntityWorld().isRemote;

		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 0, 25, 9, isRemote, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 1, 79, 9, isRemote, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1 }, 2, 134, 9, isRemote));

		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 3, 25, 35, isRemote, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 4, 79, 35, isRemote, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 3, 4 }, 5, 134, 35, isRemote));

		addSlotToContainer(new SlotPortableCrafting(this, inventory, 6, 20 + 0 * 32, 61, isRemote, Calculator.itemFlawlessCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 7, 20 + 1 * 32, 61, isRemote, Calculator.itemFlawlessCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 8, 20 + 2 * 32, 61, isRemote, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 6, 7, 8 }, 9, 134, 61, isRemote));

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
		inventory.setInventorySlotContents(2, RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1)), isRemote);
		inventory.setInventorySlotContents(5, RecipeRegistry.ScientificRecipes.instance().getCraftingResult(inventory.getStackInSlot(3), inventory.getStackInSlot(4)), isRemote);
		inventory.setInventorySlotContents(9, RecipeRegistry.AtomicRecipes.instance().getCraftingResult(inventory.getStackInSlot(6), inventory.getStackInSlot(7), inventory.getStackInSlot(8)), isRemote);
	}

	public void removeEnergy() {

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {
				int current = this.getCurrentUsage();
				if (par2 >= INV_START && (current == 0 || current == 1)) {
					if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
						return null;
					}
				} else if (par2 >= INV_START && current == 2) {
					if (!this.mergeItemStack(itemstack1, 3, 5, false)) {
						return null;
					}
				} else if (par2 >= INV_START && current == 3) {
					if (!this.mergeItemStack(itemstack1, 6, 9, false)) {
						return null;
					}
				}

				else if (par2 >= INV_START && par2 < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END, false)) {
						return null;
					}
				} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END, false)) {
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

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	public int getCurrentUsage() {
		if (((Slot) this.inventorySlots.get(0)).getHasStack() || ((Slot) this.inventorySlots.get(1)).getHasStack()) {
			return 1;
		} else if (((Slot) this.inventorySlots.get(3)).getHasStack() || ((Slot) this.inventorySlots.get(4)).getHasStack()) {
			return 2;
		} else if (((Slot) this.inventorySlots.get(6)).getHasStack() || ((Slot) this.inventorySlots.get(7)).getHasStack() || ((Slot) this.inventorySlots.get(8)).getHasStack()) {
			return 3;
		}
		return 0;
	}

	@Override
    public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player){
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return null;
		}
		return super.slotClick(slot, drag, click, player);
	}
}
