package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.BlockFire;

import java.util.Random;

public class EternalFire extends BlockFire {
	public EternalFire() {
		//this.setBlockBounds(0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		this.setTickRandomly(false);
	}

    @Override
	public int quantityDropped(Random p_149745_1_) {
		return 1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}

    @Override
	public boolean isCollidable() {
		return true;
	}
}
