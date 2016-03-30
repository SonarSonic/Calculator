package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.network.packets.PacketStorageChamber;
import sonar.calculator.mod.utils.SlotBigStorage;

public class ContainerStorageChamber extends Container {

	private TileEntityStorageChamber entity;
	public int[] currentStored;

	public ContainerStorageChamber(InventoryPlayer inventory, TileEntityStorageChamber entity) {
		this.entity = entity;

		addSlotToContainer(new SlotBigStorage(entity, 0, 26, 6 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 1, 62, 6 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 2, 98, 6 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 3, 134, 6 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 4, 8, 32 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 5, 44, 32 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 6, 80, 32 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 7, 116, 32 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 8, 152, 32 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 9, 8, 58 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 10, 44, 58 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 11, 80, 58 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 12, 116, 58 + 17));
		addSlotToContainer(new SlotBigStorage(entity, 13, 152, 58 + 17));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 17));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142 + 17));
		}
	}

	public void addCraftingToCrafters(ICrafting crafters) {
		// super.addCraftingToCrafters(crafters);
		for (int i = 0; i < entity.getStorage().stored.length; i++) {
			crafters.sendProgressBarUpdate(this, i, entity.getStorage().stored[i]);
		}
	}

	/** Looks for changes made in the container, sends them to every listener. */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			for (int s = 0; s < entity.getStorage().stored.length; s++) {
				if (this.currentStored == null || entity.getStorage().stored[s] != this.currentStored[s]) {
					icrafting.sendProgressBarUpdate(this, s, entity.getStorage().stored[s]);
				}
			}
		}
		this.currentStored = this.entity.getStorage().stored;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id < 14) {
			this.entity.getStorage().stored[id] = value;
		}

	}

	public void updateStoredValue(int id, int value) {
		for (Object o : crafters) {
			if (o != null && o instanceof EntityPlayerMP) {
				Calculator.network.sendTo(new PacketStorageChamber(entity.getPos(), id, value), (EntityPlayerMP) o);

			}
		}
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID < 14) {
				if (!this.mergeItemStack(entity.getStorage().getSlotStack(slotID, 1), 14, this.inventorySlots.size(), true)) {
					return null;
				}
				if (!entity.getWorld().isRemote) {
					if (entity.getStorage().stored[slotID] == 1) {
						entity.getStorage().resetSavedStack(slotID);
					}
					entity.getStorage().stored[slotID]--;
					updateStoredValue(slotID, entity.getStorage().stored[slotID]);
				}
				return null;

			} else if (!addCircuit(itemstack1)) {
				System.out.print("yo");
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

		}

		return itemstack;
	}

	public boolean addCircuit(ItemStack stack) {
		if (entity.getCircuitType(stack) == null) {
			return false;
		}
		if (entity.getStorage().getSavedStack() != null) {
			if (entity.getCircuitType(stack) != entity.getCircuitType(entity.getStorage().getSavedStack())) {
				return false;
			}
		}
		if (entity.getStorage().stored[stack.getItemDamage()] != entity.getStorage().maxSize) {
			if (!entity.getWorld().isRemote) {
				if (entity.getStorage().getSavedStack() == null) {
					entity.getStorage().setSavedStack(stack);
				}
				entity.getStorage().stored[stack.getItemDamage()]++;
				updateStoredValue(stack.getItemDamage(), entity.getStorage().stored[stack.getItemDamage()]);
			}
			stack.stackSize--;

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}

	@Override
	public boolean canDragIntoSlot(Slot slot) {
		if (slot.slotNumber < 14) {
			return false;
		}
		return true;
	}

	public ItemStack slotClick(int slotID, int var, int button, EntityPlayer player) {
		if (!(slotID < 14) || button == 1) {
			return super.slotClick(slotID, var, button, player);
		}
		return null;
	}
}
