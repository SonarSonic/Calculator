package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.core.utils.DischargeValues;
import sonar.core.utils.SonarAPI;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerAdvancedGreenhouse extends ContainerSync {
	private TileEntityAdvancedGreenhouse entity;
	public int lastMulti;
	public int lastBuilt;
	public int lastCarbon;
	public int lastOxygen;
	public int lastEnergy;

	public ContainerAdvancedGreenhouse(InventoryPlayer inventory,
			TileEntityAdvancedGreenhouse entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 30 - 4, 15 + 3));
		addSlotToContainer(new Slot(entity, 1, 12 - 4, 33 + 3));
		addSlotToContainer(new Slot(entity, 2, 30 - 4, 33 + 3));
		addSlotToContainer(new Slot(entity, 3, 48 - 4, 33 + 3));
		addSlotToContainer(new Slot(entity, 4, 12 - 4, 51 + 3));
		addSlotToContainer(new Slot(entity, 5, 30 - 4, 51 + 3));
		addSlotToContainer(new Slot(entity, 6, 48 - 4, 51 + 3));
		addSlotToContainer(new Slot(entity, 7, 80, 61));
			for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(entity, 8+j, 8 + j * 18, 88));
			}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						8 + j * 18, 110 + i * 18));
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

			if (!(slotID <= 16)) {
				if ((checkLog(Block.getBlockFromItem(itemstack1.getItem())))) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if ((checkStairs(Block.getBlockFromItem(itemstack1
						.getItem())))) {
					if (!mergeItemStack(itemstack1, 1, 4, false)) {
						return null;
					}
				} else if ((checkGlass(Block.getBlockFromItem(itemstack1
						.getItem())))) {
					if (!mergeItemStack(itemstack1, 4, 6, false)) {
						return null;
					}
				} else if ((checkPlanks(Block.getBlockFromItem(itemstack1
						.getItem())))) {
					if (!mergeItemStack(itemstack1, 6, 7, false)) {
						return null;
					}
				} else if (DischargeValues.discharge().value(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 7, 8, false)) {
						return null;
					}
				} 
				else if (itemstack1.getItem() instanceof IEnergyContainerItem) {
					if (!mergeItemStack(itemstack1, 7, 8, false)) {
						return null;
					}
				} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof ISpecialElectricItem) {
					if (!mergeItemStack(itemstack1, 7, 8, false)) {
						return null;
					}
				} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
					if (!mergeItemStack(itemstack1, 7, 8, false)) {
						return null;
					}
				}else if (itemstack1.getItem() instanceof IPlantable) {
					if (!mergeItemStack(itemstack1, 8, 17, false)) {
						return null;
					}
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if ((slotID >= 17) && (slotID < 44)) {
				if (!mergeItemStack(itemstack1, 43, 53, false)) {
					return null;
				}
			} else if ((slotID >= 43) && (slotID < 53)
					&& (!mergeItemStack(itemstack1, 17, 43, false))) {
				return null;

			} else if (!mergeItemStack(itemstack1, 17, 53, false)) {
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
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkGlass(Block block) {

		for (int i = 0; i < OreDictionary.getOres("blockGlass").size(); i++) {
			if (OreDictionary.getOres("blockGlass").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("blockGlassColorless").size(); i++) {
			if (OreDictionary.getOres("blockGlassColorless").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlassColorless").size(); i++) {
			if (OreDictionary.getOres("paneGlassColorless").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlass").size(); i++) {
			if (OreDictionary.getOres("paneGlass").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkStairs(Block block) {

		for (int i = 0; i < OreDictionary.getOres("stairWood").size(); i++) {
			if (OreDictionary.getOres("stairWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairStone").size(); i++) {
			if (OreDictionary.getOres("stairStone").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairs").size(); i++) {
			if (OreDictionary.getOres("stairs").get(i).getItem() == Item
					.getItemFromBlock(block)) {
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
			if (OreDictionary.getOres("plankWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("planksWood").size(); i++) {
			if (OreDictionary.getOres("planksWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
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
