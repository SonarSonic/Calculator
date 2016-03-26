package sonar.calculator.mod.common.containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerBasicGreenhouse extends ContainerSync {
	private TileEntityBasicGreenhouse entity;

	public ContainerBasicGreenhouse(InventoryPlayer inventory, TileEntityBasicGreenhouse tileentity) {
		super(tileentity);
		this.entity = tileentity;

		addSlotToContainer(new Slot(tileentity, 0, 19, 28));
		addSlotToContainer(new Slot(tileentity, 1, 37, 28));
		addSlotToContainer(new Slot(tileentity, 2, 19, 46));
		addSlotToContainer(new Slot(tileentity, 3, 37, 46));

		addSlotToContainer(new Slot(tileentity, 4, 80, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(tileentity, 5 + j, 8 + j * 18, 88));
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 168));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (!(slotID <= 13)) {
				if ((checkLog(Block.getBlockFromItem(itemstack1.getItem())))) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if ((checkStairs(Block.getBlockFromItem(itemstack1.getItem())))) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if ((checkGlass(Block.getBlockFromItem(itemstack1.getItem())))) {
					if (!mergeItemStack(itemstack1, 2, 3, false)) {
						return null;
					}
				} else if ((checkPlanks(Block.getBlockFromItem(itemstack1.getItem())))) {
					if (!mergeItemStack(itemstack1, 3, 4, false)) {
						return null;
					}
				} else if (DischargeValues.getValueOf(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 4, 5, false)) {
						return null;
					}
				} else if (itemstack1.getItem() instanceof IEnergyContainerItem) {
					if (!mergeItemStack(itemstack1, 4, 5, false)) {
						return null;
					}
				} else if (itemstack1.getItem() instanceof IPlantable) {
					if (!mergeItemStack(itemstack1, 5, 14, false)) {
						return null;
					}
				}
			} else if ((slotID >= 13) && (slotID < 41)) {
				if (!mergeItemStack(itemstack1, 40, 50, false)) {
					return null;
				}
			} else if ((slotID >= 40) && (slotID < 50) && (!mergeItemStack(itemstack1, 14, 40, false))) {
				return null;

			} else if (!mergeItemStack(itemstack1, 14, 50, false)) {
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

	public boolean checkLog(Block block) {

		for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkGlass(Block block) {

		for (int i = 0; i < OreDictionary.getOres("blockGlass").size(); i++) {
			if (OreDictionary.getOres("blockGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("blockGlassColorless").size(); i++) {
			if (OreDictionary.getOres("blockGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlassColorless").size(); i++) {
			if (OreDictionary.getOres("paneGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlass").size(); i++) {
			if (OreDictionary.getOres("paneGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkStairs(Block block) {

		for (int i = 0; i < OreDictionary.getOres("stairWood").size(); i++) {
			if (OreDictionary.getOres("stairWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairStone").size(); i++) {
			if (OreDictionary.getOres("stairStone").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairs").size(); i++) {
			if (OreDictionary.getOres("stairs").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block == Blocks.stone_stairs) {
			return true;
		}
		if (block == Blocks.stone_brick_stairs) {
			return true;
		}
		if (block == Blocks.sandstone_stairs) {
			return true;
		}
		if (block == Blocks.brick_stairs) {
			return true;
		}
		if (block == Blocks.quartz_stairs) {
			return true;
		}
		if (block == Blocks.nether_brick_stairs) {
			return true;
		}

		return false;
	}

	public boolean checkPlanks(Block block) {

		for (int i = 0; i < OreDictionary.getOres("plankWood").size(); i++) {
			if (OreDictionary.getOres("plankWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("planksWood").size(); i++) {
			if (OreDictionary.getOres("planksWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}

}
