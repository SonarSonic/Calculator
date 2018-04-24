package sonar.calculator.mod.common.containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerBasicGreenhouse extends ContainerSync {
	private TileEntityBasicGreenhouse entity;
	public static TransferSlotsManager<TileEntityBasicGreenhouse> transfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityBasicGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityBasicGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkLog(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityBasicGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityBasicGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkStairs(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityBasicGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityBasicGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkGlass(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityBasicGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityBasicGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkPlanks(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new TransferSlots<TileEntityBasicGreenhouse>(TransferType.TILE_INV, 9) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityBasicGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() instanceof IPlantable;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerBasicGreenhouse(InventoryPlayer inventory, TileEntityBasicGreenhouse tileentity) {
		super(tileentity);
		this.entity = tileentity;
		addSlotToContainer(new Slot(tileentity, 0, 26, 22));
		addSlotToContainer(new Slot(tileentity, 1, 44, 22));
		addSlotToContainer(new Slot(tileentity, 2, 26, 40));
		addSlotToContainer(new Slot(tileentity, 3, 44, 40));
		addSlotToContainer(new Slot(tileentity, 4, 80, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(tileentity, 5 + j, 8 + j * 18, 88));
		}
		addInventory(inventory, 8, 110);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUsableByPlayer(player);
	}
}
