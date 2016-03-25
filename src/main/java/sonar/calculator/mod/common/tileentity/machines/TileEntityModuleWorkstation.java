package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.client.gui.misc.GuiModuleWorkstation;
import sonar.calculator.mod.common.containers.ContainerModuleWorkstation;
import sonar.calculator.mod.common.item.calculators.FlawlessCalculator;
import sonar.calculator.mod.common.item.calculators.ModuleItemRegistry;
import sonar.calculator.mod.common.item.calculators.modules.EmptyModule;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityModuleWorkstation extends TileEntityInventory implements IGuiTile {

	public boolean updateCalc;
	public boolean newCalc;

	public TileEntityModuleWorkstation() {
		super.inv = new SonarTileInventory(this, 1 + FlawlessCalculator.moduleCapacity) {

			public void setInventorySlotContents(int i, ItemStack itemstack) {
				if (i != 16)
					updateCalc = true;
				else
					newCalc = true;
				super.setInventorySlotContents(i, itemstack);
			}

			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				if (hasFlawlessCalculator() && stack != null) {
					IModule module = Calculator.modules.getRegisteredObject(Calculator.moduleItems.getSecondaryObject(stack.getItem()));
					if (module != null) {
						ItemStack calcStack = slots()[FlawlessCalculator.moduleCapacity];
						IFlawlessCalculator calc = (IFlawlessCalculator) stack.getItem();
						if (calc.canAddModule(calcStack, module, slot)) {
							return true;
						}
					}
				}
				return true;
			}

			public ItemStack decrStackSize(int slot, int var2) {
				updateCalc = true;
				ItemStack toReturn = super.decrStackSize(slot, var2);
				if (slot == 16) {
					this.clear();
				}
				return toReturn;
			}

			public ItemStack removeStackFromSlot(int i) {
				updateCalc = true;
				ItemStack toReturn = super.removeStackFromSlot(i);
				if (i == 16) {
					this.clear();
				}
				return toReturn;
			}

			public void closeInventory(EntityPlayer player) {
				updateCalc = true;
			}

			public void clear() {
				updateCalc = true;
				super.clear();
			}

			public boolean isUseableByPlayer(EntityPlayer player) {
				// does the player own this FLAWLESS CALCULATOR?
				return true;
			}

		};
	}

	public void update() {
		super.update();
		if (isClient() || !hasFlawlessCalculator()) {
			return;
		}
		ItemStack stack = slots()[FlawlessCalculator.moduleCapacity];
		IFlawlessCalculator calc = (IFlawlessCalculator) stack.getItem();
		if (newCalc) {
			ArrayList<IModule> modules = calc.getModules(stack);
			int i = 0;
			for (IModule module : modules) {
				Item item = Calculator.moduleItems.getPrimaryObject(module.getName());
				if (item != null) {
					ItemStack moduleStack = new ItemStack(item, 1);
					moduleStack.setTagCompound(calc.getModuleTag(stack, i));
					slots()[i] = moduleStack;
				}
				i++;
			}
			newCalc = false;
			updateCalc = false;
		} else if (updateCalc) {
			ArrayList<IModule> modules = new ArrayList();
			for (int i = 0; i < FlawlessCalculator.moduleCapacity; i++) {
				ItemStack target = slots()[i];
				if (target != null) {
					IModule module = Calculator.modules.getRegisteredObject(Calculator.moduleItems.getSecondaryObject(target.getItem()));
					if (module != null) {
						calc.addModule(stack, target.getTagCompound(), module, i);
					} else {
						calc.addModule(stack, new NBTTagCompound(), EmptyModule.EMPTY, i);
					}
				} else {
					calc.addModule(stack, new NBTTagCompound(), EmptyModule.EMPTY, i);
				}
			}
			updateCalc = false;
		}
	}

	public boolean hasFlawlessCalculator() {
		return slots()[FlawlessCalculator.moduleCapacity] != null && slots()[FlawlessCalculator.moduleCapacity].getItem() instanceof IFlawlessCalculator;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerModuleWorkstation(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiModuleWorkstation(player.inventory, this);
	}

}
