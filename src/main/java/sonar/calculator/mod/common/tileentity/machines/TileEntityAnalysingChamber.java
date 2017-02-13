package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
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
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.AnalysingChamberRecipes;
import sonar.core.api.SonarAPI;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.upgrades.IUpgradableTile;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.IAdditionalInventory;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.upgrades.UpgradeInventory;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.MachineSideConfig;

public class TileEntityAnalysingChamber extends TileEntityEnergySidedInventory implements IUpgradableTile, IAdditionalInventory, IGuiTile {

	public SyncTagType.INT stable = new SyncTagType.INT(0);
	public SyncTagType.INT analysed = new SyncTagType.INT(2);
	public int maxTransfer = 2000;
	public int transferTicks = 0;
	public final int transferTime = 20;
	public final int[] itemSlots = new int[] { 2, 3, 4, 5, 6, 7 };

	public UpgradeInventory upgrades = new UpgradeInventory(3, 1, "VOID", "TRANSFER");

	public TileEntityAnalysingChamber() {
		super.input = new int[] { 0 };
		super.output = new int[] { 2, 3, 4, 5, 6, 7 };
		super.storage.setCapacity(100000).setMaxTransfer(64000);
		super.inv = new SonarInventory(this, 8) {
			public void setInventorySlotContents(int i, ItemStack itemstack) {
				super.setInventorySlotContents(i, itemstack);
				if (i == 0) {
					markBlockForUpdate();
				}
			}
		};
		super.maxTransfer = 2000;
		super.energyMode = EnergyMode.SEND;
		syncList.addParts(stable, analysed, inv);
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
			SonarAPI.getItemHelper().transferItems(this, SonarHelper.getAdjacentTileEntity(this, side), side, side.getOpposite(), null);
		}
		ArrayList<EnumFacing> inputs = sides.getSidesWithConfig(MachineSideConfig.INPUT);
		if (!inputs.isEmpty()) {
			ArrayList<BlockCoords> chambers = SonarHelper.getConnectedBlocks(Calculator.storageChamber, inputs, worldObj, pos, 256);
			for (BlockCoords chamber : chambers) {
				TileEntity tile = chamber.getTileEntity(worldObj);
				if (tile != null && tile instanceof TileEntityStorageChamber) {
					SonarAPI.getItemHelper().transferItems(this, tile, inputs.get(0), inputs.get(0).getOpposite(), null);
					if (this.slots()[0] == null) {
						return;
					}
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
				for (int i = 1; i < 7; i++) {
					if (i > 2 || upgrades.getUpgradesInstalled("VOID") == 0) {
						add(RecipeHelperV2.getItemStackFromList(AnalysingChamberRecipes.instance().getOutputs(null, new Object[] { i, tag.getInteger("Item" + i) }), 0), i + 1);
					}
					tag.removeTag("Item" + i);
				}
				tag.removeTag("Energy");
				tag.setBoolean("Analysed", true);
				analysed.setObject(1);
			}
		}
	}

	private void add(ItemStack item, int slotID) {
		if (item != null) {
			slots()[slotID] = new ItemStack(item.getItem(), 1, item.getItemDamage());
		}
	}

	private boolean canAnalyse() {
		if (slots()[0] != null && slots()[0].getItem() == Calculator.circuitBoard) {
			for (int slot : itemSlots) {
				if (slots()[slot] != null) {
					return false;
				}
			}
			return true;
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
	public EnergyMode getModeForSide(EnumFacing side) {
		if (side == null) {
			return EnergyMode.SEND_RECIEVE;
		}
		if (side == EnumFacing.DOWN) {
			return EnergyMode.SEND;
		}
		return EnergyMode.BLOCKED;
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
		if (stack.getItem() instanceof CircuitBoard) {
			NBTTagCompound tag = slots()[slot].getTagCompound();
			if (!tag.getBoolean("Analysed")) {
				return false;
			}
		}
		return slot != 1;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.readData(nbt, type);

	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.writeData(nbt, type);
		return nbt;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
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
