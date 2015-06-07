package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IHealthStore;
import sonar.calculator.mod.api.IHungerStore;
import sonar.calculator.mod.api.ProcessType;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.calculator.mod.utils.helpers.NutritionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NutritionModule extends CalcItem implements IHealthStore, IHungerStore {

	public NutritionModule() {
		setCreativeTab(Calculator.Calculator);
		this.maxStackSize = 1;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean bool) {
		if (!world.isRemote) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}
				NBTTagCompound nbtData = stack.stackTagCompound;
				if (nbtData == null) {
					stack.stackTagCompound.setInteger("health", 0);
					stack.stackTagCompound.setInteger("hunger", 0);
					stack.stackTagCompound.setInteger("ticks", 0);
				}

				int ticks = stack.stackTagCompound.getInteger("ticks");
				if (ticks < 10) {
					stack.stackTagCompound.setInteger("ticks", ticks + 1);
				} else {
					stack.stackTagCompound.setInteger("ticks", 0);
					int points = stack.stackTagCompound.getInteger("hunger");
					int hunger = player.getFoodStats().getFoodLevel();
					int maxpoints = 20 - hunger;
					int usedpoints = Math.min(maxpoints, 2);
					if (points - usedpoints >= 1) {
						points -= usedpoints;
						nbtData.setInteger("hunger", points);
						player.getFoodStats().addStats(hunger + usedpoints, 2.0F);
					} else if (points - usedpoints < 1) {
						nbtData.setInteger("hunger", 0);
						player.getFoodStats().addStats(points, 2.0F);

					}
					secondItemRightClick(stack, world, player);
				}
			}
		}
	}

	public ItemStack secondItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		NBTTagCompound nbtData = stack.stackTagCompound;

		int points = stack.stackTagCompound.getInteger("health");
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
			list.add(StatCollector.translateToLocal("points.hunger") + ": " + getHungerPoints(stack));
			list.add(StatCollector.translateToLocal("points.health") + ": " + getHealthPoints(stack));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par, float par8, float par9, float par10) {
		NutritionHelper.useHunger(stack, player, world, x, y, z, par, "hunger");
		NutritionHelper.useHealth(stack, player, world, x, y, z, par, "health");
		return true;
	}

	@Override
	public void transferHunger(int transfer, ItemStack stack, ProcessType process) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.stackTagCompound;
		if (nbtData == null) {
			stack.stackTagCompound.setInteger("hunger", 0);
		}

		int points = stack.stackTagCompound.getInteger("hunger");
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
			NBTTagCompound nbtData = stack.stackTagCompound;
			if (nbtData == null) {
				stack.stackTagCompound.setInteger("hunger", 0);
			}
			nbtData.setInteger("hunger", health);
		}
	}

	@Override
	public void transferHealth(int transfer, ItemStack stack, ProcessType process) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.stackTagCompound;
		if (nbtData == null) {
			stack.stackTagCompound.setInteger("health", 0);
		}
		int points = stack.stackTagCompound.getInteger("health");
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
			NBTTagCompound nbtData = stack.stackTagCompound;
			if (nbtData == null) {
				stack.stackTagCompound.setInteger("health", 0);
			}
			nbtData.setInteger("health", health);
		}
	}

}
