package sonar.calculator.mod.common.tileentity.generators;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.items.ILocatorModule;
import sonar.calculator.mod.api.machines.ICalculatorLocator;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorLocator;
import sonar.calculator.mod.common.block.generators.CalculatorLocator;
import sonar.calculator.mod.common.containers.ContainerCalculatorLocator;
import sonar.core.SonarCore;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.STRING;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

public class TileEntityCalculatorLocator extends TileEntityEnergyInventory implements IByteBufTile, ICalculatorLocator, IGuiTile {

	public SyncTagType.BOOLEAN active = new SyncTagType.BOOLEAN(0);
	public SyncTagType.INT size = new SyncTagType.INT(1);
	public SyncTagType.INT stability = new SyncTagType.INT(2);
	public SyncTagType.STRING owner = (STRING) new SyncTagType.STRING(3).setDefault("None");
	private int sizeTicks, luckTicks;

	public TileEntityCalculatorLocator() {
		super.storage = new SyncEnergyStorage(25000000, 128000);
		super.inv = new SonarInventory(this, 2);
		super.maxTransfer = 100000;
		super.energyMode = EnergyMode.SEND;
	}

	@Override
	public void update() {
		super.update();
		boolean flag = active.getObject();
		boolean invert = false;
		if (canGenerate()) {
			if (!worldObj.isRemote) {
				beginGeneration();
			}
			if (!active.getObject()) {
				invert = true;
			}
		} else if (active.getObject()) {
			invert = true;
		}
		if (!worldObj.isRemote && invert) {
			this.active.invert();
			SonarCore.sendPacketAround(this, 128, 0);
		}

		if (!(this.sizeTicks >= 25)) {
			sizeTicks++;
		} else {
			this.sizeTicks = 0;
			this.createStructure();
			this.getStability();
		}
		if (!worldObj.isRemote && flag != active.getObject()) {
			SonarCore.sendPacketAround(this, 64, 0);
		}

		this.charge(0);
		this.addEnergy(EnumFacing.DOWN);
		this.markDirty();
	}

	public int currentOutput() {
		int size = this.size.getObject();
		if (size != 0 && (((int) (2 * size + 1) * (2 * size + 1)) - 1) != 0) {
			int stable = (int) (stability.getObject() * 100) / ((int) (2 * size + 1) * (2 * size + 1));
			return (int) (((5 + ((int) (1000 * (Math.sqrt(size * 1.8)) - 100 * (Math.sqrt(100 - stable))) / (int) (11 - Math.sqrt(stable))) * size)) * 2);
		}
		return 0;
	}

	public void getStability() {
		int currentStable = 0;
		if (size.getObject() == 0) {
			this.stability.setObject(0);
		}

		for (int Z = -(size.getObject()); Z <= (size.getObject()); Z++) {
			for (int X = -(size.getObject()); X <= (size.getObject()); X++) {
				TileEntity target = this.worldObj.getTileEntity(pos.add(X, 0, Z));
				if (target != null && target instanceof TileEntityCalculatorPlug) {
					TileEntityCalculatorPlug plug = (TileEntityCalculatorPlug) target;
					currentStable += plug.getS();
				}
			}
		}
		this.stability.setObject(currentStable);
	}

	public boolean canGenerate() {
		if (!(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) || size.getObject() == 0) {
			return false;
		}
		if (isLocated()) {
			if (this.stability.getObject() >= 7) {
				return true;
			} else {
				EntityPlayer player = this.worldObj.getPlayerEntityByName(getOwner());
				if (player != null) {
					return true;
				}
			}
		}

		return false;
	}

	public void beginGeneration() {
		storage.modifyEnergyStored(currentOutput());
		if (!this.worldObj.isRemote) {
			if (this.luckTicks >= 0 && this.luckTicks != 50) {
				this.luckTicks++;
			} else if (this.luckTicks == 50) {
				this.luckTicks = 0;
				this.effectStart();
			}
			if (CalculatorConfig.timeEffect) {
				if (stability.getObject() * 4 < 20) {
					this.timeStart();
				}
			}
		}
	}

	private void timeStart() {
		this.worldObj.setWorldTime(worldObj.getWorldTime() + 100);
	}

	public void addItem(EntityPlayer player, Item item) {
		player.inventory.addItemStackToInventory(new ItemStack(item));
	}

	public void addPotion(EntityPlayer player, String potionID) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(potionID), 1000, 1));
	}

	private void effectStart() {
		EntityPlayer player = this.worldObj.getPlayerEntityByName(getOwner());
		if (player != null) {
			double x = player.posX;
			double y = player.posY;
			double z = player.posZ;
			int stability = this.stability.getObject();
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
						addPotion(player, "nausea");
						break;
					case 6:
						addPotion(player, "blindness");
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
						addPotion(player, "jump_boost");
						break;
					case 11:
						addPotion(player, "water_breathing");
						break;
					case 12:
						addPotion(player, "slowness");
						break;
					case 13:
						addPotion(player, "strength");
						break;
					}
				}
				if (stability < 7) {
					switch (luck) {
					case 14:
						addPotion(player, "wither");
						break;
					case 15:
						addItem(player, Items.MILK_BUCKET);
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
		if (stack.getItem() instanceof ILocatorModule) {
			String name = ((ILocatorModule) stack.getItem()).getPlayer(stack);
			if (name != null && !name.equals("None")) {
				return true;
			}
		}

		return false;

	}

	public void createOwner() {
		ItemStack stack = this.getStackInSlot(1);
		if (stack == null) {
			this.owner.setObject("None");
			return;
		}

		if (stack.getItem() instanceof ILocatorModule) {
			String name = ((ILocatorModule) stack.getItem()).getPlayer(stack);
			if (name != null) {
				this.owner.setObject(name);
			}
		}

	}

	public void createStructure() {
		int size = CalculatorLocator.multiBlockStructure(getWorld(), pos);
		if (size != this.size.getObject()) {
			this.size.setObject(size);
			SonarCore.sendPacketAround(this, 128, 1);
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {
			this.luckTicks = nbt.getInteger("ticks");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("ticks", this.luckTicks);
		}
	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Arrays.asList(active, size, stability, owner));
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if (from == EnumFacing.DOWN) {
			return true;
		}
		return false;
	}

	public boolean maxRender() {
		return true;
	}

	public int beamHeight() {
		int f = 0;
		for (int i = 1; i <= 256; i++) {
			if (this.worldObj.isSideSolid(pos.add(0, i, 0), EnumFacing.UP)) {
				return i;
			}
		}
		return 256;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(FontHelper.translate("locator.active") + ": " + (!active.getObject() ? FontHelper.translate("locator.false") : FontHelper.translate("locator.true")));
		currenttip.add(FontHelper.translate("locator.owner") + ": " + (!owner.getObject().equals("None") ? owner.getObject() : FontHelper.translate("locator.none")));
		return currenttip;
	}

	public void onLoaded() {
		super.onLoaded();
		createOwner();
		createStructure();
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		if (id == 0) {
			active.writeToBuf(buf);
		}
		if (id == 1) {
			size.writeToBuf(buf);
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 0) {
			active.readFromBuf(buf);
			markBlockForUpdate();
		}
		if (id == 1) {
			size.readFromBuf(buf);
		}
	}

	@Override
	public String getOwner() {
		return owner.getObject();
	}

	@Override
	public int getSize() {
		return size.getObject();
	}

	@Override
	public boolean isActive() {
		return active.getObject();
	}

	@Override
	public double getStabilityPercent() {
		return (stability.getObject() * 100 / (((2 * size.getObject()) + 1) * ((2 * size.getObject()) + 1) - 1));
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerCalculatorLocator(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiCalculatorLocator(player.inventory, this);
	}

}