package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.api.blocks.IObsidianDrop;
import sonar.core.common.item.SonarItem;

public class ObsidianKey extends SonarItem {

	public ObsidianKey() {
		setMaxDamage(1000);
		this.maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		if (!player.canPlayerEdit(pos, side, stack)) {
			return false;
		}
		Block block = world.getBlockState(pos).getBlock();
		if (block == Blocks.obsidian || (block instanceof IObsidianDrop && ((IObsidianDrop) block).canKeyDrop(world, pos))) {
			block.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
			stack.damageItem(1, player);
		}

		return true;
	}
}
