package sonar.calculator.mod.common.tileentity.generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.block.generators.CalculatorLocator;
import sonar.core.common.tileentity.TileEntityInventorySender;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCalculatorLocator extends TileEntityInventorySender {

	public byte active;
	public int stability;
	public int energyGenerated = CalculatorConfig.locatorRF;
	public int luckTicks;
	public int size;

	public TileEntityCalculatorLocator() {
		super.storage = new EnergyStorage(25000000, 25000000);
		super.slots = new ItemStack[2];
		super.maxTransfer = 64000;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (canStart()) {
			start();
			if (active != 1) {
				this.active = 1;
				this.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);

			}
		} else {
			if (active != 0) {
				this.active = 0;
				this.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}

		this.charge(0);
		this.addEnergy();
		this.markDirty();
	}

	public int currentGenerated() {
		if (size != 0 && (((int) (2 * size + 1) * (2 * size + 1)) - 1) != 0) {
			int stable = (int) (stability * 100) / ((int) (2 * size + 1) * (2 * size + 1));
			return 5 + ((int) (1000 * (Math.sqrt(size * 1.8)) - 100 * (Math.sqrt(100 - stable))) / (int) (11 - Math.sqrt(stable))) * size;

		}
		return 0;
	}

	public void getStability() {
		int currentStable = 0;
		for (int Z = -(size); Z <= (size); Z++) {
			for (int X = -(size); X <= (size); X++) {
				TileEntity target = this.worldObj.getTileEntity(xCoord + X, yCoord, zCoord + Z);
				if (target != null && target instanceof TileEntityCalculatorPlug) {
					TileEntityCalculatorPlug plug = (TileEntityCalculatorPlug) target;
					currentStable += plug.getS();
				}
			}
		}
		this.stability = currentStable;
	}

	public boolean canStart() {
		if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
			if (multiblock(this.worldObj, xCoord, yCoord, this.zCoord) != 0) {
				getStability();
				if (isLocated()) {
					if (this.stability >= 7) {
						this.active = 1;
						return true;
					} else {
						ItemStack stack = this.getStackInSlot(1);
						String owner = stack.getTagCompound().getString("Player");
						EntityPlayer player = this.worldObj.getPlayerEntityByName(owner);

						if (!(player == null)) {
							this.active = 1;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public void start() {
		storage.modifyEnergyStored(currentGenerated());
		if (!this.worldObj.isRemote) {
			if (this.luckTicks >= 0 && this.luckTicks != 50) {
				this.luckTicks++;
			} else if (this.luckTicks == 50) {
				this.luckTicks = 0;
				this.effectStart();
			}
			if (CalculatorConfig.timeEffect) {
				if (stability * 4 < 20) {
					this.timeStart();
				}
			}
		}
	}

	private void timeStart() {
		this.worldObj.setWorldTime(worldObj.getWorldTime() + 100);

	}

	private void addEnergy() {
		TileEntity entity = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if (SonarHelper.isEnergyHandlerFromSide(entity, ForgeDirection.DOWN)) {
			this.storage.extractEnergy(SonarHelper.pushEnergy(entity, ForgeDirection.UP, maxTransfer, false), false);
		}
	}

	public void addItem(EntityPlayer player, Item item) {
		player.inventory.addItemStackToInventory(new ItemStack(item));
	}

	public void addPotion(EntityPlayer player, int potionID) {
		player.addPotionEffect(new PotionEffect(potionID, 1000, 1));
	}

	private void effectStart() {
		ItemStack stack = this.getStackInSlot(1);
		String owner = stack.getTagCompound().getString("Player");
		EntityPlayer player = this.worldObj.getPlayerEntityByName(owner);
		if (player != null) {
			double x = player.posX;
			double y = player.posY;
			double z = player.posZ;

			int luck = 1 + (int) (Math.random() * ((20 * (stability + 1) - 1) + 20 * (stability + 1)));

			if (stability == 0) {
				worldObj.createExplosion(player, x, y, z, 4F, true);
				player.setHealth(player.getHealth() - 4);

			} else {
				if (stability < 2) {
					switch (luck) {
					case 1:
						worldObj.createExplosion(player, x, y, z, 4F, true);
						player.setHealth(player.getHealth() - 4);
						break;
					}
				}
				if (stability < 4) {
					switch (luck) {
					case 2:
						player.setFire(400);
						break;
					case 3:
						worldObj.createExplosion(player, x, y, z, 8F, true);
						break;
					case 4:
						worldObj.createExplosion(player, x, y, z, 6F, true);
						break;
					case 5:
						addPotion(player, Potion.confusion.id);
						break;
					case 6:
						addPotion(player, Potion.blindness.id);
						break;
					}
				}
				if (stability < 6) {
					switch (luck) {
					case 7:
						addItem(player, Calculator.grenade);
						break;
					case 8:
						addItem(player, Calculator.itemScientificCalculator);
						break;
					case 9:
						addItem(player, Calculator.itemCalculator);
						break;
					case 10:
						addPotion(player, Potion.jump.id);
						break;
					case 11:
						addPotion(player, Potion.waterBreathing.id);
						break;
					case 12:
						addPotion(player, Potion.digSlowdown.id);
						break;
					case 13:
						addPotion(player, Potion.damageBoost.id);
						break;
					}
				}
				if (stability < 7) {
					switch (luck) {
					case 14:
						addPotion(player, Potion.wither.id);
						break;
					case 15:
						addItem(player, Items.milk_bucket);
						break;
					case 16:
						if (luck == 16) {
							int luck2 = 1 + (int) (Math.random() * ((5) - 1) + 5);
							if (luck2 == 16) {
								worldObj.createExplosion(player, x, y, z, 80F, true);
								player.setHealth(player.getHealth() - 40);
							}
						}
						break;
					}
				}
			}
		}
	}

	protected boolean isLocated() {
		ItemStack stack = this.getStackInSlot(1);

		if (stack == null) {
			return false;
		}

		if (stack.hasTagCompound()) {
			if (stack.isItemEqual(new ItemStack(Calculator.itemLocatorModule))) {
				if (!(stack.getTagCompound().getString("Player") == "None")) {
					return true;
				}
			}
		}
		return false;

	}

	public String ownerUsername() {
		ItemStack stack = this.getStackInSlot(1);

		if (stack == null) {
			return "None";
		}

		if (stack.hasTagCompound()) {
			if (stack.isItemEqual(new ItemStack(Calculator.itemLocatorModule))) {
				return stack.getTagCompound().getString("Player");
			}

		}

		return "None";
	}

	public int multiblock(World world, int x, int y, int z) {
		int size = CalculatorLocator.multiBlockStructure(getWorldObj(), xCoord, yCoord, zCoord);
		if (size != this.size) {
			this.size = size;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return this.size;
	}

	public boolean multiblockstring() {
		if (size != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		this.stability = nbt.getInteger("stability");
		this.active = nbt.getByte("active");
		this.luckTicks = nbt.getInteger("ticks");
		this.size = nbt.getInteger("size");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		nbt.setInteger("stability", this.stability);
		nbt.setByte("active", this.active);
		nbt.setInteger("ticks", this.luckTicks);
		nbt.setInteger("size", this.size);

	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (from == ForgeDirection.DOWN) {
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public int beamHeight() {
		int f = 0;
		for (int i = 1; i <= 256; i++) {
			if (this.worldObj.isSideSolid(xCoord, yCoord + i, zCoord, ForgeDirection.UP)) {
				return i;
			}
		}
		return 256;
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return true;
	}

	@Override
	public void onSync(int data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.ACTIVE:
			this.active = (byte) data;
			break;
		case SyncType.SPECIAL1:
			this.stability = data;
			break;
		case SyncType.SPECIAL2:
			this.size = data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.ACTIVE:
			return new SyncData(true, active);
		case SyncType.SPECIAL1:
			return new SyncData(true, stability);
		case SyncType.SPECIAL2:
			return new SyncData(true, size);
		}
		return super.getSyncData(id);
	}
}