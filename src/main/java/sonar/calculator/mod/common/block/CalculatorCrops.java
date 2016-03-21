package sonar.calculator.mod.common.block;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;

public class CalculatorCrops extends BlockCrops {

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public int crop, greenhouseTier;

	public CalculatorCrops(int type, int tier) {
		this.crop = type;
		this.greenhouseTier = tier;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconArray = new IIcon[4];

		for (int i = 0; i < this.iconArray.length; i++) {
			this.iconArray[i] = iconRegister.registerIcon("Calculator:plants/" + getUnlocalizedName().substring(5) + (i + 1));
		}
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		if (metadata < 7) {
			if (metadata == 6) {
				metadata = 5;
			}

			return this.iconArray[(metadata >> 1)];
		}

		return this.iconArray[3];
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	protected Item func_149866_i() {
		if (crop == 0) {
			return Calculator.broccoliSeeds;
		}

		if (crop == 1) {
			return Calculator.prunaeSeeds;
		}
		return Calculator.fiddledewFruit;
	}

	@Override
	protected Item func_149865_P() {
		if (crop == 0) {
			return Calculator.broccoli;
		}

		if (crop == 1) {
			return Calculator.coal_dust;
		}
		return Calculator.fiddledewFruit;
	}

	public boolean canTierUse(int tier) {
		if (tier >= this.greenhouseTier) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (this.greenhouseTier == 0) {
			return canPlaceCropsAt(world, x, y, z);
		}
		return false;
	}

	public boolean canPlaceCropsAt(World world, int x, int y, int z) {
		return world.getBlock(x, y, z).isReplaceable(world, x, y, z) && world.getBlock(x, y - 1, z).canSustainPlant(world, x, y - 1, z, EnumFacing.UP, this);
	}

}
