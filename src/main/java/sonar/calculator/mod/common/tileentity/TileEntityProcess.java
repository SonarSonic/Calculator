package sonar.calculator.mod.common.tileentity;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import sonar.core.SonarCore;
import sonar.core.api.inventories.IAdditionalInventory;
import sonar.core.api.machines.IPausable;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.api.upgrades.IUpgradableTile;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.handlers.inventories.handling.ItemTransferHelper;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.sync.IDirtyPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.INT;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.upgrades.UpgradeInventory;
import sonar.core.utils.MachineSideConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * electric smelting tile entity
 */
public abstract class TileEntityProcess extends TileEntityEnergySidedInventory implements IUpgradableTile, IPausable, IAdditionalInventory, IProcessMachine, IByteBufTile {

	public float renderTicks;
	public double energyBuffer;

	public SyncTagType.BOOLEAN invertPaused = new SyncTagType.BOOLEAN(0);
	public SyncTagType.BOOLEAN paused = new SyncTagType.BOOLEAN(1);
	public SyncTagType.INT cookTime = (INT) new SyncTagType.INT(2);
	public UpgradeInventory upgrades = new UpgradeInventory(3, 16, "ENERGY", "SPEED", "TRANSFER").addMaxiumum("TRANSFER", 1);

    public boolean isActive;
	private ProcessState state = ProcessState.UNKNOWN;
	private int currentProcessTime = -1;

	public static int lowestSpeed = 4, lowestEnergy = 1000;

	// client
	public int currentSpeed;

	public TileEntityProcess() {
		syncList.addParts(paused, invertPaused, cookTime, upgrades);
	}

    public enum ProcessState {
		TRUE, FALSE, UNKNOWN;

		public boolean canProcess() {
			return this == TRUE;
		}
	}

	public abstract boolean canProcess();

	public abstract void finishProcess();

    @Override
	public void update() {
		super.update();
		if (isServer()) {
			if (upgrades.getUpgradesInstalled("TRANSFER") > 0) {
				transferItems();
			}
			if (!isPaused()) {
				boolean forceUpdate = false;
				if (getProcessState().canProcess()) {
					if (this.cookTime.getObject() >= this.getProcessTime()) {
						this.finishProcess();
						cookTime.setObject(0);
						this.energyBuffer = 0;
						forceUpdate = true;
					} else if (this.cookTime.getObject() > 0) {
						this.cookTime.increaseBy(1);
						modifyEnergy();
					} else if (cookTime.getObject() == 0) {
						this.cookTime.increaseBy(1);
						modifyEnergy();
						forceUpdate = true;
					}
				} else {
					renderTicks = 0;
					if (cookTime.getObject() != 0) {
						cookTime.setObject(0);
						this.energyBuffer = 0;
						SonarCore.sendPacketAround(this, 128, 2);
						forceUpdate = true;
					}
				}
				if (forceUpdate) {
					isActive = isActive();
					SonarCore.sendPacketAround(this, 128, 2);
					world.addBlockEvent(pos, this.getBlockType(), 1, 1);
				}
			}
		} else {
			if (!isPaused()) {
				if (getProcessState().canProcess()) {
					renderTicks();
					cookTime.increaseBy(1);
				} else {
					if (cookTime.getObject() != 0) {
						renderTicks = 0;
						cookTime.setObject(0);
					}
				}
			}
		}
	}

	public ProcessState getProcessState() {
		return state = this.canProcess() ? ProcessState.TRUE : ProcessState.FALSE;
	}

	public void resetProcessState() {
		this.state = ProcessState.UNKNOWN;
		this.currentProcessTime = -1;
	}

	public void transferItems() {
		ArrayList<EnumFacing> outputs = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		for (EnumFacing side : outputs) {
			IItemHandler handler = ItemTransferHelper.getItemHandler(world, getPos().offset(side), side);
			if(handler != null)
				ItemTransferHelper.doSimpleTransfer(Lists.newArrayList(this.inv.getItemHandler(side)), Lists.newArrayList(handler), IS -> !IS.isEmpty(), 4);
		}
	}

    @Override
	public void onFirstTick() {
		super.onFirstTick();
		if (!world.isRemote) {
			isActive = this.isActive();
			SonarCore.sendPacketAround(this, 128, 2);
			world.addBlockEvent(pos, this.getBlockType(), 1, 1);
		}
	}

	public void modifyEnergy() {
		energyBuffer += getEnergyUsage();
        int energyUsage = (int) Math.floor(energyBuffer);
		if (energyBuffer - energyUsage < 0) {
			this.energyBuffer = 0;
		} else {
			energyBuffer -= energyUsage;
		}
		this.storage.modifyEnergyStored(-energyUsage);
	}

	public void renderTicks() {
		if (this instanceof TileEntityMachine.PrecisionChamber || this instanceof TileEntityMachine.ExtractionChamber) {
			this.renderTicks += (float) Math.max(1, upgrades.getUpgradesInstalled("SPEED")) / 50;
		} else {
			this.renderTicks += (float) Math.max(1, upgrades.getUpgradesInstalled("SPEED") * 8) / 1000;
		}
		if (this.renderTicks >= 2) {
			this.renderTicks = 0;
		}
	}

	public float getRenderPosition() {
		return renderTicks < 1 ? renderTicks : 1 - (renderTicks - 1);
	}

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public int requiredEnergy() {
		int speed = upgrades.getUpgradesInstalled("SPEED");
        int energy = upgrades.getUpgradesInstalled("ENERGY");
        float i = (float) ((double) speed / 16) * getBaseEnergyUsage();
        float e = (float) ((double) energy / 16) * getBaseEnergyUsage();
		return (int) (getBaseEnergyUsage() + i - e);
	}

    @Override
	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			markBlockForUpdate();
		}
		return true;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (type.isType(SyncType.DEFAULT_SYNC)) {
				this.currentSpeed = nbt.getInteger("speed");
			}
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		if (isServer() && forceSync) {
			SonarCore.sendPacketAround(this, 128, 2);
		}
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (type.isType(SyncType.DEFAULT_SYNC)) {
				nbt.setInteger("speed", this.getProcessTime());
			}
		}
		return nbt;
	}

	@Override
	public UpgradeInventory getUpgradeInventory() {
		return upgrades;
	}

	// IPausable
	@Override
	public boolean isActive() {
		if (world.isRemote) {
			return isActive;
		}
		return !isPaused() && cookTime.getObject() > 0;
	}

	@Override
	public void onPause() {
		// paused.invert();
		markBlockForUpdate();
		this.world.addBlockEvent(pos, blockType, 1, 1);
	}

	@Override
	public boolean isPaused() {
		// return invertPaused.getObject() ? paused.getObject() : !paused.getObject();
		return invertPaused.getObject();
		// return paused.getObject();
	}

	public boolean canStack(ItemStack current, ItemStack stack) {
		if (current.isEmpty()) {
			return true;
		} else return current.getCount() != current.getMaxStackSize();
    }

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		int speed = upgrades.getUpgradesInstalled("SPEED");
		int energy = upgrades.getUpgradesInstalled("ENERGY");
		if (speed != 0) {
			currenttip.add(FontHelper.translate("circuit.speed") + ": " + speed);
		}
		if (energy != 0) {
			currenttip.add(FontHelper.translate("circuit.energy") + ": " + energy);
		}
		return currenttip;
	}

	@Override
	public ItemStack[] getAdditionalStacks() {
		ArrayList<ItemStack> drops = upgrades.getDrops();
		if (drops == null || drops.isEmpty()) {
			return new ItemStack[] { ItemStack.EMPTY };
		}
		ItemStack[] toDrop = new ItemStack[drops.size()];
		int pos = 0;
		for (ItemStack drop : drops) {
			if (!drop.isEmpty()) {
				toDrop[pos] = drop;
			}
			pos++;
		}
		return toDrop;
	}

	public abstract int getBaseEnergyUsage();

	@Override
	public int getCurrentProcessTime() {
		return cookTime.getObject();
	}

	@Override
	public int getProcessTime() {
		if (this.currentProcessTime == -1) {
			int speed = upgrades.getUpgradesInstalled("SPEED");
			int energy = upgrades.getUpgradesInstalled("ENERGY");
            double i = (double) speed / 17 * getBaseProcessTime();
			if (speed == 16) {
				return currentProcessTime = 8;
			}
			return currentProcessTime = (int) Math.max(getBaseProcessTime() - i, lowestSpeed);
		}
		return currentProcessTime;
	}

	@Override
	public double getEnergyUsage() {
		return (double) requiredEnergy() / (double) getProcessTime();
	}

	@Override
	public void markChanged(IDirtyPart part) {
		super.markChanged(part);
		if (this.isServer() && (part == inv || part == upgrades)) {
			this.resetProcessState();
		}
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		if (id == 1) {
			invertPaused.invert();
			invertPaused.writeToBuf(buf);
		}
		if (id == 2) {
			invertPaused.writeToBuf(buf);
			paused.writeToBuf(buf);
			cookTime.writeToBuf(buf);
			buf.writeBoolean(isActive());
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 0) {
			ItemStack[] upgrades = getAdditionalStacks();
			Random rand = new Random();
			for (ItemStack stack : upgrades) {
				if (!stack.isEmpty()) {
					float f = rand.nextFloat() * 0.8F + 0.1F;
					float f1 = rand.nextFloat() * 0.8F + 0.1F;
					float f2 = rand.nextFloat() * 0.8F + 0.1F;

					EntityItem dropStack = new EntityItem(getWorld(), pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
					getWorld().spawnEntity(dropStack);
				}
			}
		}
		if (id == 1) {
			invertPaused.readFromBuf(buf);
			// onPause();
		}
		if (id == 2) {
			invertPaused.readFromBuf(buf);
			paused.readFromBuf(buf);
			cookTime.readFromBuf(buf);
			isActive = buf.readBoolean();
		}
	}
}
