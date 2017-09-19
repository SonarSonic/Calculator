package sonar.calculator.mod.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.calculator.mod.utils.helpers.CalculatorTreeBuilder;

import java.util.Random;

public class CalculatorSaplings extends BlockBush implements IGrowable {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	public int type;

	public CalculatorSaplings(int i) {
		this.type = i;
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
		float f = 0.4F;
	}

    @Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
        return type != 3 ? super.canPlaceBlockAt(worldIn, pos) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) : soil.getBlock() == Calculator.material_block && soil.getValue(MaterialBlock.VARIANTS) == Variants.END_DIAMOND;
	}

    @Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		return type == 3 ? this.canSustainBush(worldIn.getBlockState(pos.down())) : super.canBlockStay(worldIn, pos, state);
	}

    @Override
	protected boolean canSustainBush(IBlockState state) {
        return type != 3 ? state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND : state.getBlock() == Calculator.material_block && state.getValue(MaterialBlock.VARIANTS) == Variants.END_DIAMOND;
	}

    @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			generateTree(worldIn, pos, state, rand);
		}
	}

	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
			return;
        WorldGenerator worldgenerator = rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
		int i = 0;
		int j = 0;
		boolean flag = false;

		switch (type) {
		case 0:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.amethystSapling, Calculator.amethystLeaves, Calculator.amethystLog);
			break;

		case 1:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.tanzaniteSapling, Calculator.tanzaniteLeaves, Calculator.tanzaniteLog);
			break;

		case 2:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.pearSapling, Calculator.pearLeaves, Calculator.pearLog);
			break;

		case 3:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.diamondSapling, Calculator.diamondLeaves, Calculator.diamondLog);
			break;
		}

		IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

		if (flag) {
			worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
		} else {
			worldIn.setBlockState(pos, iblockstate2, 4);
		}

		if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
			if (flag) {
				worldIn.setBlockState(pos.add(i, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
			} else {
				worldIn.setBlockState(pos, state, 4);
			}
		}
	}

    @Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

    @Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

    @Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}

    @Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}

    @Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

    @Override
	public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
	}

    @Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
	}
}