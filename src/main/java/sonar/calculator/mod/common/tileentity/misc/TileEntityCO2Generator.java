package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.misc.GuiCO2Generator;
import sonar.calculator.mod.common.containers.ContainerCO2Generator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityCO2Generator extends TileEntityEnergyInventory implements ISidedInventory, IGuiTile {

	public int burnTime;
	public int maxBurnTime;
	public int maxBurn = 10000;
	public int energyAmount = 100000;
	public int gasAdd;
	public boolean controlled;
	public boolean control;
	private static final int[] input = new int[] { 0 };
	public EnumFacing forward = EnumFacing.NORTH;
	public EnumFacing horizontal = EnumFacing.EAST;

	public TileEntityCO2Generator() {
		super.storage.setCapacity(1000000).setMaxTransfer(64000);
		super.inv = new SonarInventory(this, 2);
		super.energyMode = EnergyMode.RECIEVE;
		syncList.addPart(inv);
	}

	@Override
	public void update() {
		super.update();
		forward = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();
		horizontal = SonarHelper.getHorizontal(forward);
		if (SonarHelper.getHorizontal(forward) != null) {
			boolean flag1 = this.burnTime > 0;
			boolean flag2 = false;
			EnumFacing hoz = SonarHelper.getHorizontal(forward).getOpposite();
            TileEntity tile = this.world.getTileEntity(pos.add(hoz.getFrontOffsetX() * 3, 0, hoz.getFrontOffsetZ() * 3));
			ItemStack burnStack = slots().get(0);
			if (this.maxBurnTime == 0 && !this.world.isRemote && !burnStack.isEmpty()) {
				if (TileEntityFurnace.isItemFuel(burnStack) && this.storage.getEnergyStored() >= energyAmount) {
					if (tile != null && tile instanceof TileEntityFlawlessGreenhouse) {
						burn();
						this.storage.modifyEnergyStored(-energyAmount);
					}
				}
			}
			if (!this.controlled) {
				if (this.maxBurnTime != 0 && this.burnTime >= 0 && this.burnTime < this.maxBurnTime) {
					flag2 = true;
					burnTime++;
				}
			}

			if (this.controlled) {
				if (tile != null && tile instanceof TileEntityFlawlessGreenhouse) {
					TileEntityFlawlessGreenhouse greenhouse = (TileEntityFlawlessGreenhouse) tile;
					int carbon = greenhouse.getCarbon();
					if (control) {
						if (!(carbon == greenhouse.maxLevel)) {
							if (this.maxBurnTime != 0 && this.burnTime >= 0 && this.burnTime < this.maxBurnTime) {
								flag2 = true;
								burnTime++;
								this.gasAdd = 800;
							}
						} else {
							this.control = false;
						}
					}
					if (!control) {
						if (carbon <= 92000) {
							this.control = true;
						} else {
							this.gasAdd = 0;
						}
					}
				}
			}
			if (this.burnTime == this.maxBurnTime) {
				this.maxBurnTime = 0;
				this.burnTime = 0;
				this.gasAdd = 0;
				flag2 = true;
			}
			if (flag2) {
				this.markDirty();
			}
		}
		discharge(1);
	}

	public void burn() {
		ItemStack burnStack = slots().get(0);		
		this.maxBurnTime = maxBurn;
		this.gasAdd = TileEntityFurnace.getItemBurnTime(burnStack) / 100;
		this.controlled = burnStack.getItem() == Calculator.controlled_Fuel;
		burnStack.shrink(1);
	}

	public boolean isBurning() {
        return this.maxBurnTime != 0;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			this.burnTime = nbt.getInteger("burnTime");
			this.maxBurnTime = nbt.getInteger("maxBurnTime");
			this.controlled = nbt.getBoolean("controlled");
			this.control = nbt.getBoolean("control");
			this.gasAdd = nbt.getInteger("gasAdd");
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			nbt.setInteger("burnTime", this.burnTime);
			nbt.setInteger("maxBurnTime", this.maxBurnTime);
			nbt.setBoolean("controlled", this.controlled);
			nbt.setBoolean("control", this.control);
			nbt.setInteger("gasAdd", this.gasAdd);
		}
		return nbt;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return input;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && TileEntityFurnace.isItemFuel(stack);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
		return false;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		if (burnTime > 0 && maxBurn != 0 && gasAdd == 0) {
			String burn = FontHelper.translate("co2.control");
			currenttip.add(burn);
		} else if (burnTime > 0 && maxBurn != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + burnTime * 100 / maxBurn;
			currenttip.add(burn);
		} else {
			String burn = FontHelper.translate("co2.burning");
			currenttip.add(burn);
		}
		return currenttip;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerCO2Generator(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiCO2Generator(player.inventory, this);
	}
}
