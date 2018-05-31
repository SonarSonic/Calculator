package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiAtomicMultiplier;
import sonar.calculator.mod.common.containers.ContainerAtomicMultiplier;
import sonar.calculator.mod.utils.AtomicMultiplierBlacklist;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.filters.SlotFilter;
import sonar.core.inventory.handling.filters.SlotHelper;
import sonar.core.network.sync.SyncTagType;

import java.util.List;

public class TileEntityAtomicMultiplier extends TileEntityEnergyInventory implements IProcessMachine, IFlexibleGui {

	public static final SlotFilter input_slot = new SlotFilter(null, new int[] { 0 }, EnumFacing.UP);
	public static final SlotFilter circuit_slots = new SlotFilter(null, new int[] { 1, 2, 3, 4, 5, 6, 7 }, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST);

	public SyncTagType.INT cookTime = new SyncTagType.INT(0);
	public SyncTagType.INT active = new SyncTagType.INT(1);

	public TileEntityAtomicMultiplier() {
		super.storage.setCapacity(CalculatorConfig.ATOMIC_MULTIPLIER_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.ATOMIC_MULTIPLIER_TRANSFER_RATE);
		super.inv.setSize(10);
		super.inv.getInsertFilters().put(input_slot, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put(circuit_slots, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> circuit_slots.checkSlot(SLOT) ? STACK.getItem() == Calculator.circuitBoard : null, EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> input_slot.checkSlot(SLOT) ? (STACK.getMaxStackSize() >= 4 && isAllowed(STACK)) : null, EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.dischargeSlot(9), EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.blockSlot(8), EnumFilterType.INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE) -> SLOT == 8, EnumFilterType.EXTERNAL);
		super.energyMode = EnergyMode.RECIEVE;
		super.CHARGING_RATE = CalculatorConfig.ATOMIC_MULTIPLIER_TRANSFER_RATE;
		syncList.addParts(cookTime, active);
	}

	@Override
	public void update() {
		super.update();
		discharge(9);
		if (this.cookTime.getObject() > 0) {
			this.active.setObject(1);
			this.cookTime.increaseBy(1);
			int energy = CalculatorConfig.ATOMIC_MULTIPLIER_USAGE / CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
			this.storage.modifyEnergyStored(-energy);
		}
		if (this.canCook()) {
			if (!this.world.isRemote) {
				if (cookTime.getObject() == 0) {
					this.cookTime.increaseBy(1);
				}
			}
			if (this.cookTime.getObject() >= CalculatorConfig.ATOMIC_MULTIPLIER_SPEED) {

				this.cookTime.setObject(0);
				this.cookItem();
				this.active.setObject(0);

				int energy = CalculatorConfig.ATOMIC_MULTIPLIER_USAGE / CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
				this.storage.modifyEnergyStored(-energy);
				markBlockForUpdate();
			}
		} else {
			if (this.cookTime.getObject() != 0 || this.active.getObject() != 0) {
				this.cookTime.setObject(0);
				this.active.setObject(0);
				markBlockForUpdate();
			}
		}

		this.markDirty();
	}

	public boolean canCook() {
		if (this.storage.getEnergyStored() == 0) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			if (slots().get(i).isEmpty()) {
				return false;
			}
		}
		if (!isAllowed(slots().get(0))) {
			return false;
		}
		ItemStack output = slots().get(8);
		if (!output.isEmpty()) {
			if (output.getCount() + 4 > 64) {
				return false;
			}
			if (!slots().get(0).isItemEqual(output)) {
				return false;
			}
		}

		if (cookTime.getObject() == 0) {
			if (this.storage.getEnergyStored() < CalculatorConfig.ATOMIC_MULTIPLIER_USAGE) {
				return false;
			}
		}
		if (!(slots().get(0).getMaxStackSize() >= 4)) {
			return false;
		}

		for (int i = 1; i < 8; i++) {
			if (slots().get(i).getItem() != Calculator.circuitBoard) {
				return false;
			}
		}

		if (cookTime.getObject() >= CalculatorConfig.ATOMIC_MULTIPLIER_SPEED) {
			return true;
		}
		return true;
	}

	public static boolean isAllowed(ItemStack stack) {
		return AtomicMultiplierBlacklist.blacklist().isAllowed(stack.getItem());
	}

	private void cookItem() {
		ItemStack itemstack = new ItemStack(slots().get(0).getItem(), 4, slots().get(0).getItemDamage());
		ItemStack output = slots().get(8);
		if (output.isEmpty()) {
			slots().set(8, itemstack);
		} else if (output.isItemEqual(itemstack)) {
			output.grow(4);
		}

		for (int i = 0; i < 8; i++) {
			slots().get(i).shrink(1);
		}
	}


    @Override
	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			markBlockForUpdate();
		}
		return true;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		super.getWailaInfo(currenttip, state);
		if (cookTime.getObject() > 0) {
			String active = FontHelper.translate("locator.state") + ": " + FontHelper.translate("locator.active");
			currenttip.add(active);
		} else {
			String idle = FontHelper.translate("locator.state") + ": " + FontHelper.translate("locator.idle");
			currenttip.add(idle);
		}
		return currenttip;
	}

	@Override
	public int getCurrentProcessTime() {
		return cookTime.getObject();
	}

	@Override
	public int getProcessTime() {
		return CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
	}

	@Override
	public double getEnergyUsage() {
        return CalculatorConfig.ATOMIC_MULTIPLIER_USAGE / CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerAtomicMultiplier(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiAtomicMultiplier(player.inventory, this);
	}

	@Override
	public int getBaseProcessTime() {
		return CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
	}
}
