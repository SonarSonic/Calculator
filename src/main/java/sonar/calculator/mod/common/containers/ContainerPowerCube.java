package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.core.api.SonarAPI;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.utils.SonarCompat;

public class ContainerPowerCube extends ContainerSync {

	private TileEntityPowerCube entity;
	public static TransferSlotsManager<TileEntityPowerCube> powerCubeTransfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityPowerCube>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityPowerCube inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return SonarAPI.getEnergyHelper().canTransferEnergy(stack) != null;
				}
			});
			addTransferSlot(new TransferSlots<TileEntityPowerCube>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityPowerCube inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return DischargeValues.getValueOf(stack) > 0 || SonarAPI.getEnergyHelper().canTransferEnergy(stack) != null;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerPowerCube(InventoryPlayer inventory, TileEntityPowerCube entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 80, 34));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addInventory(inventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return powerCubeTransfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUsableByPlayer(player);
	}
}
