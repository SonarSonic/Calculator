package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.recipes.crafting.RecipeRegistry;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;
import sonar.core.common.item.InventoryItem;

public class ContainerCalculator extends Container {
	private final InventoryItem inventory;

	private static final int INV_START = CalculatorItem.CalculatorInventory.size, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public InventoryStoredCrafting craftMatrix;
	public InventoryStoredResult craftResult;
	public int[] research;
	public World worldObj;
	
	public ContainerCalculator(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryItem inventoryItem, int[] research) {
		this.worldObj = player.getEntityWorld();
		this.inventory = inventoryItem;
		craftMatrix = new InventoryStoredCrafting(this, 2, 1, this.inventory);
		craftResult = new InventoryStoredResult(this.inventory);
		this.research = research;

		addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 134, 35));

		for (int k = 0; k < 2; k++) {
			addSlotToContainer(new Slot(this.craftMatrix, k, 25 + (k * 54), 35));
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {	
		this.craftResult.setInventorySlotContents(0, RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(craftMatrix.getStackInSlot(0),craftMatrix.getStackInSlot(1)));
				
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
					if (!this.mergeItemStack(itemstack1, 1, INV_START, false)) {
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

	public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
		return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
	}
}
