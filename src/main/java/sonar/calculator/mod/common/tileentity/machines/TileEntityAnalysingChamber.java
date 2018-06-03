package sonar.calculator.mod.common.tileentity.machines;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.common.containers.ContainerAnalysingChamber;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.AnalysingChamberRecipes;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.inventories.IAdditionalInventory;
import sonar.core.api.upgrades.IUpgradableTile;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.ItemTransferHelper;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.network.sync.SyncTagType;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.upgrades.UpgradeInventory;
import sonar.core.utils.MachineSideConfig;

import java.util.ArrayList;
import java.util.List;

public class TileEntityAnalysingChamber extends TileEntityEnergySidedInventory implements IUpgradableTile, IAdditionalInventory, IFlexibleGui {

	public SyncTagType.INT stable = new SyncTagType.INT(0);
	public SyncTagType.INT analysed = new SyncTagType.INT(2);
	public final int[] itemSlots = new int[] { 2, 3, 4, 5, 6, 7 };

	public UpgradeInventory upgrades = new UpgradeInventory(3, 1, "VOID", "TRANSFER");

	public TileEntityAnalysingChamber() {
		super.sides.input = new int[] { 0 };
		super.sides.output = new int[] { 2, 3, 4, 5, 6, 7 };
		super.storage.setCapacity(CalculatorConfig.ANALYSING_CHAMBER_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.ANALYSING_CHAMBER_TRANSFER_RATE);
		super.inv.setSize(8);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> SLOT != 1 ? SLOT == 0 && CircuitBoard.getState(STACK) == CircuitBoard.CircuitState.NOT_ANALYSED : null, EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.chargeSlot(1), EnumFilterType.INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE)-> SLOT == 0 ? CircuitBoard.getState(inv.getStackInSlot(SLOT)) != CircuitBoard.CircuitState.NOT_ANALYSED : SLOT != 1, EnumFilterType.EXTERNAL);
		super.energyMode = EnergyMode.SEND;
		syncList.addParts(stable, analysed);
	}

	@Override
	public void update() {
		super.update();
		if (this.world.isRemote) {
			return;
		}
		if (upgrades.getUpgradesInstalled("TRANSFER") > 0) {
			transferItems();
		}
		if (analysed.getObject() == 1 && this.slots().get(0).isEmpty()) {
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

	@Override
	public void onInventoryContentsChanged(int slot){
		super.onInventoryContentsChanged(slot);
		if (slot == 0) {
			markBlockForUpdate();
		}
	}

	public void transferItems() {
		ArrayList<EnumFacing> outputs = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		for (EnumFacing side : outputs) {
			IItemHandler handler = ItemTransferHelper.getItemHandler(world, getPos().offset(side), side);
			if(handler != null) {
				ItemTransferHelper.doSimpleTransfer(Lists.newArrayList(this.inv.getItemHandler(side)), Lists.newArrayList(handler), IS -> !IS.isEmpty(), 4);
			}
		}
		ArrayList<EnumFacing> inputs = sides.getSidesWithConfig(MachineSideConfig.INPUT);
		if (!inputs.isEmpty()) {
			ArrayList<BlockCoords> chambers = SonarHelper.getConnectedBlocks(Calculator.storageChamber, inputs, world, pos, 256);
			List<IItemHandler> handlers = new ArrayList<>();
			for (BlockCoords chamber : chambers) {
				IItemHandler handler = ItemTransferHelper.getItemHandler(world, chamber.getBlockPos(), EnumFacing.DOWN);
				if(!ItemTransferHelper.isInvalidItemHandler(handler)){
					handlers.add(handler);
				}
			}
			ItemTransferHelper.doSimpleTransfer(Lists.newArrayList(inv()), handlers, IS -> true, 32);
		}
	}

	private void analyse(int slot) {
		if (slots().get(slot).hasTagCompound()) {
			NBTTagCompound tag = slots().get(slot).getTagCompound();
			if (!tag.getBoolean("Analysed")) {
				int storedEnergy = itemEnergy(slots().get(slot).getTagCompound().getInteger("Energy"));
				this.storage.receiveEnergy(storedEnergy, false);
				for (int i = 1; i < 7; i++) {
					if (i > 2 || upgrades.getUpgradesInstalled("VOID") == 0) {
                        add(RecipeHelperV2.getItemStackFromList(AnalysingChamberRecipes.instance().getOutputs(null, i, tag.getInteger("Item" + i)), 0), i + 1);
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
		slots().set(slotID, new ItemStack(item.getItem(), 1, item.getItemDamage()));
	}

	private boolean canAnalyse() {
		if (slots().get(0).getItem() == Calculator.circuitBoard) {
			for (int slot : itemSlots) {
				if (!slots().get(slot).isEmpty()) {
					return false;
				}
			}
			return true;
		}
		if (slots().get(0).isEmpty()) {
			stable.setObject(0);
			return false;
		}
		return false;
	}

	public static List<Integer> energyValues = Lists.newArrayList(0,1000,500,250,10000,5000, 100000, 100, 175, 400, 750, 800);

	public static int itemEnergy(int n) {
		return n < energyValues.size() ? energyValues.get(n) : 0;
	}

	private int stable(int par) {
		ItemStack stableStack = slots().get(par);
		if (stableStack.hasTagCompound() && stableStack.getItem() instanceof IStability) {
			IStability item = (IStability) stableStack.getItem();
			boolean stable = item.getStability(stableStack);
			if (!stable) {
				item.onFalse(stableStack);
			}
			return stable ? 1 : 0;
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
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.readData(nbt, type);
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE))
			upgrades.writeData(nbt, type);
		return nbt;
	}

    @Override
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

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerAnalysingChamber(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiAnalysingChamber(player.inventory, this);
	}

	@Override
	public UpgradeInventory getUpgradeInventory() {
		return upgrades;
	}
}
