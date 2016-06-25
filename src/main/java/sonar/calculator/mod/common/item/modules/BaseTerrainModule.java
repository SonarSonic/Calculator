package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.block.Block;
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
import sonar.core.common.item.SonarEnergyItem;
import sonar.core.helpers.FontHelper;

public class BaseTerrainModule extends SonarEnergyItem {

	public Block[] replacable;

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			list.add(FontHelper.translate("calc.mode") + ": " + currentBlockString(stack, player));
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode || this.getEnergyStored(stack) > 0) {
			if (!player.canPlayerEdit(pos, side, stack)) {
				return EnumActionResult.PASS;
			}
			if (player.isSneaking()) {
				incrementMode(stack);
				FontHelper.sendMessage(currentBlockString(stack, player), world, player);
			} else {
				if (canReplace(stack, world, pos)) {
					world.setBlockState(pos, getCurrentBlock(stack).getStateFromMeta(stack.getMetadata()));
					if (!player.capabilities.isCreativeMode) {
						int energy = this.getEnergyStored(stack);
						stack.getTagCompound().setInteger("Energy", energy - 1);
					}
				}
			}
		} else if (this.getEnergyStored(stack) == 0) {
			FontHelper.sendMessage(FontHelper.translate("energy.noEnergy"), world, player);
		}
		return EnumActionResult.SUCCESS;
	}

	public String currentBlockString(ItemStack stack, EntityPlayer player) {
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
		} else if (replaceableBlock(world.getBlockState(pos).getBlock())) {
			return true;
		}
		return false;
	}

	public boolean replaceableBlock(Block block) {
		for (int s = 0; s < replacable.length; s++) {
			if (replacable[s] != null) {
				if (block == replacable[s]) {
					return true;
				}
			}
		}
		return false;
	}
}
