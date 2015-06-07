package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.core.utils.DischargeValues;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerLantern extends ContainerSync {
	private TileEntityGasLantern entity;

	public ContainerLantern(InventoryPlayer inventory,
			TileEntityGasLantern entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 80, 34));

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

			if ((p_82846_2_ != 0)) {
				if (TileEntityFurnace.getItemBurnTime(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {

						return null;
					}
				}
			}else if ((p_82846_2_ >= 1) && (p_82846_2_ < 28)) {
					if (!mergeItemStack(itemstack1, 28, 37, false)) {
						return null;
					}
				} else if ((p_82846_2_ >= 28) && (p_82846_2_ < 37)
						&& (!mergeItemStack(itemstack1, 1, 28, false))) {
					return null;
				
			} else if (!mergeItemStack(itemstack1, 1, 37, false)) {
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
}
