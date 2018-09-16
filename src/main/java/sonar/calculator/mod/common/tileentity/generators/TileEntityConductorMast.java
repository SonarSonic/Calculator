package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
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
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.sync.SyncTagType;
import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.RecipeHelperV2;

import java.util.Random;

public class TileEntityConductorMast extends TileEntityEnergyInventory implements IProcessMachine, IFlexibleGui {

	public final SyncTagType.INT cookTime = new SyncTagType.INT(0);
	public final SyncTagType.INT lightningSpeed = new SyncTagType.INT(1);
	public final SyncTagType.INT lightTicks = new SyncTagType.INT(2);
	public final SyncTagType.INT lightingTicks = new SyncTagType.INT(3);
	public final SyncTagType.INT random = new SyncTagType.INT(4);
	public final SyncTagType.INT rfPerStrike = new SyncTagType.INT(5);
	public final SyncTagType.DOUBLE rfPerTick = new SyncTagType.DOUBLE(6);

	public int energyUsage;
	public int lastStations, strikes, avgTicks;
	public Random rand = new Random();

	public TileEntityConductorMast() {
		super.storage.setCapacity(CalculatorConfig.CONDUCTOR_MAST_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.CONDUCTOR_MAST_TRANSFER_RATE);
		super.CHARGING_RATE = CalculatorConfig.CONDUCTOR_MAST_CHARGING_RATE;
		super.energyMode = EnergyMode.SEND;
		super.inv.setSize(2);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> SLOT == 0 && ConductorMastRecipes.instance().isValidInput(STACK), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE) -> SLOT == 1, EnumFilterType.EXTERNAL);
		syncList.addParts(cookTime, lightingTicks, lightTicks, lightningSpeed, random, rfPerStrike, rfPerTick);
	}


	public DefaultSonarRecipe.Value getRecipe(ItemStack stack) {
		return ConductorMastRecipes.instance().getRecipeFromInputs(null, new Object[] { stack });
	}

	public void onLoaded() {
		setWeatherStationAngles(true, world, pos);
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
			if (cookTime.getObject() >= CalculatorConfig.CONDUCTOR_MAST_SPEED) {
				cookTime.setObject(0);
				cookItem();
			}
		} else {
			cookTime.setObject(0);
		}
		if (this.storage.getFullCapacity() != this.storage.getEnergyLevel() && this.storage.getEnergyLevel() < storage.getFullCapacity() && this.lightningSpeed.getObject() == 0) {
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
						world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY() + 4, pos.getZ(), true));
						currentstrikes--;
					}
				}
				this.lastStations = this.getStations();
				lightTicks.increaseBy(1);
				if (isServer()) {
                    rfPerStrike.setObject((CalculatorConfig.CONDUCTOR_MAST_PER_TICK / 200 + this.lastStations * CalculatorConfig.WEATHER_STATION_PER_TICK / 200) * strikes * 4 * 200);
                    rfPerTick.setObject((double) rfPerStrike.getObject() / lastSpeed);
				}
			} else {
				lightingTicks.increaseBy(1);
			}
		}
		if (lightTicks.getObject() > 0) {
            int add = (CalculatorConfig.CONDUCTOR_MAST_PER_TICK / 200 + this.lastStations * CalculatorConfig.WEATHER_STATION_PER_TICK / 200) * strikes * 4;
			if (lightTicks.getObject() < 200) {
				if (this.storage.getEnergyLevel() + add <= this.storage.getFullCapacity()) {
					lightTicks.increaseBy(1);
					storage.modifyEnergyStored(add);
				} else {
					lightTicks.increaseBy(1);
					storage.setEnergyStored(this.storage.getFullCapacity());
				}
			} else {
				if (this.storage.getEnergyLevel() + add <= storage.getFullCapacity()) {
					lightTicks.setObject(0);
					storage.modifyEnergyStored(add);
				} else {
					lightTicks.increaseBy(1);
					storage.setEnergyStored(this.storage.getFullCapacity());
				}
			}
		}
		if(isServer()) {
			addEnergy(EnumFacing.DOWN);
			this.markDirty();
		}
	}

	private void lightningStrike(World world, int x, int y, int z) {
		if (storage.getEnergyLevel() < storage.getFullCapacity()) {
			world.spawnEntity(new EntityLightningBolt(world, x, y, z, true));
		}
	}

	public boolean canCook() {
		ItemStack toCook = slots().get(0);
		if (this.storage.getEnergyLevel() == 0 || toCook.isEmpty()) {
			return false;
		}

		if (cookTime.getObject() >= CalculatorConfig.CONDUCTOR_MAST_SPEED) {
			return true;
		}
		DefaultSonarRecipe.Value recipe = this.getRecipe(toCook);
		if (recipe == null) {
			return false;
		}
		ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), 0);
		ItemStack outputStack = slots().get(1);
		if (!outputStack.isEmpty()) {
			if (!outputStack.isItemEqual(stack)) {
				return false;
			} else if (outputStack.getCount() + stack.getCount() > outputStack.getMaxStackSize()) {
				return false;
			}
		}
		energyUsage = recipe.getValue();
		if (cookTime.getObject() == 0) {
            return this.storage.getEnergyLevel() >= energyUsage;
		}

		return true;
	}

	private void cookItem() {
		ItemStack inputStack = slots().get(0);
		DefaultSonarRecipe.Value recipe = this.getRecipe(inputStack);
		if (recipe == null)
			return;
		ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), 0);
		energyUsage = recipe.getValue();
		this.storage.modifyEnergyStored(-energyUsage);
		ItemStack outputStack = slots().get(1);

		if (outputStack.isEmpty()) {
			slots().set(1, stack.copy());
		} else if (outputStack.isItemEqual(stack)) {
			outputStack.grow(1);
		}
		inputStack.shrink(1);
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {
			this.lastStations = nbt.getInteger("lastStations");
			this.strikes = nbt.getInteger("strikes");
		}
	}

    @Override
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
				if (target instanceof TileEntityWeatherStation) {
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
				TileEntity target = world.getTileEntity(pos.add(x, 0, z));
				if (target instanceof TileEntityWeatherStation) {
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
                return 1500 - 135 * trans;
			}
		}
		return 1500;
	}

	public int getTransmitters() {
		int transmitter = 0;
		for (int x = -20; x <= 20; x++) {
			for (int z = -20; z <= 20; z++) {
				TileEntity target = world.getTileEntity(pos.add(x, 0, z));
				if (target instanceof TileEntityTransmitter) {
					TileEntityTransmitter station = (TileEntityTransmitter) target;
					transmitter++;
				}
			}
		}
		return transmitter;
	}

	@Override
	public int getCurrentProcessTime() {
		return cookTime.getObject();
	}

	@Override
	public int getProcessTime() {
		return getBaseProcessTime();
	}

	@Override
	public double getEnergyUsage() {
		return energyUsage;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerConductorMast(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiConductorMast(player.inventory, this);
	}

	@Override
	public int getBaseProcessTime() {
		return CalculatorConfig.CONDUCTOR_MAST_SPEED;
	}

    @Override
	public boolean maxRender() {
		return true;
	}
}
