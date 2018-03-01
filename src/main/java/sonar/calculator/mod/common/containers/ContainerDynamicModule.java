package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.recipes.CalculatorRecipes;
import sonar.calculator.mod.common.recipes.ScientificRecipes;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.common.item.InventoryItem;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.utils.SonarCompat;

public class ContainerDynamicModule extends Container implements ICalculatorCrafter {
	private final InventoryItem inventory;
	public EntityPlayer player;

	private static final int INV_START = 10, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerDynamicModule(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		this.player = player;

		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 0, 25, 9, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 1, 79, 9, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1 }, 2, 134, 9));

		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 3, 25, 35, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 4, 79, 35, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 3, 4 }, 5, 134, 35));

        addSlotToContainer(new SlotPortableCrafting(this, inventory, 6, 20, 61, Calculator.itemFlawlessCalculator));
        addSlotToContainer(new SlotPortableCrafting(this, inventory, 7, 20 + 32, 61, Calculator.itemFlawlessCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 8, 20 + 2 * 32, 61, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 6, 7, 8 }, 9, 134, 61));

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
        inventory.setInventorySlotContents(2, RecipeHelperV2.getItemStackFromList(CalculatorRecipes.instance().getOutputs(player, inventory.getStackInSlot(0), inventory.getStackInSlot(1)), 0), false);
        inventory.setInventorySlotContents(5, RecipeHelperV2.getItemStackFromList(ScientificRecipes.instance().getOutputs(player, inventory.getStackInSlot(3), inventory.getStackInSlot(4)), 0), false);
        inventory.setInventorySlotContents(9, RecipeHelperV2.getItemStackFromList(AtomicCalculatorRecipes.instance().getOutputs(player, inventory.getStackInSlot(6), inventory.getStackInSlot(7), inventory.getStackInSlot(8)), 0), false);
	}

    @Override
	public void removeEnergy(int remove) {}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUsableByPlayer(player);
	}

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack itemstack = SonarCompat.getEmpty();
        Slot slot = this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END, true)) {
					return SonarCompat.getEmpty();
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				int current = this.getCurrentUsage();
				if (par2 >= INV_START && (current == 0 || current == 1)) {
					if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
						return SonarCompat.getEmpty();
					}
				} else if (par2 >= INV_START && current == 2) {
					if (!this.mergeItemStack(itemstack1, 3, 5, false)) {
						return SonarCompat.getEmpty();
					}
				} else if (par2 >= INV_START && current == 3) {
					if (!this.mergeItemStack(itemstack1, 6, 9, false)) {
						return SonarCompat.getEmpty();
					}
                } else if (par2 >= INV_START && par2 < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END, false)) {
						return SonarCompat.getEmpty();
					}
				} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END, false)) {
						return SonarCompat.getEmpty();
					}
				}
			}

			if (SonarCompat.getCount(itemstack1) == 0) {
				slot.putStack(SonarCompat.getEmpty());
			} else {
				slot.onSlotChanged();
			}

			if (SonarCompat.getCount(itemstack1) == SonarCompat.getCount(itemstack)) {
				return SonarCompat.getEmpty();
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	public int getCurrentUsage() {
        if (this.inventorySlots.get(0).getHasStack() || this.inventorySlots.get(1).getHasStack()) {
			return 1;
        } else if (this.inventorySlots.get(3).getHasStack() || this.inventorySlots.get(4).getHasStack()) {
			return 2;
        } else if (this.inventorySlots.get(6).getHasStack() || this.inventorySlots.get(7).getHasStack() || this.inventorySlots.get(8).getHasStack()) {
			return 3;
		}
		return 0;
	}

	@Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return SonarCompat.getEmpty();
		}
		return super.slotClick(slot, drag, click, player);
	}
}
