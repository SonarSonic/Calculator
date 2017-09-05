package sonar.calculator.mod.common.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;

import java.util.Random;

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
        return this.greenhouseTier == 0 && super.canPlaceBlockAt(world, pos);
	}

	public boolean isUseable(int tier) {
        return tier >= this.greenhouseTier;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}
}
