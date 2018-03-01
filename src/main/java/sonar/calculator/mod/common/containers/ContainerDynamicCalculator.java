package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.recipes.CalculatorRecipes;
import sonar.calculator.mod.common.recipes.ScientificRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.utils.SonarCompat;

public class ContainerDynamicCalculator extends Container implements ICalculatorCrafter {

	private static final int INV_START = 10, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	private EntityPlayer player;
	private TileEntityCalculator.Dynamic dynamic;

	public ContainerDynamicCalculator(EntityPlayer player, TileEntityCalculator.Dynamic dynamic) {
		this.player = player;
		this.dynamic = dynamic;

		this.addSlotToContainer(new SlotPortableCrafting(this, dynamic, 0, 25, 9, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, dynamic, 1, 79, 9, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, dynamic, this, new int[] { 0, 1 }, 2, 134, 9));

		this.addSlotToContainer(new SlotPortableCrafting(this, dynamic, 3, 25, 35, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableCrafting(this, dynamic, 4, 79, 35, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, dynamic, this, new int[] { 3, 4 }, 5, 134, 35));

		addSlotToContainer(new SlotPortableCrafting(this, dynamic, 6, 20, 61, Calculator.itemFlawlessCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, dynamic, 7, 20 + 32, 61, Calculator.itemFlawlessCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, dynamic, 8, 20 + 2 * 32, 61, Calculator.itemFlawlessCalculator));
		this.addSlotToContainer(new SlotPortableResult(player, dynamic, this, new int[] { 6, 7, 8 }, 9, 134, 61));

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
		dynamic.setInventorySlotContents(2, RecipeHelperV2.getItemStackFromList(CalculatorRecipes.instance().getOutputs(player, dynamic.getStackInSlot(0), dynamic.getStackInSlot(1)), 0));
		dynamic.setInventorySlotContents(5, RecipeHelperV2.getItemStackFromList(ScientificRecipes.instance().getOutputs(player, dynamic.getStackInSlot(3), dynamic.getStackInSlot(4)), 0));
		dynamic.setInventorySlotContents(9, RecipeHelperV2.getItemStackFromList(AtomicCalculatorRecipes.instance().getOutputs(player, dynamic.getStackInSlot(6), dynamic.getStackInSlot(7), dynamic.getStackInSlot(8)), 0));
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!this.player.getEntityWorld().isRemote) {
			dynamic.setInventorySlotContents(2, SonarCompat.getEmpty());
			dynamic.setInventorySlotContents(5, SonarCompat.getEmpty());
			dynamic.setInventorySlotContents(9, SonarCompat.getEmpty());
		}
	}

	@Override
	public void removeEnergy(int remove) {
		if (!player.getEntityWorld().isRemote) {
			if (player.capabilities.isCreativeMode) {
				return;
			}
			SonarAPI.getEnergyHelper().extractEnergy(player.getHeldItemMainhand(), remove, ActionType.PERFORM);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return dynamic.isUsableByPlayer(player);
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
				if (par2 >= INV_START) {
					if (current == 0 || current == 1) {
						if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
							return SonarCompat.getEmpty();
						}
					} else if (current == 2) {
						if (!this.mergeItemStack(itemstack1, 3, 5, false)) {
							return SonarCompat.getEmpty();
						}
					} else if (current == 3) {
						if (!this.mergeItemStack(itemstack1, 6, 9, false)) {
							return SonarCompat.getEmpty();
						}
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
}
