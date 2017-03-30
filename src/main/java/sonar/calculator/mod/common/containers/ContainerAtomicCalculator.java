package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.api.nutrition.IHungerStore;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;
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

	private boolean isRemote;
	private static final int INV_START = 4, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	private EntityPlayer player;

	public ContainerAtomicCalculator(EntityPlayer player, TileEntityCalculator.Atomic atomic) {
		this.player = player;
		this.atomic = atomic;
		isRemote = player.getEntityWorld().isRemote;

		for (int k = 0; k < 3; k++) {
			addSlotToContainer(new SlotPortableCrafting(this, atomic, k, 20 + k * 32, 35, isRemote, null));
		}
		addSlotToContainer(new SlotPortableResult(player, atomic, this, new int[] { 0, 1, 2 }, 3, 134, 35, isRemote));
		addInventory(player.inventory, 8, 84);
		onItemCrafted();
	}

	public void removeEnergy(int remove) {}

	@Override
	public void onItemCrafted() {
		atomic.setInventorySlotContents(3, RecipeHelperV2.getItemStackFromList(AtomicCalculatorRecipes.instance().getOutputs(player, atomic.getStackInSlot(0), atomic.getStackInSlot(1), atomic.getStackInSlot(2)), 0));
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, atomic, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return atomic.isUseableByPlayer(player);
	}

}
