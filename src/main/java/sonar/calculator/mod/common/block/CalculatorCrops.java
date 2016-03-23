package sonar.calculator.mod.common.block;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;

public class CalculatorCrops extends BlockCrops {

	public int crop, greenhouseTier;

	public CalculatorCrops(int type, int tier) {
		this.crop = type;
		this.greenhouseTier = tier;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	protected Item getSeed() {
		if (crop == 0) {
			return Calculator.broccoliSeeds;
		}

		if (crop == 1) {
			return Calculator.prunaeSeeds;
		}
		return Calculator.fiddledewFruit;
	}

	@Override
	protected Item getCrop() {
		if (crop == 0) {
			return Calculator.broccoli;
		}

		if (crop == 1) {
			return Calculator.coal_dust;
		}
		return Calculator.fiddledewFruit;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		if (this.greenhouseTier == 0) {
			return super.canPlaceBlockAt(world, pos);
		}
		return false;
	}

	public boolean isUseable(int tier) {
		if (tier >= this.greenhouseTier) {
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}
}
