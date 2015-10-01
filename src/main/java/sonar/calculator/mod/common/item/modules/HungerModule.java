package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IHungerStore;
import sonar.calculator.mod.api.ProcessType;
import sonar.calculator.mod.utils.helpers.NutritionHelper;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HungerModule extends SonarItem implements IHungerStore {

	public HungerModule() {
		this.setCreativeTab(Calculator.Calculator);
		this.maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return NutritionHelper.chargeHunger(stack, world, player, "points");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par, float par8, float par9, float par10) {
		return NutritionHelper.useHunger(stack, player, world, x, y, z, par, "points");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);

		if (stack.hasTagCompound()) {
			list.add(FontHelper.translate("points.hunger") + ": " + getHungerPoints(stack));
		}
	}

	@Override
	public void transferHunger(int transfer, ItemStack stack, ProcessType process) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger("points", 0);
		}
		int points = stack.getTagCompound().getInteger("points");
		if (process == ProcessType.REMOVE) {
			nbtData.setInteger("points", points - transfer);
		} else if (process == ProcessType.ADD) {
			nbtData.setInteger("points", points + transfer);
		}
	}

	@Override
	public int getHungerPoints(ItemStack stack) {
		return NutritionHelper.getIntegerTag(stack, "points");
	}

	@Override
	public int getMaxHungerPoints(ItemStack stack) {
		return 1000;
	}

	@Override
	public void setHunger(ItemStack stack, int health) {
		if (!(health < 0) && health <= this.getMaxHungerPoints(stack)) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbtData = stack.getTagCompound();
			if (nbtData == null) {
				stack.getTagCompound().setInteger("points", 0);
			}
			nbtData.setInteger("points", health);
		}
	}

}
