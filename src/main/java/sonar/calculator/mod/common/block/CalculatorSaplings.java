package sonar.calculator.mod.common.block;

import java.util.List;
import java.util.Random;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.utils.helpers.CalculatorTreeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CalculatorSaplings extends BlockBush implements IGrowable {
	
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	int type;

	public CalculatorSaplings(int i) {
		this.type=i;
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}

	@Override
	public boolean canPlaceBlockOn(Block block) {
		return type == 3 ? block == Calculator.end_diamond_block : super.canPlaceBlockOn(block);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		return type == 3 ? canPlaceBlockOn(world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock()) : super.canBlockStay(world, pos, state);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (((Integer) state.getValue(STAGE)).intValue() == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, pos, state, rand);
		}
	}

	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
			return;
		WorldGenerator worldgenerator = (WorldGenerator) (rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
		int i = 0;
		int j = 0;
		boolean flag = false;

		switch (type) {
		case 0:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.AmethystSapling, Calculator.amethystLeaves, Calculator.amethystLog);
			break;

		case 1:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.tanzaniteSapling, Calculator.tanzaniteLeaves, Calculator.tanzaniteLog);
			break;

		case 2:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.PearSapling, Calculator.pearLeaves, Calculator.pearLog);
			break;

		case 3:
			worldgenerator = new CalculatorTreeBuilder(true, Calculator.diamondSapling, Calculator.diamondLeaves, Calculator.diamondLog);
			break;
		}

		IBlockState iblockstate2 = Blocks.air.getDefaultState();

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

	public int damageDropped(IBlockState state) {
		return 0;
	}

	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE).intValue();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { STAGE });
	}
}