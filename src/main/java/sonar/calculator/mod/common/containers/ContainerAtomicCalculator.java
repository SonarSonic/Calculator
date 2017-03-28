package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.recipes.RecipeHelperV2;

public class ContainerAtomicCalculator extends Container implements ICalculatorCrafter {

	private boolean isRemote;
	private static final int INV_START = 4, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	private EntityPlayer player;
	private TileEntityCalculator.Atomic atomic;

	public ContainerAtomicCalculator(EntityPlayer player, TileEntityCalculator.Atomic atomic) {
		this.player = player;
		this.atomic = atomic;
		isRemote = player.getEntityWorld().isRemote;

		for (int k = 0; k < 3; k++) {
			addSlotToContainer(new SlotPortableCrafting(this, atomic, k, 20 + k * 32, 35, isRemote, null));
		}
		addSlotToContainer(new SlotPortableResult(player, atomic, this, new int[] { 0, 1, 2 }, 3, 134, 35, isRemote));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

		this.onItemCrafted();
	}

	@Override
	public void onItemCrafted() {
		atomic.setInventorySlotContents(3, RecipeHelperV2.getItemStackFromList(AtomicCalculatorRecipes.instance().getOutputs(player, atomic.getStackInSlot(0), atomic.getStackInSlot(1), atomic.getStackInSlot(2)), 0));
	}

	public void removeEnergy(int remove) {
		if (!this.isRemote) {
			if (player.capabilities.isCreativeMode) {
				return;
			}
			// SonarAPI.getEnergyHelper().extractEnergy(player.getHeldItemMainhand(), 1, ActionType.PERFORM);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return atomic.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotID < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				if (slotID >= INV_START) {
					if (!this.mergeItemStack(itemstack1, 0, INV_START - 1, false)) {
						return null;
					}
				} else if (slotID >= INV_START && slotID < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if (slotID >= HOTBAR_START && slotID < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}
			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return null;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

}
