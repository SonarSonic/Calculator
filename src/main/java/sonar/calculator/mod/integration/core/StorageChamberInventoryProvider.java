package sonar.calculator.mod.integration.core;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.api.ActionType;
import sonar.core.api.InventoryHandler;
import sonar.core.api.StoredItemStack;

public class StorageChamberInventoryProvider extends InventoryHandler {

	public static String name = "Storage Chamber";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canHandleItems(TileEntity tile, EnumFacing dir) {
		return tile instanceof TileEntityStorageChamber;
	}

	@Override
	public StoredItemStack getStack(int slot, TileEntity tile, EnumFacing dir) {
		if (!(slot < 14)) {
			return null;
		}
		if (tile instanceof TileEntityStorageChamber) {
			TileEntityStorageChamber chamber = (TileEntityStorageChamber) tile;
			if (chamber != null) {
				if (chamber.getStorage().getSavedStack() != null) {
					ItemStack stack = chamber.getStorage().getFullStack(slot);
					if (stack != null) {
						return new StoredItemStack(stack);

					}
				}
			}
		}
		return null;
	}

	@Override
	public StorageSize getItems(List<StoredItemStack> storedStacks, TileEntity tile, EnumFacing dir) {
		TileEntityStorageChamber chamber = (TileEntityStorageChamber) tile;		
		TileEntityStorageChamber.StorageChamberInventory inv = chamber.getStorage();
		if (chamber != null) {
			if (inv.getSavedStack() != null) {
				long stored = 0;
				long maxStorage = 0;
				for (int i = 0; i < 14; i++) {
					ItemStack stack = inv.getFullStack(i);
					if (stack != null) {
						stored += stack.stackSize;
						storedStacks.add(new StoredItemStack(stack));
					}
					maxStorage += inv.maxSize;
				}
				return new StorageSize(stored, maxStorage);

			}
		}
		return StorageSize.EMPTY;
	}

	public boolean isLoadable() {
		return true;
	}

	@Override
	public StoredItemStack addStack(StoredItemStack add, TileEntity tile, EnumFacing dir, ActionType action) {
		TileEntityStorageChamber chamber = (TileEntityStorageChamber) tile;
		TileEntityStorageChamber.StorageChamberInventory inv = chamber.getStorage();
		if (inv.getSavedStack() != null) {
			if (chamber.getCircuitType(add.getItemStack()) == chamber.getCircuitType(inv.getSavedStack())) {
				int stored = inv.getStored()[add.getItemDamage()];
				if (stored == inv.maxSize) {
					return add;
				}
				if (stored + add.getStackSize() <= inv.maxSize) {
					if (!action.shouldSimulate())
						inv.increaseStored(add.getItemDamage(), (int) add.getStackSize());
					return null;
				} else {
					if (!action.shouldSimulate())
						inv.setStored(add.getItemDamage(), inv.maxSize);
					add.stored -= inv.maxSize - stored;
					return add;
				}
			}
		} else if (chamber.getCircuitType(add.getItemStack()) != null) {
			if (!action.shouldSimulate()){
				inv.setSavedStack(add.getItemStack().copy());
			}
			if (add.getStackSize() <= inv.maxSize) {
				if (!action.shouldSimulate())
					inv.stored[add.getItemDamage()] += add.getStackSize();
				return null;
			} else {
				if (!action.shouldSimulate())
					inv.stored[add.getItemDamage()] = inv.maxSize;
				add.stored -= inv.maxSize;
				return add;
			}
		}

		return add;
	}

	@Override
	public StoredItemStack removeStack(StoredItemStack remove, TileEntity tile, EnumFacing dir, ActionType action) {
		TileEntityStorageChamber chamber = (TileEntityStorageChamber) tile;
		TileEntityStorageChamber.StorageChamberInventory inv = chamber.getStorage();
		if (inv.getSavedStack() != null) {
			if (chamber.getCircuitType(remove.getItemStack()) == chamber.getCircuitType(inv.getSavedStack())) {
				int stored = inv.stored[remove.getItemDamage()];
				if (stored != 0) {
					if (stored <= remove.getStackSize()) {
						ItemStack stack = inv.getFullStack(remove.getItemDamage());
						if (!action.shouldSimulate()) {
							inv.stored[remove.getItemDamage()] = 0;
							inv.resetSavedStack(remove.getItemDamage());
						}
						remove.stored -= stack.stackSize;

					} else {
						ItemStack stack = inv.getSlotStack(remove.getItemDamage(), (int) remove.getStackSize());
						if (!action.shouldSimulate())
							inv.stored[remove.getItemDamage()] -= remove.getStackSize();
						return null;
					}
				}
			}
		}

		return remove;
	}

}