package sonar.calculator.mod.common.item.calculators;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
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
import sonar.core.api.energy.ISonarEnergyItem;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.item.InventoryItem;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.IGuiItem;


@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyContainerItem", modid = "redstoneflux")})
public class FlawlessCalculator extends SonarItem implements IItemInventory, IModuleProvider, ISonarEnergyItem, IEnergyContainerItem, IFlawlessCalculator, IGuiItem {
	public final String invTag = "inv";
	public final String emptyModule = "";
	public static final int moduleCapacity = 16;

	public FlawlessCalculator() {
		setMaxStackSize(1);
	}

    @Override
	public NBTTagCompound getDefaultTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("slot" + 0, GuiModule.flawless.getName());
		tag.setString("slot" + 1, GuiModule.dynamic.getName());
		tag.setString("slot" + 2, GuiModule.crafting.getName());
		tag.setString("slot" + 3, GuiModule.storage.getName());
		tag.setString("slot" + 4, new EnergyModule().getName());
		return tag;
	}

    @Override
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

    @Override
	public IModule getCurrentModule(ItemStack stack) {
		return getModuleInSlot(stack, getCurrentSlot(stack));
	}

	@Override
	public ArrayList<IModule> getModules(ItemStack stack) {
        ArrayList<IModule> list = new ArrayList<>();
		for (int i = 0; i < moduleCapacity; i++) {
            list.add(i, getModuleInSlot(stack, i));
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
        return ((IModuleInventory) module).getInventory(stack, String.valueOf(slot), false);
	}

    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		ArrayList<IModule> modules = getModules(stack);
		int slot = 0;
		for (IModule module : modules) {
			if (module instanceof IModuleUpdate) {
                NBTTagCompound tag = stack.getSubCompound(String.valueOf(slot));
				if (tag == null) {
					tag = new NBTTagCompound();
				}
				((IModuleUpdate) module).onUpdate(stack, tag, world, entity);
				if (!tag.hasNoTags())
                    stack.setTagInfo(String.valueOf(slot), tag);
			}
			slot++;
		}
	}

    @Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound tag = getTagCompound(stack);
		if (!player.isSneaking()) {
			int slot = this.getCurrentSlot(stack);
			IModule module = this.getCurrentModule(stack);
            tag = stack.getSubCompound(String.valueOf(slot));
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
                stack.setTagInfo(String.valueOf(slot), tag);
		} else if (!world.isRemote) {
			player.openGui(Calculator.instance, CalculatorGui.ModuleSelect, world, -1000, -1000, -1000);
			/* int slot = this.getCurrentSlot(stack); slot++; if (!(slot < moduleCapacity)) { slot = 0; } tag.setInteger("slot", slot); stack.setTagCompound(tag);
			 * 
			 * IModule module = this.getCurrentModule(stack); FontHelper.sendMessage("Module " + " : " + module.getClientName(), world, player); */
		}
		// }
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

    @Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		IModule module = this.getCurrentModule(stack);
		int slot = this.getCurrentSlot(stack);
		if (module instanceof IModuleClickable) {
            NBTTagCompound tag = stack.getSubCompound(String.valueOf(slot));
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			boolean toReturn = ((IModuleClickable) module).onBlockClicked(stack, tag, player, world, pos, new BlockInteraction(side.getIndex(), hitX, hitY, hitZ, BlockInteractionType.RIGHT));
			if (!tag.hasNoTags())
                stack.setTagInfo(String.valueOf(slot), tag);
			return toReturn ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		}
		return EnumActionResult.PASS;
	}

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);
		IModule current = this.getCurrentModule(stack);
		list.add("Current Module: " + current.getClientName());

		long energyStored = 0;
		long itemsStored = 0;
		int slot = 0;

		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
                energyStored += ((IModuleEnergy) module).getEnergyStored(stack, stack.getOrCreateSubCompound(String.valueOf(slot)));
			} else if (module instanceof IModuleInventory) {
                itemsStored += ((IModuleInventory) module).getInventory(stack, String.valueOf(slot), false).getItemsStored(stack);
			}
			slot++;
		}
		list.add(FontHelper.translate("energy.stored") + ": " + energyStored + " RF");
		if (itemsStored != 0)
			list.add(FontHelper.translate("calc.storedstacks") + ": " + itemsStored);
	}

	@Override
	public long addEnergy(ItemStack stack, long maxReceive, ActionType action) {
		long received = maxReceive;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
                received -= ((IModuleEnergy) module).receiveEnergy(stack, stack.getOrCreateSubCompound(String.valueOf(slot)), maxReceive, action);
			}
			slot++;
		}
		return maxReceive - received;
	}

	@Override
	public long removeEnergy(ItemStack stack, long maxExtract, ActionType action) {
		long extracted = maxExtract;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
                extracted -= ((IModuleEnergy) module).extractEnergy(stack, stack.getOrCreateSubCompound(String.valueOf(slot)), maxExtract, action);
			}
			slot++;
		}
		return maxExtract - extracted;
	}

	@Override
	public long getEnergyLevel(ItemStack stack) {
		long stored = 0;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
                stored += ((IModuleEnergy) module).getEnergyStored(stack, stack.getOrCreateSubCompound(String.valueOf(slot)));
			}
			slot++;
		}
		return stored;
	}

	@Override
	public long getFullCapacity(ItemStack stack) {
		long stored = 0;
		int slot = 0;
		for (IModule module : (ArrayList<IModule>) getModules(stack).clone()) {
			if (module instanceof IModuleEnergy) {
                stored += ((IModuleEnergy) module).getMaxEnergyStored(stack, stack.getOrCreateSubCompound(String.valueOf(slot)));
			}
			slot++;
		}
		return stored;
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return (int) addEnergy(container, maxReceive, ActionType.getTypeForAction(simulate));
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return (int) removeEnergy(container, maxExtract, ActionType.getTypeForAction(simulate));
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int getEnergyStored(ItemStack container) {
		return (int) getEnergyLevel(container);
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int getMaxEnergyStored(ItemStack container) {
		return (int) getFullCapacity(container);
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
        NBTTagCompound tag = stack.getSubCompound(String.valueOf(slot));
        stack.setTagInfo(String.valueOf(slot), new NBTTagCompound());
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
            stack.setTagInfo(String.valueOf(slot), moduleTag);
        } else if (stack.getTagCompound().hasKey(String.valueOf(slot), 10)) {
            stack.getTagCompound().removeTag(String.valueOf(slot));
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
        return stack.getOrCreateSubCompound(String.valueOf(slot));
	}

	@Override
	public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) getCurrentModule(stack)).getGuiContainer(player, stack);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) getCurrentModule(stack)).getGuiScreen(player, stack);
	}

    @Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

    @Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || newStack.getItem() != oldStack.getItem() || newStack.getItemDamage() != oldStack.getItemDamage();
	}
}