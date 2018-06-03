package sonar.calculator.mod.common.item.modules;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
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
import sonar.core.api.utils.ActionType;
import sonar.core.common.item.SonarEnergyItem;
import sonar.core.helpers.FontHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class BaseTerrainModule extends SonarEnergyItem {

	public Block[] replacable;
	public final int usage;

	public BaseTerrainModule(int capacity, int usage) {
		super(capacity, capacity, capacity);
		this.usage = usage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
		super.addInformation(stack, world, list, par4);
		if (stack.hasTagCompound()) {
			list.add(FontHelper.translate("calc.mode") + ": " + currentBlockString(stack, world));
		}
	}

	@Nonnull
    @Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
			if (player.isSneaking()) {
				incrementMode(stack);
				FontHelper.sendMessage(currentBlockString(stack, player), world, player);
			} else if (canReplace(stack, world, pos)) {
				if (player.capabilities.isCreativeMode || this.getEnergyLevel(stack) >= usage) {
					world.setBlockState(pos, getCurrentBlock(stack).getStateFromMeta(stack.getMetadata()));
					if (!player.capabilities.isCreativeMode) {
						removeEnergy(stack, usage, ActionType.PERFORM);
					}
				} else {
					FontHelper.sendMessage(FontHelper.translate("energy.noEnergy"), world, player);
				}
			}

		return EnumActionResult.SUCCESS;
	}

	public String currentBlockString(ItemStack stack, EntityPlayer player) {
		return new ItemStack(getCurrentBlock(stack), 1).getDisplayName();
	}

	public String currentBlockString(ItemStack stack, World world) {
		return new ItemStack(getCurrentBlock(stack), 1).getDisplayName();
	}

	public Block getCurrentBlock(ItemStack stack) {
		return replacable[getCurrentMode(stack)];
	}

	public int getCurrentMode(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			stack.getTagCompound().setInteger("Mode", 0);
		}
		return stack.getTagCompound().getInteger("Mode");
	}

	public void incrementMode(ItemStack stack) {
		int current = this.getCurrentMode(stack);
		if (current + 1 != replacable.length) {
			stack.getTagCompound().setInteger("Mode", current + 1);
		} else {
			stack.getTagCompound().setInteger("Mode", 0);
		}
	}

	public boolean canReplace(ItemStack stack, World world, BlockPos pos) {
		if (getCurrentBlock(stack) == world.getBlockState(pos).getBlock()) {
			return false;
		} else return replaceableBlock(world.getBlockState(pos).getBlock());
    }

	public boolean replaceableBlock(Block block) {
		for (Block aReplacable : replacable) {
			if (aReplacable != null) {
				if (block == aReplacable) {
					return true;
				}
			}
		}
		return false;
	}
}
