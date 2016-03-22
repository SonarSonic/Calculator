package sonar.calculator.mod.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLeaves.LeafGrowth;
import sonar.core.utils.helpers.FontHelper;

public class NutritionHelper {

	public static ItemStack chargeHunger(ItemStack stack, World world, EntityPlayer player, String tag) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		int points = stack.getTagCompound().getInteger(tag);
		if (player.isSneaking()) {
			int hunger = player.getFoodStats().getFoodLevel();

			int usedpoints = 20 - hunger;

			if (!(hunger >= 20)) {
				if (points - usedpoints > 1) {
					points -= usedpoints;
					nbtData.setInteger(tag, points);
					player.getFoodStats().addStats(20, 2.0F);

				} else if (points - usedpoints < 1) {
					nbtData.setInteger(tag, 0);
					player.getFoodStats().addStats(points, 2.0F);
				}
			}
		} else if (!world.isRemote) {
			FontHelper.sendMessage(FontHelper.translate("points.hunger") + ": " + stack.getTagCompound().getInteger(tag), world, player);
		}
		return stack;
	}

	public static ItemStack chargeHealth(ItemStack stack, World world, EntityPlayer player, String tag) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		if (player.isSneaking()) {
			int points = stack.getTagCompound().getInteger(tag);
			if (points != 0) {
				int current = (int) player.getHealth();
				int max = (int) player.getMaxHealth();
				if (current != max & (current < max)) {
					int used = max - current;
					if (!(points - used < 0)) {
						nbtData.setInteger(tag, points - used);
						player.setHealth(player.getMaxHealth());
					} else if ((points - used < 0)) {
						nbtData.setInteger(tag, 0);
						player.setHealth(nbtData.getInteger(tag) + current);
					}
				}
			}
		} else {
			FontHelper.sendMessage(FontHelper.translate("points.health") + ": " + stack.getTagCompound().getInteger(tag), world, player);
		}
		return stack;
	}

	public static boolean useHunger(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, String tag) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		int points = stack.getTagCompound().getInteger(tag);

		if (!player.canPlayerEdit(pos, side, stack)) {
			return false;
		}
		Block block = world.getBlockState(pos).getBlock();
		if (block != null && block == Calculator.amethystLeaves) {
			IBlockState state = world.getBlockState(pos);
			LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);
			if (growth == LeafGrowth.READY || growth == LeafGrowth.MATURED) {
				points += 1;
				if (!world.isRemote) {
					world.setBlockState(pos, state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
				}
				nbtData.setInteger(tag, points);
			}

		}
		return true;
	}

	public static boolean useHealth(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, String tag) {
	
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}

		int points = stack.getTagCompound().getInteger(tag);

		if (!player.canPlayerEdit(pos, side, stack)) {
			return false;
		}

		Block block = world.getBlockState(pos).getBlock();	
		if (block != null && block == Calculator.tanzaniteLeaves) {
			IBlockState state = world.getBlockState(pos);
			LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);
			if (growth == LeafGrowth.READY || growth == LeafGrowth.MATURED) {
				points += 1;
				if (!world.isRemote) {
					world.setBlockState(pos, state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
				}
				nbtData.setInteger(tag, points);
			}
		}

		return true;
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

	public int getPoints(ItemStack stack, String tag) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger(tag, 0);
		}
		return stack.getTagCompound().getInteger(tag);

	}

}
