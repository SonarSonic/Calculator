package sonar.calculator.mod.common.tileentity.generators;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.STRING;
import sonar.core.network.utils.IByteBufTile;

import java.util.List;

public class TileEntityCalculatorLocator extends TileEntityEnergyInventory implements IByteBufTile, ICalculatorLocator, IFlexibleGui {

	public SyncTagType.BOOLEAN active = new SyncTagType.BOOLEAN(0);
	public SyncTagType.INT size = new SyncTagType.INT(1);
	public SyncTagType.INT stability = new SyncTagType.INT(2);
	public SyncTagType.STRING owner = (STRING) new SyncTagType.STRING(3).setDefault("None");
	public SyncTagType.INT currentGen = new SyncTagType.INT(4);
	private int sizeTicks, luckTicks;

	public TileEntityCalculatorLocator() {
		super.storage.setCapacity(CalculatorConfig.CALCULATOR_LOCATOR_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.CALCULATOR_LOCATOR_TRANSFER_RATE);
		super.CHARGING_RATE = CalculatorConfig.CALCULATOR_LOCATOR_CHARGING_RATE;
		super.inv.setSize(2);
		super.inv.getInsertFilters().put(SlotHelper.chargeSlot(0), EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(1, s -> s.getItem() instanceof ILocatorModule), EnumFilterType.EXTERNAL_INTERNAL);
		super.energyMode = EnergyMode.SEND;
		syncList.addParts(active, size, stability, owner, currentGen);
	}

	@Override
	public void update() {
		super.update();
		boolean flag = active.getObject();
		boolean invert = false;
		if (canGenerate()) {
			if (isServer()) {
				beginGeneration();
			}
			if (!active.getObject()) {
				invert = true;
			}
		} else if (active.getObject()) {
			invert = true;
		}
		if (isServer()) {
			if (invert) {
				this.active.invert();
			}
			if (!(this.sizeTicks >= 25)) {
				sizeTicks++;
			} else {
				sizeTicks = 0;
				createStructure();
				getStability();
			}

			if (flag != active.getObject()) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(CalculatorLocator.ACTIVE, active.getObject()), 2);
				SonarCore.sendPacketAround(this, 128, 1);
			}
			charge(0);
			addEnergy(EnumFacing.DOWN);
		}
		//markDirty();
	}

    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public int currentOutput() {
		int size = this.size.getObject();
        if (size != 0 && (2 * size + 1) * (2 * size + 1) - 1 != 0) {
            int stable = stability.getObject() * 100 / ((2 * size + 1) * (2 * size + 1));
            return (int) ((5 + (int) (1000 * Math.sqrt(size * 1.8) - 100 * Math.sqrt(100 - stable)) / (int) (11 - Math.sqrt(stable)) * size) * CalculatorConfig.CALCULATOR_LOCATOR_MULTIPLIER);
		}
		return 0;
	}

	public void getStability() {
		if (world.isRemote) {
			return;
		}
		int currentStable = 0;
		if (size.getObject() == 0) {
			this.stability.setObject(0);
		}

        for (int Z = -size.getObject(); Z <= size.getObject(); Z++) {
            for (int X = -size.getObject(); X <= size.getObject(); X++) {
				TileEntity target = world.getTileEntity(pos.add(X, 0, Z));
				if (target instanceof TileEntityCalculatorPlug) {
					TileEntityCalculatorPlug plug = (TileEntityCalculatorPlug) target;
					currentStable += plug.getS();
				}
			}
		}
		this.stability.setObject(currentStable);
	}

	public boolean canGenerate() {
		if (!(this.storage.getEnergyLevel() < this.storage.getFullCapacity()) || size.getObject() == 0) {
			return false;
		}
		if (isLocated()) {
			if (this.stability.getObject() >= 7) {
				return true;
			} else {
				EntityPlayer player = this.world.getPlayerEntityByName(getOwner());
                return player != null;
			}
		}

		return false;
	}

	public void beginGeneration() {
		currentGen.setObject(currentOutput());
		storage.modifyEnergyStored(currentGen.getObject());
		if (!this.world.isRemote) {
			if (this.luckTicks >= 0 && this.luckTicks != 50) {
				this.luckTicks++;
			} else if (this.luckTicks == 50) {
				this.luckTicks = 0;
				this.effectStart();
			}
			if (CalculatorConfig.CALCULATOR_LOCATOR_CAN_CHANGE_TIME) {
				if (stability.getObject() * 4 < 20) {
					this.timeStart();
				}
			}
		}
	}

	private void timeStart() {
		this.world.setWorldTime(world.getWorldTime() + 100);
	}

	public void addItem(EntityPlayer player, Item item) {
		player.inventory.addItemStackToInventory(new ItemStack(item));
	}

	public void addPotion(EntityPlayer player, String potionID) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(potionID), 1000, 1));
	}

	private void effectStart() {
		EntityPlayer player = this.world.getPlayerEntityByName(getOwner());
		if (player != null) {
			double x = player.posX;
			double y = player.posY;
			double z = player.posZ;
			int stability = this.stability.getObject();
            int luck = 1 + (int) (Math.random() * (20 * (stability + 1) - 1 + 20 * (stability + 1)));

			if (stability == 0) {
				world.createExplosion(player, x, y, z, 4F, true);
				player.setHealth(player.getHealth() - 4);
			} else {
				if (stability < 2) {
					switch (luck) {
					case 1:
						world.createExplosion(player, x, y, z, 4F, true);
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
						world.createExplosion(player, x, y, z, 8F, true);
						break;
					case 4:
						world.createExplosion(player, x, y, z, 6F, true);
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
                        int luck2 = 1 + (int) (Math.random() * (5 - 1) + 5);
                        if (luck2 == 16) {
                            world.createExplosion(player, x, y, z, 80F, true);
                            player.setHealth(player.getHealth() - 40);
                        }
                        break;
					}
				}
			}
		}
	}

	protected boolean isLocated() {
		ItemStack stack = this.getStackInSlot(1);
		if (stack.isEmpty()) {
			return false;
		}
		if (stack.getItem() instanceof ILocatorModule) {
			String name = ((ILocatorModule) stack.getItem()).getPlayer(stack);
            return name != null && !name.equals("None");
		}

		return false;
	}

	public void onInventoryContentsChanged(int slot){
		super.onInventoryContentsChanged(slot);
		if(slot == 1) createOwner();
	}

	public void createOwner() {
		ItemStack stack = this.getStackInSlot(1);
		if (stack.isEmpty()) {
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
		}
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {
			this.luckTicks = nbt.getInteger("ticks");
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("ticks", this.luckTicks);
		}
		return nbt;
	}

	@Override
	public EnergyMode getModeForSide(EnumFacing side) {
		if(side == null){
			return EnergyMode.SEND_RECIEVE;
		}
		if (side == EnumFacing.DOWN) {
			return EnergyMode.SEND;
		}
		return EnergyMode.BLOCKED;
	}

    @Override
	public boolean maxRender() {
		return true;
	}

	public int beamHeight() {
		int f = 0;
		for (int i = 1; i <= 256; i++) {
			if (this.world.isSideSolid(pos.add(0, i, 0), EnumFacing.UP)) {
				return i;
			}
		}
		return 256;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		currenttip.add(FontHelper.translate("locator.active") + ": " + (!state.getValue(CalculatorLocator.ACTIVE) ? FontHelper.translate("locator.false") : FontHelper.translate("locator.true")));
		currenttip.add(FontHelper.translate("locator.owner") + ": " + (!owner.getObject().equals("None") ? owner.getObject() : FontHelper.translate("locator.none")));
		return currenttip;
	}

    @Override
	public void onFirstTick() {
		super.onFirstTick();
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
        return stability.getObject() * 100 / ((2 * size.getObject() + 1) * (2 * size.getObject() + 1) - 1);
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerCalculatorLocator(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiCalculatorLocator(player.inventory, this);
	}
}