package sonar.calculator.mod.common.item.modules;
/*
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.common.item.SonarEnergyItem;
import sonar.core.api.inventories.IItemInventory;

public class WIPSmeltingModule extends SonarEnergyItem implements IItemInventory {

	public int syncCook, syncEnergy;
	public int requiredEnergy = 1000;
	public int speed = 1000;

	public WIPSmeltingModule() {
		capacity = CalculatorConfig.getInteger("Standard Machine");
		maxReceive = CalculatorConfig.getInteger("Standard Machine");
		maxExtract = CalculatorConfig.getInteger("Standard Machine");
		maxTransfer = CalculatorConfig.getInteger("Standard Machine");
	}

	public static class SmeltingInventory extends InventoryItem {

		public SmeltingInventory(ItemStack stack) {
			super(stack, 3, "Items");
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		return onCalculatorRightClick(itemstack, world, player, CalculatorGui.SmeltingModule);
	}

	public static int getIntegerTag(ItemStack stack, String tag) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		return stack.getTagCompound().getInteger(tag);

	}

	public int cookTime(ItemStack stack) {
		return getIntegerTag(stack, "cookTime");
	}

	public static void setCookTime(ItemStack stack, int burn) {
		setIntegerTag(stack, "cookTime", burn);
	}

	public static int itemBurnTime(ItemStack stack) {
		return getIntegerTag(stack, "cookTime");
	}

	public static void setItemBurnTime(ItemStack stack, int burn) {
		setIntegerTag(stack, "cookTime", burn);
	}

	public static void setIntegerTag(ItemStack stack, String tag, int value) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		nbtData.setInteger(tag, value);

	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean bool) {

		int energy = this.requiredEnergy / this.speed;
		int currentCook = cookTime(stack);

		if (currentCook > 0) {
			currentCook++;
			this.extractEnergy(stack, energy, false);
		}
		if (this.canCook(stack)) {
			if (currentCook == 0) {
				currentCook++;
				this.extractEnergy(stack, energy, false);
			}

			if (currentCook == this.speed) {
				currentCook = 0;
				cookItem(stack);
			}

		} else {
			if (currentCook != 0) {
				currentCook = 0;

			}
		}
		if (currentCook != this.cookTime(stack)) {
			this.setCookTime(stack, currentCook);
		}
	}

	public void cookItem(ItemStack stack) {
		// a instance of inventory not always updated
		InventoryItem inv = this.getInventory(stack);
		ItemStack itemstack = recipe(inv.getStackInSlot(0));
		if (itemstack != null) {
			if (inv.getStackInSlot(2) == null) {
				inv.setInventorySlotContents(2, itemstack.copy());
			} else if (inv.getStackInSlot(2).isItemEqual(itemstack)) {
				this.getInventory(stack).decrStackSize(2, itemstack.getCount());
			}

			this.getInventory(stack).decrStackSize(0, 1);
		}
	}

	public boolean canCook(ItemStack stack) {
		int currentCook = cookTime(stack);
		InventoryItem inv = this.getInventory(stack);
		if (inv.getStackInSlot(0) == null) {
			return false;
		}

		if (currentCook == 0) {
			if (this.getEnergyStored(stack) < this.requiredEnergy) {
				return false;
			}
		}

		ItemStack itemstack = recipe(inv.getStackInSlot(0));

		if (itemstack == null) {
			return false;
		}
		if (inv.getStackInSlot(2) != null) {
			if (!inv.getStackInSlot(2).isItemEqual(itemstack)) {
				return false;
			} else {
				if (inv.getStackInSlot(2).stackSize + itemstack.getCount() > inv.getStackInSlot(2).getMaxStackSize()) {
					return false;
				}
			}

		}

		return true;

	}

	public ItemStack recipe(ItemStack stack) {
		return FurnaceRecipes.instance().getSmeltingResult(stack);
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new SmeltingInventory(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			int storedItems = getInventory(stack).getItemsStored(stack);
			if (storedItems != 0) {
				list.add("Stored Stacks: " + storedItems);
			}
		}
	}
}
*/