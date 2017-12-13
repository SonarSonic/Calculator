package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.recipes.RecipeHelperV2;

public class ContainerAtomicCalculator extends ContainerSonar implements ICalculatorCrafter {

	private TileEntityCalculator.Atomic atomic;
	public static TransferSlotsManager<TileEntityCalculator.Atomic> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityCalculator.Atomic>(TransferType.TILE_INV, 3));
			addTransferSlot(new TransferSlots<TileEntityCalculator.Atomic>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};

	private static final int INV_START = 4, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	private EntityPlayer player;

	public ContainerAtomicCalculator(EntityPlayer player, TileEntityCalculator.Atomic atomic) {
		this.player = player;
		this.atomic = atomic;

		for (int k = 0; k < 3; k++) {
			addSlotToContainer(new SlotPortableCrafting(this, atomic, k, 20 + k * 32, 35, null));
		}
		addSlotToContainer(new SlotPortableResult(player, atomic, this, new int[] { 0, 1, 2 }, 3, 134, 35));
		addInventory(player.inventory, 8, 84);
		onItemCrafted();
	}

	@Override
	public void removeEnergy(int remove) {}

	@Override
	public void onItemCrafted() {
		atomic.setInventorySlotContents(3, RecipeHelperV2.getItemStackFromList(AtomicCalculatorRecipes.instance().getOutputs(player, atomic.getStackInSlot(0), atomic.getStackInSlot(1), atomic.getStackInSlot(2)), 0));
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!this.player.getEntityWorld().isRemote) {
			atomic.setInventorySlotContents(3, ItemStack.EMPTY);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, atomic, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return atomic.isUseableByPlayer(player);
	}
}
