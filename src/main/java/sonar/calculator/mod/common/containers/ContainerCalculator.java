package sonar.calculator.mod.common.containers;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.common.item.InventoryItem;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerCalculator extends Container implements ICalculatorCrafter {
	private final InventoryItem inventory;
	private boolean isRemote;

	private static final int INV_START = CalculatorItem.CalculatorInventory.size, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public Map<Integer, Integer> research;
	private EntityPlayer player;

	public ContainerCalculator(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryItem inventoryItem, Map<Integer, Integer> research) {
		this.player = player;
		this.inventory = inventoryItem;
		this.research = research;
		isRemote = player.getEntityWorld().isRemote;
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 0, 25, 35, isRemote));
		this.addSlotToContainer(new SlotPortableCrafting(this, inventory, 1, 79, 35, isRemote));
		this.addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1 }, 2, 134, 35, isRemote));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

		this.onItemCrafted();
	}

	@Override
	public void onItemCrafted() {
		inventory.setInventorySlotContents(2, RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1)), isRemote);

	}

	public void removeEnergy() {
		if (!this.isRemote) {
			if (player.capabilities.isCreativeMode) {
				return;
			}
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem energy = (IEnergyContainerItem) player.getHeldItem().getItem();
				energy.extractEnergy(player.getHeldItem(), 1, false);
				int stored = energy.getEnergyStored(player.getHeldItem()) - 1;
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
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
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
			return null;
		}
		return super.slotClick(slot, button, flag, player);
	}
}
