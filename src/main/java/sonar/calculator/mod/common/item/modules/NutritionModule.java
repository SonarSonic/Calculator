package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.ProcessType;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.api.nutrition.IHungerStore;
import sonar.calculator.mod.utils.helpers.NutritionHelper;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;

public class NutritionModule extends SonarItem implements IHealthStore, IHungerStore {

	public NutritionModule() {
		setCreativeTab(Calculator.Calculator);
		maxStackSize = 1;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean bool) {
		if (!world.isRemote) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}
				NBTTagCompound nbtData = stack.getTagCompound();
				if (nbtData == null) {
					stack.getTagCompound().setInteger("health", 0);
					stack.getTagCompound().setInteger("hunger", 0);
					stack.getTagCompound().setInteger("ticks", 0);
				}

				int ticks = stack.getTagCompound().getInteger("ticks");
				if (ticks < 10) {
					stack.getTagCompound().setInteger("ticks", ticks + 1);
				} else {
					stack.getTagCompound().setInteger("ticks", 0);
					int points = stack.getTagCompound().getInteger("hunger");
					int hunger = player.getFoodStats().getFoodLevel();
					int maxpoints = 20 - hunger;
					int usedpoints = Math.min(maxpoints, 2);
					if (!(hunger >= 20)) {
						if (points - usedpoints > 0) {
							points -= usedpoints;
							nbtData.setInteger("hunger", points);
							player.getFoodStats().addStats(hunger + usedpoints, 0.2F);
						} else if (points - usedpoints <= 0) {
							nbtData.setInteger("hunger", 0);
							player.getFoodStats().addStats(points, 0.2F);
						}
					}
					secondItemRightClick(stack, world, player);
				}
			}
		}
	}

	public ItemStack secondItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		NBTTagCompound nbtData = stack.getTagCompound();

		int points = stack.getTagCompound().getInteger("health");
		if (points != 0) {
			int current = (int) player.getHealth();
			int max = (int) player.getMaxHealth();
			if (current != max & (current < max)) {
				int maxpoints = max - current;
				int usedpoints = Math.min(maxpoints, 2);
				if (!(points - usedpoints < 0)) {
					nbtData.setInteger("health", points - usedpoints);
					player.setHealth(player.getHealth() + usedpoints);
				} else if ((points - usedpoints < 0)) {
					nbtData.setInteger("health", 0);
					player.setHealth(nbtData.getInteger("health") + current);
				}

			}
		}
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			list.add(FontHelper.translate("points.hunger") + ": " + getHungerPoints(stack));
			list.add(FontHelper.translate("points.health") + ": " + getHealthPoints(stack));
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		NutritionHelper.useHunger(stack, player, world, pos, side, "hunger");
		NutritionHelper.useHealth(stack, player, world, pos, side, "health");
		return EnumActionResult.SUCCESS;
	}

	@Override
	public void transferHunger(int transfer, ItemStack stack, ProcessType process) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger("hunger", 0);
		}

		int points = stack.getTagCompound().getInteger("hunger");
		if (process == ProcessType.REMOVE) {
			nbtData.setInteger("hunger", points - transfer);
		} else if (process == ProcessType.ADD) {
			nbtData.setInteger("hunger", points + transfer);
		}
	}

	@Override
	public int getHungerPoints(ItemStack stack) {
		return NutritionHelper.getIntegerTag(stack, "hunger");
	}

	@Override
	public int getMaxHungerPoints(ItemStack stack) {
		return -1;
	}

	@Override
	public void setHunger(ItemStack stack, int health) {
		if (!(health < 0) && health <= this.getMaxHungerPoints(stack)) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbtData = stack.getTagCompound();
			if (nbtData == null) {
				stack.getTagCompound().setInteger("hunger", 0);
			}
			nbtData.setInteger("hunger", health);
		}
	}

	@Override
	public void transferHealth(int transfer, ItemStack stack, ProcessType process) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger("health", 0);
		}
		int points = stack.getTagCompound().getInteger("health");
		if (process == ProcessType.REMOVE) {
			nbtData.setInteger("health", points - transfer);
		} else if (process == ProcessType.ADD) {
			nbtData.setInteger("health", points + transfer);
		}
	}

	@Override
	public int getHealthPoints(ItemStack stack) {
		return NutritionHelper.getIntegerTag(stack, "health");
	}

	@Override
	public int getMaxHealthPoints(ItemStack stack) {
		return -1;
	}

	@Override
	public void setHealth(ItemStack stack, int health) {
		if (!(health < 0) && health <= this.getMaxHealthPoints(stack)) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbtData = stack.getTagCompound();
			if (nbtData == null) {
				stack.getTagCompound().setInteger("health", 0);
			}
			nbtData.setInteger("health", health);
		}
	}

}
