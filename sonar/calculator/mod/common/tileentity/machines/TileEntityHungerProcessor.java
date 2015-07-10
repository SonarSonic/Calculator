package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IHungerStore;
import sonar.calculator.mod.api.ProcessType;
import sonar.calculator.mod.network.packets.PacketSonarSides;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.utils.ISyncTile;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityHungerProcessor extends TileEntitySidedInventory
		implements ISidedInventory, ISyncTile {

	public int storedpoints, speed = 1;

	public TileEntityHungerProcessor() {
		super.input = new int[] { 0 };
		super.output = new int[] { 1 };
		super.slots = new ItemStack[2];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
			food(slots[0]);
			charge(slots[1]);
		
		this.markDirty();
	}

	public void charge(ItemStack stack) {
		if (!(stack == null) && this.storedpoints != 0) {
			if (stack.getItem() instanceof IHungerStore) {
				IHungerStore module = (IHungerStore) stack.getItem();
				int hunger = module.getHungerPoints(stack);
				int max = module.getMaxHungerPoints(stack);
				if (!(hunger >= max) || max == -1) {
					if (storedpoints >= speed) {
						if (max == -1 || max >= hunger + speed) {
							module.transferHunger(speed, stack, ProcessType.ADD);
							storedpoints = storedpoints - speed;
						} else if (max != -1) {
							module.transferHunger(max - hunger, stack,
									ProcessType.ADD);
							storedpoints = storedpoints - (max - hunger);
						}
					} else if (storedpoints <= speed) {
						if (max == -1 | max >= hunger + speed) {
							module.transferHunger(speed, stack, ProcessType.ADD);
							storedpoints = 0;
						} else if (max != -1) {
							module.transferHunger(max - hunger, stack,
									ProcessType.ADD);
							storedpoints = storedpoints - max - hunger;
						}
					}
				}
			}
		}

	}

	private void food(ItemStack stack) {
		if (!(stack == null)) {
			if (stack.getItem() instanceof ItemFood) {
				ItemFood food = (ItemFood) stack.getItem();
				storedpoints = storedpoints + food.func_150905_g(stack);
				this.slots[0].stackSize--;
				if (this.slots[0].stackSize <= 0) {
					this.slots[0] = null;
				}
			}
			if (stack.getItem() instanceof IHungerStore) {

				IHungerStore module = (IHungerStore) stack.getItem();
				int hunger = module.getHungerPoints(stack);

				if (hunger != 0) {
					if (hunger >= speed) {
						module.transferHunger(speed, stack, ProcessType.REMOVE);
						storedpoints = storedpoints + speed;
					} else if (hunger <= speed) {
						module.transferHunger(hunger, stack, ProcessType.REMOVE);
						storedpoints = storedpoints + hunger;
					}
				}
			}
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		this.storedpoints = nbt.getInteger("Food");

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		nbt.setInteger("Food", this.storedpoints);

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int slots) {
		if (slot == 1) {
			if (this.storedpoints == 0) {
				return true;
			}
			if (!(this.storedpoints == 0)) {
				return false;
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(FontHelper.translate("points.hunger") + ": " + storedpoints);
		return currenttip;
	}
	
	@Override
	public void sendPacket(int dimension, int side, int value) {
		Calculator.network.sendToAllAround(new PacketSonarSides(xCoord, yCoord, zCoord, side, value), new TargetPoint(dimension, xCoord, yCoord, zCoord, 32));
		
	}
}
