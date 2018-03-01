package sonar.calculator.mod.common.item.calculators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IModuleProvider;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.calculator.mod.api.modules.IModuleInventory;
import sonar.calculator.mod.api.modules.IModuleUpdate;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.item.InventoryItem;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.ContainerCraftInventory;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.IGuiItem;

public class SonarModule extends SonarItem implements IItemInventory, IModuleProvider, IGuiItem {
	public IModule module;
	public final String invTag = "inv";

	public SonarModule(IModule module) {
		this.module = module;
		setMaxStackSize(1);
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		if (module instanceof IModuleInventory) {
			return ((IModuleInventory) module).getInventory(stack, invTag, true);
		} else {
			Calculator.logger.error("Module: " + module.getName() + " doesn't contain an Inventory!");
		}
		return null;
	}

    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (module instanceof IModuleUpdate) {
			NBTTagCompound tag = getTagCompound(stack);
			((IModuleUpdate) module).onUpdate(stack, tag, world, entity);
			stack.setTagCompound(tag);
		}
	}

    @Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (module instanceof IModuleClickable) {
			NBTTagCompound tag = getTagCompound(stack);
			((IModuleClickable) module).onModuleActivated(stack, tag, world, player);
			stack.setTagCompound(tag);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

    @Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (module instanceof IModuleClickable) {
			NBTTagCompound tag = getTagCompound(stack);
			boolean toReturn = ((IModuleClickable) module).onBlockClicked(stack, tag, player, world, pos, new BlockInteraction(side.getIndex(), hitX, hitY, hitZ, BlockInteractionType.RIGHT));
			stack.setTagCompound(tag);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
        super.addInformation(stack, player, list, par4);
		if (module instanceof IModuleInventory) {
			int items = ((IModuleInventory) module).getInventory(stack, invTag, true).getItemsStored(stack);
			if (items != 0) {
				list.add(FontHelper.translate("calc.storedstacks") + ": " + items);
			}
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, EntityPlayer player) {
		if (module instanceof IModuleInventory && itemstack != null && player instanceof EntityPlayerMP && player.openContainer instanceof ContainerCraftInventory) {
			player.closeScreen();
		}
		return super.onDroppedByPlayer(itemstack, player);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return module instanceof IModuleInventory ? 1 : super.getMaxItemUseDuration(stack);
	}

	@Override
	public ArrayList<IModule> getModules(ItemStack stack) {
        return (ArrayList<IModule>) Collections.singletonList(module);
	}

	@Override
	public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) module).getGuiContainer(player, stack);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
		return ((IGuiItem) module).getGuiScreen(player, stack);
	}

    @Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || newStack.getItem() != oldStack.getItem() || newStack.getItemDamage() != oldStack.getItemDamage();
	}
}
