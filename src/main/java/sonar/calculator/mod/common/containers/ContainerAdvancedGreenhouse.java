package sonar.calculator.mod.common.containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerAdvancedGreenhouse extends ContainerSync {
	private TileEntityAdvancedGreenhouse entity;
	public static TransferSlotsManager<TileEntityAdvancedGreenhouse> transfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityAdvancedGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAdvancedGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkLog(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityAdvancedGreenhouse>(TransferType.TILE_INV, 3) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAdvancedGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkStairs(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityAdvancedGreenhouse>(TransferType.TILE_INV, 2) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAdvancedGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkGlass(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(new TransferSlots<TileEntityAdvancedGreenhouse>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAdvancedGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return GreenhouseHelper.checkPlanks(Block.getBlockFromItem(stack.getItem()));
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new TransferSlots<TileEntityAdvancedGreenhouse>(TransferType.TILE_INV, 9) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAdvancedGreenhouse inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() instanceof IPlantable;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerAdvancedGreenhouse(InventoryPlayer inventory, TileEntityAdvancedGreenhouse entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 35, 11));
		addSlotToContainer(new Slot(entity, 1, 17, 29));
		addSlotToContainer(new Slot(entity, 2, 35, 29));
		addSlotToContainer(new Slot(entity, 3, 53, 29));
		addSlotToContainer(new Slot(entity, 4, 17, 47));
		addSlotToContainer(new Slot(entity, 5, 35, 47));
		addSlotToContainer(new Slot(entity, 6, 53, 47));
		addSlotToContainer(new Slot(entity, 7, 80, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(entity, 8 + j, 8 + j * 18, 88));
		}
		addInventory(inventory, 8, 110);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
