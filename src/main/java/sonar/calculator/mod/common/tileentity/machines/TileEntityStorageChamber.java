package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.machines.GuiStorageChamber;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.tileentity.TileEntityLargeInventory;
import sonar.core.handlers.inventories.SonarLargeInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.helpers.NBTHelper.SyncType;

public class TileEntityStorageChamber extends TileEntityLargeInventory implements IFlexibleGui{

	public CircuitType circuitType = CircuitType.None;

	@Override
	public void update() {
		super.update();
	}

	public TileEntityStorageChamber() {
		super(14, 1024);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> SLOT == STACK.getMetadata(), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> {
			CircuitType type = getCircuitType(STACK);
			if(type != null){
				return circuitType == CircuitType.None || circuitType == type;
			}
			return false;
		}, EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.default_external_extract_result = true;
		syncList.addParts(inv);
	}

	@Override
	public void onInventoryContentsChanged(int slot){
		CircuitType type = CircuitType.None;
		for(SonarLargeInventory.InventoryLargeSlot largeSlot : inv.slots){
			CircuitType slotType = getCircuitType(largeSlot.getLargeStack().getItemStack());
			if(slotType != null && slotType != CircuitType.None){
				type = slotType;
				break;
			}
		}
		circuitType = type;
	}

	@Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			circuitType = CircuitType.values()[nbt.getInteger("type")];
			if (circuitType == null) {
				circuitType = CircuitType.None;
			}
		}
		if (type.isType(SyncType.DROP)) {
			inv.readData(nbt, SyncType.SAVE);
			circuitType = CircuitType.values()[nbt.getInteger("type")];
			if (circuitType == null) {
				circuitType = CircuitType.None;
			}
		}
	}

	@Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			nbt.setInteger("type", circuitType.ordinal());
		}
		if (type.isType(SyncType.DROP)) {
			inv.writeData(nbt, SyncType.SAVE);
			nbt.setInteger("type", circuitType.ordinal());
		}
		return nbt;
	}

	public static CircuitType getCircuitType(ItemStack stack) {
		if (stack.getItem() == Calculator.circuitBoard && stack.getItem() instanceof IStability) {
			IStability stability = (IStability) stack.getItem();
			if (stability.getStability(stack) && stack.hasTagCompound()) {
				if (stack.getTagCompound().getBoolean("Analysed")) {
					return CircuitType.Stable;
				}
			} else if (!stack.hasTagCompound()) {
				return CircuitType.Analysed;
			} else if (stack.getTagCompound().getBoolean("Analysed")) {
				return CircuitType.Analysed;
			}
		} else if (stack.getItem() == Calculator.circuitDamaged) {
			return CircuitType.Damaged;
		} else if (stack.getItem() == Calculator.circuitDirty) {
			return CircuitType.Dirty;
		}
		return null;
	}

	public enum CircuitType {
		Analysed, Stable, Damaged, Dirty, None;

		public boolean isProcessed() {
			return this == Analysed || this == Stable;
		}

		public boolean isStable() {
			return this == Stable;
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerStorageChamber(player, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiStorageChamber(player, this);
	}
}
