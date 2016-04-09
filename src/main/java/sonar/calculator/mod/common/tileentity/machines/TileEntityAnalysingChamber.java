package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.common.containers.ContainerAnalysingChamber;
import sonar.calculator.mod.common.recipes.machines.AnalysingChamberRecipes;
import sonar.core.api.SonarAPI;
import sonar.core.api.upgrades.IUpgradableTile;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.common.tileentity.TileEntityEnergy.EnergyMode;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.IAdditionalInventory;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.network.sync.SyncTagType;
import sonar.core.upgrades.UpgradeInventory;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.MachineSideConfig;

import com.google.common.collect.Lists;

public class TileEntityAnalysingChamber extends TileEntityEnergySidedInventory implements IUpgradableTile, IAdditionalInventory, IGuiTile {

	public SyncTagType.INT stable = new SyncTagType.INT(0);
	public SyncTagType.INT analysed = new SyncTagType.INT(2);
	public int maxTransfer = 2000;

	public UpgradeInventory upgrades = new UpgradeInventory(1, "VOID", "TRANSFER");

	public TileEntityAnalysingChamber() {
		super.input = new int[] { 0 };
		super.output = new int[] { 2, 3, 4, 5, 6, 7 };
		super.storage = new SyncEnergyStorage(1000000, 64000);
		super.inv = new SonarTileInventory(this, 8){
			public void setInventorySlotContents(int i, ItemStack itemstack) {
				super.setInventorySlotContents(i, itemstack);
				if(i==0){
					worldObj.markBlockForUpdate(pos);
				}
			}
		};
		super.maxTransfer = 2000;
		super.energyMode = EnergyMode.SEND;

	}

	@Override
	public void update() {
		super.update();

		if (this.worldObj.isRemote) {
			return;
		}
		if (upgrades.getUpgradesInstalled("TRANSFER") > 0) {
			transferItems();
		}
		if (analysed.getObject() == 1 && this.slots()[0] == null) {
			this.analysed.setObject(0);
			this.stable.setObject(0);
		}
		if (canAnalyse()) {
			analyse(0);
		}
		charge(1);
		stable.setObject(stable(0));
		this.addEnergy(EnumFacing.VALUES);
		this.markDirty();
	}

	public void transferItems() {
		ArrayList<EnumFacing> outputs = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		for (EnumFacing side : outputs) {
			SonarAPI.getItemHelper().transferItems(this, SonarHelper.getAdjacentTileEntity(this, side), side.getOpposite(), side, null);
		}
		ArrayList<EnumFacing> inputs = sides.getSidesWithConfig(MachineSideConfig.INPUT);
		for (EnumFacing side : inputs) {
			int offset = 1;
			while (offset != 64) {
				TileEntity tile = worldObj.getTileEntity(getPos().offset(side, offset));
				if (tile != null && tile instanceof TileEntityStorageChamber) {
					SonarAPI.getItemHelper().transferItems(this, tile, side.getOpposite(), side, null);
					offset++;
				} else {
					break;
				}
			}
		}
	}

	private void analyse(int slot) {
		if (slots()[slot].hasTagCompound()) {
			NBTTagCompound tag = slots()[slot].getTagCompound();
			if (!tag.getBoolean("Analysed")) {
				int storedEnergy = itemEnergy(slots()[slot].getTagCompound().getInteger("Energy"));
				this.storage.receiveEnergy(storedEnergy, false);
				if (upgrades.getUpgradesInstalled("VOID") == 0) {
					ItemStack item1 = AnalysingChamberRecipes.instance().getResult(1, tag.getInteger("Item1"));
					ItemStack item2 = AnalysingChamberRecipes.instance().getResult(1, tag.getInteger("Item2"));
					if (item1 != null) {
						add(item1, 2);
					}
					if (item2 == null) {
						add(item2, 3);
					}
				}
				ItemStack item3 = AnalysingChamberRecipes.instance().getResult(2, tag.getInteger("Item3"));
				ItemStack item4 = AnalysingChamberRecipes.instance().getResult(3, tag.getInteger("Item4"));
				ItemStack item5 = AnalysingChamberRecipes.instance().getResult(4, tag.getInteger("Item5"));
				ItemStack item6 = AnalysingChamberRecipes.instance().getResult(5, tag.getInteger("Item6"));

				if (item3 != null) {
					add(item3, 4);
				}
				if (item4 != null) {
					add(item4, 5);
				}
				if (item5 != null) {
					add(item5, 6);
				}
				if (item6 != null) {
					add(item6, 7);
				}
				tag.removeTag("Item1");
				tag.removeTag("Item2");
				tag.removeTag("Item3");
				tag.removeTag("Item4");
				tag.removeTag("Item5");
				tag.removeTag("Item6");
				tag.removeTag("Energy");
				tag.setBoolean("Analysed", true);

				analysed.setObject(1);
			}
		}
	}

	private void add(ItemStack item, int slotID) {
		if (item != null) {
			if (this.canAnalyse()) {
				this.slots()[slotID] = new ItemStack(item.getItem(), 1, item.getItemDamage());
			}
		}

	}

	private boolean canAnalyse() {
		if (slots()[0] != null) {
			if (slots()[0].getItem() == Calculator.circuitBoard) {
				if (this.slots()[2] == null && this.slots()[3] == null && this.slots()[4] == null && this.slots()[5] == null && this.slots()[6] == null && this.slots()[7] == null) {
					return true;
				}
			}
		}

		if (slots()[0] == null) {
			stable.setObject(0);
			return false;
		}

		return false;
	}

	public static int itemEnergy(int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1000;
		} else if (n == 2) {
			return 500;
		} else if (n == 3) {
			return 250;
		} else if (n == 4) {
			return 10000;
		} else if (n == 5) {
			return 5000;
		} else if (n == 6) {
			return 100000;
		} else if (n == 7) {
			return 100;
		} else if (n == 8) {
			return 175;
		} else if (n == 9) {
			return 400;
		} else if (n == 10) {
			return 750;
		} else if (n == 11) {
			return 800;
		}
		return 0;
	}

	private int stable(int par) {
		if (slots()[par] != null) {
			if (slots()[par].hasTagCompound() && slots()[par].getItem() instanceof IStability) {
				IStability item = (IStability) slots()[par].getItem();
				boolean stable = item.getStability(slots()[par]);
				if (!stable) {
					item.onFalse(slots()[par]);
				}
				return stable ? 1 : 0;
			}
		}
		return 0;

	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if (from == EnumFacing.DOWN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (stack.getItem() == Calculator.circuitBoard) {
				return true;
			}
			if (stack.getItem() == Calculator.circuitBoard) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
		return slot != 1;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.readData(nbt, type);

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.writeData(nbt, type);

	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Lists.newArrayList(stable, analysed));
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		int vUpgrades = upgrades.getUpgradesInstalled("VOID");
		if (vUpgrades != 0) {
			currenttip.add(FontHelper.translate("circuit.void") + ": " + FontHelper.translate("circuit.installed"));
		}
		return currenttip;
	}

	@Override
	public ItemStack[] getAdditionalStacks() {
		ArrayList<ItemStack> drops = upgrades.getDrops();
		if (drops == null || drops.isEmpty()) {
			return new ItemStack[] { null };
		}
		ItemStack[] toDrop = new ItemStack[drops.size()];
		int pos = 0;
		for (ItemStack drop : drops) {
			if (drop != null) {
				toDrop[pos] = drop;
			}
			pos++;
		}
		return toDrop;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerAnalysingChamber(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiAnalysingChamber(player.inventory, this);
	}

	@Override
	public UpgradeInventory getUpgradeInventory() {
		return upgrades;
	}

}
