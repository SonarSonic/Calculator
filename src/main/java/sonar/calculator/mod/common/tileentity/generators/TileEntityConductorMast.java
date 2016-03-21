package sonar.calculator.mod.common.tileentity.generators;

import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.machines.IProcessMachine;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;

public class TileEntityConductorMast extends TileEntityEnergyInventory implements ISidedInventory, IProcessMachine {

	public int cookTime, lightingTicks, lightTicks;
	public int furnaceSpeed = 50;
	public int lightningSpeed, strikes;
	public int random;
	public int lastStations;
	public final int weatherStationRF = CalculatorConfig.getInteger("Weather Station");
	public final int strikeRF = CalculatorConfig.getInteger("Conductor Mast");
	public Random rand = new Random();

	public TileEntityConductorMast() {
		super.storage = new SyncEnergyStorage(5000000, 64000);
		super.inv = new SonarTileInventory(this, 2);
		super.maxTransfer = 5000000;
	}

	public ItemStack recipeOutput(ItemStack stack) {
		return RecipeRegistry.ConductorMastItemRecipes.instance().getCraftingResult(stack);
	}

	public int recipeEnergy(ItemStack stack) {
		return RecipeRegistry.ConductorMastPowerRecipes.instance().getPowercost(stack);
	}

	public void onLoaded() {
		setWeatherStationAngles(true, worldObj, pos);
	}

	@Override
	public void update() {
		super.update();
		if (this.cookTime > 0) {
			this.cookTime++;
		}
		if (!this.worldObj.isRemote) {
			if (this.canCook()) {
				if (cookTime == 0) {
					this.cookTime++;
				}

				if (this.cookTime == furnaceSpeed) {
					this.cookTime = 0;
					this.cookItem();
				}

			} else {
				this.cookTime = 0;
			}
		}
		if (!this.worldObj.isRemote) {
			if (this.storage.getMaxEnergyStored() != this.storage.getEnergyStored() && this.storage.getEnergyStored() < storage.getMaxEnergyStored() && this.lightningSpeed == 0) {
				this.random = (int) (Math.random() * +9);
				this.lightningSpeed = rand.nextInt(1800 - 1500) + getNextTime();
			}
		}
		if (this.lightningSpeed > 0) {

			if (this.lightingTicks >= lightningSpeed) {
				this.lightingTicks = 0;
				this.lightningSpeed = 0;
				strikes = this.getStrikes();
				if (this.worldObj.isRemote) {
					int currentstrikes;
					if (strikes > 5) {
						currentstrikes = 5;
					} else {
						currentstrikes = strikes;
					}
					while (currentstrikes != 0) {

						worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 4, pos.getZ()));
						currentstrikes--;
					}
				}
				this.lastStations = this.getStations();
				lightTicks++;
			} else {
				this.lightingTicks++;
			}

		}
		if (lightTicks > 0) {
			int add = (((strikeRF / 200) + (this.lastStations * (weatherStationRF / 200))) * strikes);
			if (lightTicks < 200) {
				if (this.storage.getEnergyStored() + add <= this.storage.getMaxEnergyStored()) {
					lightTicks++;
					storage.receiveEnergy(add, false);

				} else {
					lightTicks++;
					storage.setEnergyStored(this.storage.getMaxEnergyStored());
				}
			} else {
				if (this.storage.getEnergyStored() + add <= storage.getMaxEnergyStored()) {
					lightTicks = 0;
					storage.receiveEnergy(add, false);
				} else {
					lightTicks++;
					storage.setEnergyStored(this.storage.getMaxEnergyStored());
				}
			}
		}

		addEnergy();
		this.markDirty();

	}

	private void addEnergy() {

		TileEntity entity = SonarHelper.getAdjacentTileEntity(this, EnumFacing.DOWN);

		if (SonarHelper.isEnergyHandlerFromSide(entity, EnumFacing.DOWN.getOpposite())) {

			this.storage.extractEnergy(SonarHelper.pushEnergy(entity, EnumFacing.UP, this.storage.extractEnergy(maxTransfer, true), false), false);
		}
	}

	private void lightningStrike(World world, int x, int y, int z) {
		if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
			world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
		}

	}

	public boolean canCook() {
		if (this.storage.getEnergyStored() == 0) {
			return false;
		}

		if (slots()[0] == null) {
			return false;
		}

		if (cookTime >= furnaceSpeed) {
			return true;
		}

		ItemStack itemstack = recipeOutput(slots()[0]);
		if (itemstack == null) {
			return false;
		}
		if (slots()[1] != null) {
			if (!slots()[1].isItemEqual(itemstack)) {
				return false;
			} else if (slots()[1].stackSize + itemstack.stackSize > slots()[1].getMaxStackSize()) {
				return false;
			}
		}

		int itemEnergy = recipeEnergy(slots()[0]);
		if (cookTime == 0) {
			if (this.storage.getEnergyStored() < itemEnergy) {
				return false;
			}
		}

		return true;

	}

	private void cookItem() {

		ItemStack itemstack = recipeOutput(slots()[0]);
		int energy = recipeEnergy(slots()[0]);
		this.storage.modifyEnergyStored(-energy);

		if (this.slots()[1] == null) {
			this.slots()[1] = itemstack.copy();

		} else if (this.slots()[1].isItemEqual(itemstack)) {
			this.slots()[1].stackSize++;
		}
		this.slots()[0].stackSize--;

		if (this.slots()[0].stackSize <= 0) {
			this.slots()[0] = null;
		}

	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.cookTime = nbt.getInteger("CookTime");
			this.lightingTicks = nbt.getInteger("Ticks");
			this.lightTicks = nbt.getInteger("lightTicks");
			this.lightningSpeed = nbt.getInteger("lightSpeed");
			this.random = nbt.getInteger("rand");
			if (type == SyncType.SAVE) {
				this.lastStations = nbt.getInteger("lastStations");
				this.strikes = nbt.getInteger("strikes");
			}
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("CookTime", this.cookTime);
			nbt.setInteger("Ticks", this.lightingTicks);
			nbt.setInteger("lightTicks", this.lightTicks);
			nbt.setInteger("lightSpeed", this.lightningSpeed);
			nbt.setInteger("rand", this.random);

			if (type == SyncType.SAVE) {
				nbt.setInteger("lastStations", this.lastStations);
				nbt.setInteger("strikes", this.strikes);
			}
		}
	}

	@Override
	public boolean canConnectEnergy(EnumFacing direction) {
		if (direction == EnumFacing.DOWN) {
			return true;
		}
		return false;
	}

	public static void setWeatherStationAngles(boolean packet, World world, BlockPos pos) {
		for (int x = -10; x <= 10; x++) {
			for (int z = -10; z <= 10; z++) {
				BlockPos currentPos = pos.add(x, 0, z);
				TileEntity target = world.getTileEntity(currentPos);
				if (target != null && target instanceof TileEntityWeatherStation) {
					TileEntityWeatherStation station = (TileEntityWeatherStation) target;
					station.setAngle();
					world.markBlockForUpdate(currentPos);

				}
			}
		}
	}

	public int getStations() {
		int stations = 0;
		for (int x = -10; x <= 10; x++) {
			for (int z = -10; z <= 10; z++) {
				TileEntity target = worldObj.getTileEntity(pos.add(x, 0, z));
				if (target != null && target instanceof TileEntityWeatherStation) {
					TileEntityWeatherStation station = (TileEntityWeatherStation) target;
					if (station.x == pos.getX() && station.z == pos.getZ()) {
						stations++;
					}
				}
			}
		}
		return stations;
	}

	public int getStrikes() {
		int trans = this.getTransmitters();
		if (trans != 0) {
			if (trans > 9) {
				return trans / 4;
			} else {
				return 1;
			}
		}
		return 1;

	}

	public int getNextTime() {
		int trans = this.getTransmitters();
		if (trans != 0) {
			if (trans > 9) {
				return 250;
			} else {
				return 1500 - (135 * trans);
			}
		}
		return 1500;

	}

	public int getTransmitters() {
		int transmitter = 0;
		for (int x = -20; x <= 20; x++) {
			for (int z = -20; z <= 20; z++) {
				TileEntity target = worldObj.getTileEntity(pos.add(x, 0, z));
				if (target != null && target instanceof TileEntityTransmitter) {
					TileEntityTransmitter station = (TileEntityTransmitter) target;
					transmitter++;
				}
			}
		}
		return transmitter;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? new int[] { 1 } : new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		if (slot == 0) {
			if (stack != null && recipeOutput(stack) != null) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
		return slot == 1;
	}

	@Override
	public int getCurrentProcessTime() {
		return cookTime;
	}

	@Override
	public int getProcessTime() {
		return furnaceSpeed;
	}

	@Override
	public double getEnergyUsage() {
		if (slots()[0] != null) {
			return recipeEnergy(slots()[0]);
		}
		return 0;
	}
}
