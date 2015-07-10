package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cofh.api.energy.EnergyStorage;

public class TileEntityDockingStation extends TileEntityInventoryReceiver {

	public int type;

	public TileEntityDockingStation() {
		super.storage = new EnergyStorage(CalculatorConfig.cubeEnergy, CalculatorConfig.cubeEnergy);
		super.slots = new ItemStack[2];
	}

	/** type 0= No Calculator, 1=Basic, 2=Scientific, 3=Atomic, 4=Flawless */
	public void updateEntity() {
		super.updateEntity();
		int lastType = type;
		int currentType = isCalculator(slots[0]);
		if (currentType > 0) {
			this.type = currentType;
		} else {
			this.type = 0;
		}
		if (lastType != this.type) {
			this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
		this.markDirty();
	}

	public void setType() {

	}

	public static int isCalculator(ItemStack itemstack1) {
		if (itemstack1 != null) {
			if (itemstack1.getItem() == Calculator.itemCalculator) {
				return 1;
			}
			if (itemstack1.getItem() == Calculator.itemScientificCalculator) {
				return 2;
			}
			if (itemstack1.getItem() == Item.getItemFromBlock(Calculator.atomiccalculatorBlock)) {
				return 3;
			}
			if (itemstack1.getItem() == Calculator.itemFlawlessCalculator) {
				return 4;
			}
		}
		return 0;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.type = nbt.getInteger("Type");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("Type", this.type);

		}
	}

}
