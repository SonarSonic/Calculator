package sonar.calculator.mod.integration.ae2;

import java.lang.reflect.Constructor;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IExternalStorageHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public class StorageChamberHandler implements IExternalStorageHandler {

	public static class ReflectionFactory {
		private static Class classInventoryAdaptor;
		private static Class classMEAdaptor;
		private static Class classMonitor;

		private static Constructor constMEAdaptor;
		private static Constructor constMonitor;

		public static boolean init() {
			try {
				classInventoryAdaptor = Class.forName("appeng.util.InventoryAdaptor");
				classMEAdaptor = Class.forName("appeng.util.inv.IMEAdaptor");
				classMonitor = Class.forName("appeng.me.storage.MEMonitorIInventory");

				constMEAdaptor = classMEAdaptor.getConstructor(IMEInventory.class, BaseActionSource.class);
				constMonitor = classMonitor.getConstructor(classInventoryAdaptor);

				return true;
			} catch (Throwable t) {
				return false;
			}
		}

		public static IMEMonitor<IAEItemStack> createStorageBusMonitor(IMEInventory<IAEItemStack> inventory, BaseActionSource src) {
			try {
				Object adaptor = constMEAdaptor.newInstance(inventory, src);
				Object monitor = constMonitor.newInstance(adaptor);

				return (IMEMonitor<IAEItemStack>) monitor;
			} catch (Throwable t) {
				return null;
			}
		}
	}

	@Override
	public boolean canHandle(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource mySrc) {
		return te != null && te instanceof TileEntityStorageChamber;
	}

	@Override
	public IMEInventory getInventory(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource src) {
		if (te != null && te instanceof TileEntityStorageChamber) {
			return ReflectionFactory.createStorageBusMonitor(new StorageInventory(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord), src);
		}
		return null;
	}

	public static class StorageInventory implements IMEInventory<IAEItemStack> {

		private World world;
		private int x;
		private int y;
		private int z;

		public StorageInventory(World world, int x, int y, int z) {
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public TileEntityStorageChamber getChamber() {
			if (world != null) {
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile != null && tile instanceof TileEntityStorageChamber) {
					return (TileEntityStorageChamber) tile;
				}
			}
			return null;

		}

		@Override
		public IAEItemStack injectItems(IAEItemStack input, Actionable type, BaseActionSource src) {
			TileEntityStorageChamber chamber = getChamber();
			if (chamber != null) {
				if (chamber.getSavedStack() != null) {
					if (chamber.getCircuitType(input.getItemStack()) == chamber.getCircuitType(chamber.getSavedStack())) {
						int stored = chamber.getStored()[input.getItemDamage()];
						if (stored == chamber.maxSize) {
							return input;
						}
						if (stored + input.getStackSize() <= chamber.maxSize) {
							if (type != Actionable.SIMULATE)
								chamber.increaseStored(input.getItemDamage(), (int) input.getStackSize());
							return null;
						} else {
							if (type != Actionable.SIMULATE)
								chamber.setStored(input.getItemDamage(), chamber.maxSize);
							input.decStackSize(chamber.maxSize - stored);
							return input;
						}
					}
				} else if (chamber.getCircuitType(input.getItemStack()) != null) {

					if (type != Actionable.SIMULATE) {
						chamber.setSavedStack(input.getItemStack().copy());
					}

					if (input.getStackSize() <= chamber.maxSize) {
						if (type != Actionable.SIMULATE)
							chamber.stored[input.getItemDamage()] += input.getStackSize();
						return null;
					} else {
						if (type != Actionable.SIMULATE)
							chamber.stored[input.getItemDamage()] = chamber.maxSize;
						input.decStackSize(chamber.maxSize);
						return input;
					}
				}
			}
			return input;
		}

		@Override
		public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
			TileEntityStorageChamber chamber = getChamber();
			if (chamber != null) {
				if (chamber.getSavedStack() != null) {
					if (chamber.getCircuitType(request.getItemStack()) == chamber.getCircuitType(chamber.getSavedStack())) {
						int stored = chamber.stored[request.getItemDamage()];
						if (stored != 0) {
							if (stored <= request.getStackSize()) {

								ItemStack stack = chamber.getFullStack(request.getItemDamage());
								if (mode != Actionable.SIMULATE) {
									chamber.stored[request.getItemDamage()] = 0;
									chamber.resetSavedStack(request.getItemDamage());
								}
								return AEApi.instance().storage().createItemStack(stack);
							} else {

								ItemStack stack = chamber.getSlotStack(request.getItemDamage(), (int) request.getStackSize());
								if (mode != Actionable.SIMULATE) {
									chamber.stored[request.getItemDamage()] -= request.getStackSize();
								}
								return AEApi.instance().storage().createItemStack(stack);
							}
						}
					}
				}
			}
			return null;
		}

		@Override
		public IItemList getAvailableItems(IItemList out) {
			TileEntityStorageChamber chamber = getChamber();
			if (chamber != null) {
				if (chamber.getSavedStack() != null) {
					for (int i = 0; i < 14; i++) {
						ItemStack stack = chamber.getFullStack(i);
						if (stack != null) {
							out.add(AEApi.instance().storage().createItemStack(stack));
						}
					}
				}
			}
			return out;
		}

		@Override
		public StorageChannel getChannel() {
			return StorageChannel.ITEMS;
		}

	}

}
