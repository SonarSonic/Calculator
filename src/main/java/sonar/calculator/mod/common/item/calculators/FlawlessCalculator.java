package sonar.calculator.mod.common.item.calculators;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.api.items.IModuleProvider;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.calculator.mod.api.modules.IModuleEnergy;
import sonar.calculator.mod.api.modules.IModuleInventory;
import sonar.calculator.mod.api.modules.IModuleUpdate;
import sonar.calculator.mod.common.item.calculators.modules.EmptyModule;
import sonar.calculator.mod.common.item.calculators.modules.EnergyModule;
import sonar.calculator.mod.common.item.calculators.modules.GuiModule;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.item.InventoryItem;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.IGuiItem;
import cofh.api.energy.IEnergyContainerItem;

public class FlawlessCalculator extends SonarItem implements IItemInventory, IModuleProvider, IEnergyContainerItem, IFlawlessCalculator, IGuiItem {
	public final String invTag = "inv";
	public final String emptyModule = "";
	public static final int moduleCapacity = 16;

	public FlawlessCalculator() {
		setMaxStackSize(1);
	}

	public NBTTagCompound getDefaultTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("slot" + 0, GuiModule.flawless.getName());
		tag.setString("slot" + 1, GuiModule.dynamic.getName());
		tag.setString("slot" + 2, GuiModule.crafting.getName());
		tag.setString("slot" + 3, GuiModule.storage.getName());
		tag.setString("slot" + 4, new EnergyModule().getName());
		return tag;
	}

	public int getCurrentSlot(ItemStack stack) {
		NBTTagCompound tag = getTagCompound(stack);
		return tag.getInteger("slot");
	}

	public IModule getModuleInSlot(ItemStack stack, int slot) {
		NBTTagCompound tag = getTagCompound(stack);
		try {
			String moduleName = tag.getString("slot" + slot);
			if (!moduleName.isEmpty() && !moduleName.equals(emptyModule)) {
				IModule module = Calculator.modules.getRegisteredObject(moduleName);
				if (module != null) {
					return module;
				}
			}
		} catch (Exception exception) {
			Calculator.logger.error("Flawless Calculator: " + "encountered a null error retrieving a module");
		}
		return EmptyModule.EMPTY;
	}

	public IModule getCurrentModule(ItemStack stack) {
		return getModuleInSlot(stack, this.getCurrentSlot(stack));
	}

	@Override
	public ArrayList<IModule> getModules(ItemStack stack) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < moduleCapacity; i++) {
			list.add(i, (getModuleInSlot(stack, i)));
		}
		return list;
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		IModule module = this.getCurrentModule(stack);
		int slot = this.getCurrentSlot(stack);
		if (!(module instanceof IModuleInventory)) {
			return null;
		}
		return ((IModuleInventory) module).getInventory(stack, "" + slot, false);
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		ArrayList<IModule> modules = getModules(stack);
		int slot = 0;
		for (IModule module : modules) {
			if (module instanceof IModuleUpdate) {
				NBTTagCompound tag = stack.getSubCompound("" + slot, false);
				if (tag == null) {
					tag = new NBTTagCompound();
				}
				((IModuleUpdate) module).onUpdate(stack, tag, world, entity);
				if (!tag.hasNoTags())
					stack.setTagInfo("" + slot, tag);
			}
			slot++;
		}
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		// if (!world.isRemote) {
		// this.addModule(stack, new WarpModule(), 3);
		NBTTagCompound tag = getTagCompound(stack);
		if (!player.isSneaking()) {
			int slot = this.getCurrentSlot(stack);
			IModule module = this.getCurrentModule(stack);
			tag = stack.getSubCompound("" + slot, false);
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			if (module instanceof IModuleClickable) {
				((IModuleClickable) module).onModuleActivated(stack, tag, world, player);

			} else if (module instanceof IModuleEnergy) {
				IModuleEnergy energy = (IModuleEnergy) module;
				FontHelper.sendMessage("Energy Module: " + energy.getEnergyStored(stack, tag) + " RF", world, player);
			}
			if (!tag.hasNoTags() && !world.isRemote)
				stack.setTagInfo("" + slot, tag);
		} else if (!world.isRemote) {
			player.openGui(Calculator.instance, CalculatorGui.ModuleSelect, world, -1000, -1000, -1000);
			/*
			int slot = this.getCurrentSlot(stack);
			slot++;
			if (!(slot < moduleCapacity)) {
				slot = 0;
			}
			tag.setInteger("slot", slot);
			stack.setTagCompound(tag);

			IModule module = this.getCurrentModule(stack);
			FontHelper.sendMessage("Module " + " : " + module.getClientName(), world, player);
			*/
		}
		// }
		return stack;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		IModule module = this.getCurrentModule(stack);
		int slot = this.getCurrentSlot(stack);
		if (module instanceof IModuleClickable) {
			NBTTagCompound tag = stack.getSubCompound("" + slot, false);
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			boolean toReturn = ((IModuleClickable) module).onBlockClicked(stack, tag, player, world, pos, new BlockInteraction(side.getIndex(), hitx, hity, hitz, BlockInteractionType.RIGHT));
			if (!tag.hasNoTags())
				stack.setTagInfo("" + slot, tag);
			return toReturn;
		}
		return false;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		IModule current = this.getCurrentModule(stack);
		list.add("Current Module: " + current.getClientName());

		long energyStored = 0;
		long itemsStored = 0;
		int slot = 0;

		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
				energyStored += ((IModuleEnergy) module).getEnergyStored(stack, stack.getSubCompound("" + slot, true));
			} else if (module instanceof IModuleInventory) {
				itemsStored += ((IModuleInventory) module).getInventory(stack, "" + slot, false).getItemsStored(stack);
			}
			slot++;
		}
		list.add(FontHelper.translate("energy.stored") + ": " + energyStored + " RF");
		if (itemsStored != 0)
			list.add(FontHelper.translate("calc.storedstacks") + ": " + itemsStored);
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int received = maxReceive;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(container).clone()) {
			if (module instanceof IModuleEnergy) {
				received -= ((IModuleEnergy) module).receiveEnergy(container, container.getSubCompound("" + slot, true), maxReceive, simulate);
			}
			slot++;
		}
		return maxReceive - received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int extracted = maxReceive;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(container).clone()) {
			if (module instanceof IModuleEnergy) {
				extracted -= ((IModuleEnergy) module).extractEnergy(container, container.getSubCompound("" + slot, true), maxReceive, simulate);
			}
			slot++;
		}
		return maxReceive - extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		int stored = 0;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(container).clone()) {
			if (module instanceof IModuleEnergy) {
				stored += ((IModuleEnergy) module).getEnergyStored(container, container.getSubCompound("" + slot, true));
			}
			slot++;
		}
		return stored;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		int stored = 0;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(container).clone()) {
			if (module instanceof IModuleEnergy) {
				stored += ((IModuleEnergy) module).getMaxEnergyStored(container, container.getSubCompound("" + slot, true));
			}
			slot++;
		}
		return stored;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, EntityPlayer player) {
		// if (module instanceof IModuleInventory && itemstack != null && player instanceof EntityPlayerMP && player.openContainer instanceof ContainerCraftInventory) {
		// player.closeScreen();
		// }
		return super.onDroppedByPlayer(itemstack, player);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// return module instanceof IModuleInventory ? 1 : super.getMaxItemUseDuration(stack);
		return 1;
	}

	@Override
	public ItemStack removeModule(ItemStack stack, int slot) {
		IModule module = getModuleInSlot(stack, slot);
		NBTTagCompound tag = stack.getSubCompound("" + slot, false);
		stack.setTagInfo("" + slot, new NBTTagCompound());
		stack.getTagCompound().setString("slot" + slot, "");
		ItemStack toReturn = new ItemStack(Calculator.moduleItems.getPrimaryObject(module.getName()), 1);
		if (tag != null && !tag.hasNoTags())
			toReturn.setTagCompound(tag);
		return toReturn;
	}

	@Override
	public void addModule(ItemStack stack, NBTTagCompound moduleTag, IModule module, int slot) {
		NBTTagCompound tag = getTagCompound(stack);
		if (module.getName().equals("Empty")) {
			stack.getTagCompound().removeTag("slot" + slot);
		} else {
			tag.setString("slot" + slot, module.getName());
		}
		stack.setTagCompound(tag);
		if (moduleTag != null && !moduleTag.hasNoTags()) {
			stack.setTagInfo("" + slot, moduleTag);
		} else if (stack.getTagCompound().hasKey("" + slot, 10)) {
			stack.getTagCompound().removeTag("" + slot);
		}
	}

	@Override
	public boolean canRemoveModule(ItemStack stack, int slot) {
		IModule module = getModuleInSlot(stack, slot);
		if (module.getName().equals("Empty")) {
			return false;
		} else {
			Item item = Calculator.moduleItems.getPrimaryObject(module.getName());
			return item != null;
		}
	}

	@Override
	public boolean canAddModule(ItemStack stack, IModule module, int slot) {
		IModule current = getModuleInSlot(stack, slot);
		return module.getName().equals("Empty");
	}

	@Override
	public NBTTagCompound getModuleTag(ItemStack stack, int slot) {
		return stack.getSubCompound("" + slot, true);
	}

	@Override
	public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) getCurrentModule(stack)).getGuiContainer(player, stack);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) getCurrentModule(stack)).getGuiScreen(player, stack);
	}
	
	public boolean hasEffect(ItemStack stack){
		return true;
	}
}
