package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.client.gui.misc.GuiModuleWorkstation;
import sonar.calculator.mod.common.containers.ContainerModuleWorkstation;
import sonar.calculator.mod.common.item.calculators.FlawlessCalculator;
import sonar.calculator.mod.common.item.calculators.ModuleItemRegistry;
import sonar.calculator.mod.common.item.calculators.modules.EmptyModule;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileEntityModuleWorkstation extends TileEntityInventory implements IFlexibleGui {

	public boolean updateCalc;
	public boolean newCalc;

	public TileEntityModuleWorkstation() {
		super.inv.setSize(1 + FlawlessCalculator.moduleCapacity);
		super.inv.slotLimit = 1;
		super.inv.getInsertFilters().put((SLOT, STACK, FACE) -> 0 <= SLOT && SLOT < FlawlessCalculator.moduleCapacity ? isModule(STACK) : null, EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(FlawlessCalculator.moduleCapacity, s -> s.getItem() instanceof IFlawlessCalculator), EnumFilterType.INTERNAL);
	}

	@Override
	public boolean checkDrop(int slot, @Nonnull ItemStack stack){
		return slot == FlawlessCalculator.moduleCapacity;
	}

	@Override
	public void onInventoryContentsChanged(int slot){
		super.onInventoryContentsChanged(slot);
		if (slot != FlawlessCalculator.moduleCapacity) {
			updateCalc = true;
		}else{
			ItemStack stack = this.getStackInSlot(slot);
			if (stack.isEmpty()) {
				for(int i = 0; i < FlawlessCalculator.moduleCapacity; i++){
					slots().set(i, ItemStack.EMPTY);
				}
			} else {
				newCalc = true;
			}
		}
	}

	@Override
	public void update() {
		if (isClient() || !hasFlawlessCalculator()) {
			return;
		}
		super.update();
		ItemStack stack = slots().get(FlawlessCalculator.moduleCapacity);
		IFlawlessCalculator calc = (IFlawlessCalculator) stack.getItem();
		if (newCalc) {
			ArrayList<IModule> modules = calc.getModules(stack);
			int i = 0;
			for (IModule module : modules) {
				Item item = ModuleItemRegistry.instance().getValue(module.getName());
				if (item != null) {
					ItemStack moduleStack = new ItemStack(item, 1);
					moduleStack.setTagCompound(calc.getModuleTag(stack, i));
					if(!slots().get(i).isEmpty()){
						InventoryHelper.spawnItemStack(world, getPos().getX(), getPos().getY(), getPos().getZ(), slots().get(i));
					}
					slots().set(i, moduleStack);
				}
				i++;
			}
			newCalc = false;
			updateCalc = false;
		} else if (updateCalc) {
			ArrayList<IModule> modules = new ArrayList<>();
			for (int i = 0; i < FlawlessCalculator.moduleCapacity; i++) {
				ItemStack target = slots().get(i);
				NBTTagCompound tag = new NBTTagCompound();
				IModule module = !target.isEmpty() ? Calculator.modules.getObject(ModuleItemRegistry.instance().getKey(target.getItem())) : EmptyModule.EMPTY;
				if (module == null) {
					module = EmptyModule.EMPTY;
				} else if (!target.isEmpty()) {
					tag = target.getTagCompound();
				}
				calc.addModule(stack, tag, module, i);
			}
			updateCalc = false;
		}
	}

	public boolean hasFlawlessCalculator() {
		return slots().get(FlawlessCalculator.moduleCapacity).getItem() instanceof IFlawlessCalculator;
	}

	public boolean isModule(ItemStack stack){
		return !stack.isEmpty() && ModuleItemRegistry.instance().getKey(stack.getItem()) != null;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerModuleWorkstation(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiModuleWorkstation(player.inventory, this);
	}
}
