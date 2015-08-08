package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.common.recipes.machines.AnalysingChamberRecipes;
import sonar.calculator.mod.network.packets.PacketSonarSides;
import sonar.core.common.tileentity.TileEntitySidedInventorySender;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAnalysingChamber extends TileEntitySidedInventorySender implements ISidedInventory, IUpgradeCircuits {

	public int stable, vUpgrade, analysed;
	public int maxTransfer = 2000;

	public TileEntityAnalysingChamber() {
		super.input = new int[] { 0 };
		super.output = new int[] { 2, 3, 4, 5, 6, 7 };
		super.storage = new EnergyStorage(1000000, 1000000);
		super.slots = new ItemStack[8];
		super.maxTransfer = 2000;

	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this.worldObj.isRemote) {
			return;
		}
		if (analysed == 1 && this.slots[0] == null) {
			this.analysed = 0;
			this.stable = 0;
		}
		if (canAnalyse()) {
			analyse(0);
		}
		charge(1);
		addEnergy();
		stable = stable(0);

		this.markDirty();
	}

	private void addEnergy() {
		TileEntity entity = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if (SonarHelper.isEnergyHandlerFromSide(entity, ForgeDirection.DOWN)) {		
			if(entity instanceof IEnergyHandler){	
			this.storage.modifyEnergyStored(-((IEnergyHandler) entity).receiveEnergy(ForgeDirection.UP, this.storage.extractEnergy(maxTransfer, true), false));
			}
		}
	}

	private void analyse(int slot) {
		if (slots[slot].hasTagCompound()) {
			NBTTagCompound tag = slots[slot].getTagCompound();
			int storedEnergy = itemEnergy(slots[slot].getTagCompound().getInteger("Energy"));
			this.storage.receiveEnergy(storedEnergy, false);

			if (vUpgrade == 0) {
				ItemStack item1 = AnalysingChamberRecipes.instance().getResult(1, tag.getInteger("Item1"));
				ItemStack item2 = AnalysingChamberRecipes.instance().getResult(1, tag.getInteger("Item2"));
				if (item1 != null) {
					add(item1, 2);
				}
				if (item2 == null) {
					add(item2, 3);
				}
			}
			ItemStack item3 = AnalysingChamberRecipes.instance().getResult(2, tag.getInteger("Item3"));
			ItemStack item4 = AnalysingChamberRecipes.instance().getResult(3, tag.getInteger("Item4"));
			ItemStack item5 = AnalysingChamberRecipes.instance().getResult(4, tag.getInteger("Item5"));
			ItemStack item6 = AnalysingChamberRecipes.instance().getResult(5, tag.getInteger("Item6"));

			if (item3 != null) {
				add(item3, 4);
			}
			if (item4 != null) {
				add(item4, 5);
			}
			if (item5 != null) {
				add(item5, 6);
			}
			if (item6 != null) {
				add(item6, 7);
			}

			tag.setInteger("Item1", 0);
			tag.setInteger("Item2", 0);
			tag.setInteger("Item3", 0);
			tag.setInteger("Item4", 0);
			tag.setInteger("Item5", 0);
			tag.setInteger("Item6", 0);
			tag.setInteger("Energy", 0);

			analysed = 1;
		}
	}

	private void add(ItemStack item, int slotID) {
		if (item != null) {
			if (this.canAnalyse()) {
				this.slots[slotID] = new ItemStack(item.getItem(), 1, item.getItemDamage());
			}
		}

	}

	private boolean canAnalyse() {
		if (slots[0] != null) {
			if (slots[0].getItem() == Calculator.circuitBoard) {
				if (this.slots[2] == null && this.slots[3] == null && this.slots[4] == null && this.slots[5] == null && this.slots[6] == null && this.slots[7] == null) {
					return true;
				}
			}
		}

		if (slots[0] == null) {
			stable = 0;
			return false;
		}

		return false;
	}

	public static int itemEnergy(int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1000;
		} else if (n == 2) {
			return 500;
		} else if (n == 3) {
			return 250;
		} else if (n == 4) {
			return 10000;
		} else if (n == 5) {
			return 5000;
		} else if (n == 6) {
			return 100000;
		} else if (n == 7) {
			return 100;
		} else if (n == 8) {
			return 175;
		} else if (n == 9) {
			return 400;
		} else if (n == 10) {
			return 750;
		} else if (n == 11) {
			return 800;
		}
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

	}

	private int stable(int par) {
		if (slots[par] != null) {
			if (slots[par].hasTagCompound() && slots[par].getItem() instanceof IStability) {
				IStability item = (IStability) slots[par].getItem();
				boolean stable = item.getStability(slots[par]);
				if (!stable) {
					item.onFalse(slots[par]);
				}
				return stable ? 1 : 0;
			}
		}
		return 0;

	}


	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (from == ForgeDirection.DOWN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (stack.getItem() == Calculator.circuitBoard) {
				return true;
			}
			if (stack.getItem() == Calculator.circuitBoard) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int slots) {
		return slot != 1;
	}

	@Override
	public boolean canAddUpgrades() {
		return true;
	}

	@Override
	public boolean canAddUpgrades(int type) {
		if (type == 2) {
			return true;
		}
		return false;
	}

	@Override
	public int getUpgrades(int type) {
		switch (type) {
		case 2:
			return vUpgrade;
		}
		return 0;
	}

	@Override
	public int getMaxUpgrades(int type) {
		switch (type) {
		case 2:
			return 1;
		}
		return 0;
	}

	@Override
	public void incrementUpgrades(int type, int increment) {
		if (type == 2) {
			vUpgrade += increment;
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.stable = nbt.getShort("Stable");
			this.vUpgrade = nbt.getShort("vUpgrade");
			if (type == SyncType.SAVE)
				this.analysed = nbt.getShort("analysed");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setShort("Stable", (short) this.stable);
			nbt.setShort("vUpgrade", (short) this.vUpgrade);
			if (type == SyncType.SAVE)
				nbt.setShort("analysed", (short) this.analysed);
		}

	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if (vUpgrade != 0) {
			currenttip.add(FontHelper.translate("circuit.void") + ": " + FontHelper.translate("circuit.installed"));
		}
		return currenttip;
	}

	@Override
	public void sendPacket(int dimension, int side, int value) {
		Calculator.network.sendToAllAround(new PacketSonarSides(xCoord, yCoord, zCoord, side, value), new TargetPoint(dimension, xCoord, yCoord, zCoord, 32));
	}

}
