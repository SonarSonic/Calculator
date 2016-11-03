package sonar.calculator.mod.common.tileentity.generators;

import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.common.containers.ContainerConductorMast;
import sonar.calculator.mod.common.recipes.ConductorMastRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.utils.IGuiTile;

public class TileEntityConductorMast extends TileEntityEnergyInventory implements ISidedInventory, IProcessMachine, IGuiTile {

	public final SyncTagType.INT cookTime = new SyncTagType.INT(0);
	public final SyncTagType.INT lightningSpeed = new SyncTagType.INT(1);
	public final SyncTagType.INT lightTicks = new SyncTagType.INT(2);
	public final SyncTagType.INT lightingTicks = new SyncTagType.INT(3);
	public final SyncTagType.INT random = new SyncTagType.INT(4);
	public final SyncTagType.INT rfPerStrike = new SyncTagType.INT(5);
	public final SyncTagType.DOUBLE rfPerTick = new SyncTagType.DOUBLE(6);

	
	public int energyUsage;
	public int lastStations, strikes, avgTicks;
	public static int furnaceSpeed = 50;
	public final int weatherStationRF = CalculatorConfig.getInteger("Weather Station");
	public final int strikeRF = CalculatorConfig.getInteger("Conductor Mast");
	public Random rand = new Random();

	public TileEntityConductorMast() {
		super.storage.setCapacity(50000000).setMaxTransfer(64000);
		super.inv = new SonarInventory(this, 2);
		super.maxTransfer = 5000000;
		super.energyMode = EnergyMode.SEND;
		syncParts.addAll(Lists.newArrayList(cookTime, lightingTicks, lightTicks, lightningSpeed, random, rfPerStrike, rfPerTick));
	}

	public DefaultSonarRecipe.Value getRecipe(ItemStack stack) {
		return ConductorMastRecipes.instance().getRecipeFromInputs(null, new Object[] { stack });
	}

	public void onLoaded() {
		setWeatherStationAngles(true, worldObj, pos);
	}

	@Override
	public void update() {
		super.update();

		if (this.cookTime.getObject() > 0) {
			this.cookTime.increaseBy(1);
		}
		if (canCook()) {
			if (cookTime.getObject() == 0) {
				cookTime.increaseBy(1);
			}
			if (cookTime.getObject() == furnaceSpeed) {
				cookTime.setObject(0);
				cookItem();
			}
		} else {
			cookTime.setObject(0);
		}
		if (this.storage.getMaxEnergyStored() != this.storage.getEnergyStored() && this.storage.getEnergyStored() < storage.getMaxEnergyStored() && this.lightningSpeed.getObject() == 0) {
			this.random.setObject((int) (Math.random() * +9));
			this.lightningSpeed.setObject(rand.nextInt(1800 - 1500) + getNextTime());
		}
		if (this.lightningSpeed.getObject() > 0) {
			if (this.lightingTicks.getObject() >= lightningSpeed.getObject()) {
				int lastSpeed = lightningSpeed.getObject();
				lightingTicks.setObject(0);
				lightningSpeed.setObject(0);
				strikes = this.getStrikes();
				if (isClient()) {
					int currentstrikes;
					if (strikes > 5) {
						currentstrikes = 5;
					} else {
						currentstrikes = strikes;
					}
					while (currentstrikes != 0) {
						worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 4, pos.getZ(), true));
						currentstrikes--;
					}
				}
				this.lastStations = this.getStations();
				lightTicks.increaseBy(1);
				if (isServer()) {
					rfPerStrike.setObject(((((strikeRF / 200) + (this.lastStations * (weatherStationRF / 200))) * strikes) * 4) * 200);
					rfPerTick.setObject((double) ((double) rfPerStrike.getObject() / lastSpeed));
				}

			} else {
				lightingTicks.increaseBy(1);
			}

		}
		if (lightTicks.getObject() > 0) {
			int add = (((strikeRF / 200) + (this.lastStations * (weatherStationRF / 200))) * strikes) * 4;
			if (lightTicks.getObject() < 200) {
				if (this.storage.getEnergyStored() + add <= this.storage.getMaxEnergyStored()) {
					lightTicks.increaseBy(1);
					storage.receiveEnergy(add, false);

				} else {
					lightTicks.increaseBy(1);
					storage.setEnergyStored(this.storage.getMaxEnergyStored());
				}
			} else {
				if (this.storage.getEnergyStored() + add <= storage.getMaxEnergyStored()) {
					lightTicks.setObject(0);
					storage.receiveEnergy(add, false);
				} else {
					lightTicks.increaseBy(1);
					storage.setEnergyStored(this.storage.getMaxEnergyStored());
				}
			}
		}

		addEnergy(EnumFacing.DOWN);
		this.markDirty();
	}

	private void lightningStrike(World world, int x, int y, int z) {
		if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
			world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z, true));
		}

	}

	public boolean canCook() {
		if (this.storage.getEnergyStored() == 0 || slots()[0] == null) {
			return false;
		}

		if (cookTime.getObject() >= furnaceSpeed) {
			return true;
		}
		DefaultSonarRecipe.Value recipe = this.getRecipe(slots()[0]);
		if (recipe == null) {
			return false;
		}
		ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), 0);
		if (slots()[1] != null) {
			if (!slots()[1].isItemEqual(stack)) {
				return false;
			} else if (slots()[1].stackSize + stack.stackSize > slots()[1].getMaxStackSize()) {
				return false;
			}
		}

		energyUsage = recipe.getValue();
		if (cookTime.getObject() == 0) {
			if (this.storage.getEnergyStored() < energyUsage) {
				return false;
			}
		}

		return true;

	}

	private void cookItem() {
		DefaultSonarRecipe.Value recipe = this.getRecipe(slots()[0]);
		if (recipe == null)
			return;
		ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), 0);
		energyUsage = recipe.getValue();
		this.storage.extractEnergy(energyUsage, false);

		if (this.slots()[1] == null) {
			this.slots()[1] = stack.copy();

		} else if (this.slots()[1].isItemEqual(stack)) {
			this.slots()[1].stackSize++;
		}
		this.slots()[0].stackSize--;

		if (this.slots()[0].stackSize <= 0) {
			this.slots()[0] = null;
		}

	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {
			this.lastStations = nbt.getInteger("lastStations");
			this.strikes = nbt.getInteger("strikes");
		}

	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("lastStations", this.lastStations);
			nbt.setInteger("strikes", this.strikes);
		}
		return nbt;
	}

	@Override
	public EnergyMode getModeForSide(EnumFacing side) {
		if (side == null) {
			return EnergyMode.SEND_RECIEVE;
		}
		if (side == EnumFacing.DOWN) {
			return EnergyMode.SEND;
		}
		return EnergyMode.BLOCKED;
	}

	public static void setWeatherStationAngles(boolean packet, World world, BlockPos pos) {
		for (int x = -10; x <= 10; x++) {
			for (int z = -10; z <= 10; z++) {
				TileEntity target = world.getTileEntity(pos.add(x, 0, z));
				if (target != null && target instanceof TileEntityWeatherStation) {
					TileEntityWeatherStation station = (TileEntityWeatherStation) target;
					station.setAngle();
					station.markBlockForUpdate();
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
			if (stack != null && ConductorMastRecipes.instance().isValidInput(stack)) {
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
		return cookTime.getObject();
	}

	@Override
	public int getProcessTime() {
		return furnaceSpeed;
	}

	@Override
	public double getEnergyUsage() {
		return energyUsage;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerConductorMast(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiConductorMast(player.inventory, this);
	}

	@Override
	public int getBaseProcessTime() {
		return furnaceSpeed;
	}

	public boolean maxRender() {
		return true;
	}
}
