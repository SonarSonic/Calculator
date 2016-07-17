package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.machines.GuiStorageChamber;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.ILargeInventory;
import sonar.core.inventory.SonarLargeInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityStorageChamber extends TileEntityInventory implements IGuiTile, ILargeInventory {

	public CircuitType circuitType = CircuitType.None;

	public void update() {
		super.update();
		this.resetCircuitType();
	}

	public TileEntityStorageChamber() {
		super.inv = new SonarLargeInventory(this, 14, 1024) {

			public boolean isItemValidForSlot(int slot, ItemStack item) {
				int target = (int) Math.floor(slot / getTileInv().numStacks);
				if (item != null && item.getItemDamage() == target) {
					CircuitType stackType = getCircuitType(item);
					if (stackType == null) {
						return false;
					}
					if (((TileEntityStorageChamber) tile).circuitType != CircuitType.None) {
						if (circuitType != stackType) {
							return false;
						}
					}
					return super.isItemValidForSlot(slot, item);
				}
				return false;
			}
		};
	}

	public SonarLargeInventory getTileInv() {
		return (SonarLargeInventory) inv;
	}

	public void resetCircuitType() {
		if (isServer()) {
			ArrayList<ItemStack>[] slots = getTileInv().slots;
			for (ArrayList<ItemStack> list : slots) {
				if (list != null) {
					ItemStack stack = null;
					for (ItemStack item : list) {
						if (item != null && item.stackSize > 0) {
							stack = item;
							break;
						}
					}
					if (stack != null) {
						CircuitType type = getCircuitType(stack);
						if (type != null && type != CircuitType.None) {
							circuitType = type;
							return;
						}
					}
				}
			}
			circuitType = CircuitType.None;
		}

	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			/*
			if(!nbt.hasKey("key")){
				resetCircuitType();
			}
			*/
			circuitType = CircuitType.values()[nbt.getInteger("type")];
			if (circuitType == null) {
				circuitType = CircuitType.None;
			}
		}
		if (type.isType(SyncType.DROP)) {
			inv.readData(nbt, SyncType.SAVE);
			circuitType = CircuitType.values()[nbt.getInteger("type")];
			if (circuitType == null) {
				circuitType = CircuitType.None;
			}
		}
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			nbt.setInteger("type", circuitType.ordinal());
		}
		if (type.isType(SyncType.DROP)) {
			inv.writeData(nbt, SyncType.SAVE);
			nbt.setInteger("type", circuitType.ordinal());
		}
		return nbt;
	}

	public static CircuitType getCircuitType(ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() == Calculator.circuitBoard && stack.getItem() instanceof IStability) {
				IStability stability = (IStability) stack.getItem();
				if (stability.getStability(stack) && stack.hasTagCompound()) {
					if (stack.getTagCompound().getBoolean("Analysed")) {
						return CircuitType.Stable;
					}
				} else if (!stack.hasTagCompound()) {
					return CircuitType.Analysed;
				} else if (stack.hasTagCompound()) {
					if (stack.getTagCompound().getBoolean("Analysed")) {
						return CircuitType.Analysed;
					}
				}
			} else if (stack.getItem() == Calculator.circuitDamaged) {
				return CircuitType.Damaged;
			} else if (stack.getItem() == Calculator.circuitDirty) {
				return CircuitType.Dirty;
			}
		}
		return null;
	}

	public enum CircuitType {
		Analysed, Stable, Damaged, Dirty, None;
		public boolean isProcessed() {
			return this == Analysed || this == Stable;
		}

		public boolean isStable() {
			return this == Stable;
		}
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerStorageChamber(player, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiStorageChamber(player, this);
	}

}
