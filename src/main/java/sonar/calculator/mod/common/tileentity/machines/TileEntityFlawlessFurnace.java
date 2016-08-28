package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.containers.ContainerFlawlessFurnace;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.core.api.machines.IPausable;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.RecipeHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.utils.IGuiTile;

public class TileEntityFlawlessFurnace extends TileEntityEnergySidedInventory implements IPausable, IGuiTile {
	
	public SyncTagType.INT[] cookTime = new SyncTagType.INT[9];
	public float renderTicks;
	public double energyBuffer;
	public boolean paused;
	public final int speed = 100;
	public final int size = 9;
	public int maxProcess;

	public TileEntityFlawlessFurnace() {
		this.inv = new SonarInventory(this, 28);
		super.storage.setCapacity(10000000).setMaxTransfer(64000);
	}

	public void update() {
		super.update();
		if (this.worldObj.isBlockPowered(pos)) {
			this.paused = true;
			return;
		} else {
			this.paused = false;
		}
		this.discharge(27);
		if (!paused) {
			for (int i = 0; i < size; i++) {
				if (this.cookTime[i].getObject() > 0) {
					this.cookTime[i].increaseBy(1);
					if (!this.worldObj.isRemote) {
						energyBuffer += (energyUsage() / speed) * 8;
						int energyUsage = (int) Math.round(energyBuffer);
						if (energyBuffer - energyUsage < 0) {
							this.energyBuffer = 0;
						} else {
							energyBuffer -= energyUsage;
						}
						this.storage.modifyEnergyStored(-energyUsage);
					}
				}
				if (this.canProcess(i)) {
					if (!this.worldObj.isRemote) {
						if (this.cookTime[i].getObject() == 0) {
							this.cookTime[i].increaseBy(1);
							energyBuffer += energyUsage() / speed;
							int energyUsage = (int) Math.round(energyBuffer);
							if (energyBuffer - energyUsage < 0) {
								this.energyBuffer = 0;
							} else {
								energyBuffer -= energyUsage;
							}
							this.storage.modifyEnergyStored(-energyUsage);
						}
						if (this.cookTime[i].getObject() >= this.currentSpeed()) {
							for (int process = 0; process < 8; process++) {
								if (canProcess(i)) {
									this.finishProcess(i);
								}
							}
							if (canProcess(i)) {
								this.cookTime[i].increaseBy(1);
							} else {
							}
							this.cookTime[i].setObject(0);
							this.energyBuffer = 0;
						}
					}
				} else {
					renderTicks = 0;
					if (cookTime[i].getObject() != 0) {
						this.cookTime[i].setObject(0);
						this.energyBuffer = 0;
					}
				}
			}
		}

		this.markDirty();
	}

	public boolean canProcess(int slot) {
		if (slots()[slot] == null) {
			return false;
		}

		if (cookTime[slot].getObject() == 0) {
			// if (this.storage.getEnergyStored() < energyUsage()) {
			// return false;
			// }
		}
		ItemStack[] output = getOutput(true, slots()[slot]);
		if (output == null || output.length == 0) {
			return false;
		}
		for (int o = 0; o < output.length; o++) {
			if (output[o] == null) {
				return false;
			} else {
				if (slots()[slot + ((o + 1) * size)] != null) {
					if (!slots()[slot + ((o + 1) * size)].isItemEqual(output[o])) {

						return false;
					} else if (slots()[slot + ((o + 1) * size)].stackSize + output[o].stackSize > slots()[slot + ((o + 1) * size)].getMaxStackSize()) {

						return false;
					}
				}
			}
		}
		return true;
	}

	public void finishProcess(int slot) {
		ItemStack[] output = getOutput(false, slots()[slot]);
		for (int o = 0; o < output.length; o++) {
			if (output[o] != null) {
				if (this.slots()[slot + ((o + 1) * size)] == null) {
					ItemStack outputStack = output[o].copy();
					if (output[o].getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					this.slots()[slot + ((o + 1) * size)] = outputStack;
				} else if (this.slots()[slot + ((o + 1) * size)].isItemEqual(output[o])) {
					this.slots()[slot + ((o + 1) * size)].stackSize += output[o].stackSize;

				}
			}
		}
		if (recipeHelper() != null) {
			this.slots()[slot].stackSize -= recipeHelper().getInputSize(0, output);
		} else {
			this.slots()[slot].stackSize -= 1;
		}
		if (this.slots()[slot].stackSize <= 0) {
			this.slots()[slot] = null;
		}

	}

	public RecipeHelper recipeHelper() {
		return AlgorithmSeparatorRecipes.instance();
	}

	public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
		// return new ItemStack[] {
		// FurnaceRecipes.smelting().getSmeltingResult(stacks[0]) };
		return recipeHelper().getOutput(stacks);
	}

	public int currentSpeed() {
		return speed;
	}

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public double energyUsage() {

		return 5000;
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			markBlockForUpdate();
		}
		return true;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			for (int i = 0; i < cookTime.length; i++){
				cookTime[i].readData(nbt, type);
			}
			this.paused = nbt.getBoolean("pause");
		}

	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			for (int i = 0; i < cookTime.length; i++){
				cookTime[i].writeData(nbt, type);
			}
			nbt.setBoolean("pause", this.paused);
		}
		return nbt;
	}

	// IPausable
	@Override
	public boolean isActive() {
		return !isPaused();
	}

	@Override
	public void onPause() {
		paused = !paused;
		markBlockForUpdate();
		this.worldObj.addBlockEvent(pos, blockType, 1, 1);
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing dir) {
		return true;
	}

	public boolean canStack(ItemStack current, ItemStack stack) {
		if (current == null) {
			return true;
		}
		if (current.stackSize == current.getMaxStackSize()) {
			return false;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		return currenttip;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return null;
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new ContainerFlawlessFurnace(player.inventory, this);
	}

}
