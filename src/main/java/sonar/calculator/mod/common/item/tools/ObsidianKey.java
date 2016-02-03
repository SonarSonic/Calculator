package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.api.blocks.IObsidianDrop;
import sonar.core.common.item.SonarItem;

public class ObsidianKey extends SonarItem {

	public ObsidianKey() {
		setMaxDamage(1000);
		this.maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		}
		Block block = world.getBlock(x, y, z);
		if (block == Blocks.obsidian || (block instanceof IObsidianDrop && ((IObsidianDrop) block).canKeyDrop(world, x, y, z))) {
			block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
			stack.damageItem(1, player);
		}

		return true;
	}
}
