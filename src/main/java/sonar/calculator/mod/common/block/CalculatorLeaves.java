package sonar.calculator.mod.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.ConnectedBlock.StableStoneColours;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.calculator.mod.common.block.StableStone.TextureTypes;

public class CalculatorLeaves extends BlockLeavesBase implements IShearable {

	int leafType;

	public static final PropertyEnum<LeafGrowth> GROWTH = PropertyEnum.<LeafGrowth> create("growth", LeafGrowth.class);

	public enum LeafGrowth implements IStringSerializable {
		FRESH(0), GROWING(1), READY(2), MATURED(3);
		private int meta;

		LeafGrowth(int meta) {
			this.meta = meta;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		public boolean canGrow() {
			return this != MATURED;
		}

		public int getMeta() {
			return meta;
		}

	}

	public CalculatorLeaves(int type) {
		super(Material.leaves, true);
		this.leafType = type;
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(Block.soundTypeGrass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, LeafGrowth.FRESH));

	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		int blocks = 0;
		for (int i = 0; i < EnumFacing.VALUES.length; i++) {
			EnumFacing dir = EnumFacing.VALUES[i];
			Block block = world.getBlockState(pos.offset(dir)).getBlock();
			if (!block.isAir(world, pos)) {
				blocks++;
			}
		}
		if (blocks != 6) {
			int randInt = 0;
			
			if (leafType == 3) {
				randInt = rand.nextInt(20);
			} else if (leafType < 2) {
				randInt = rand.nextInt(8);
			} else {
				randInt = rand.nextInt(10);
			}
			
			if (randInt == 2) {
				LeafGrowth leaf = state.getValue(GROWTH);
				if (leaf.canGrow()) {
					world.setBlockState(pos, state.cycleProperty(GROWTH));
				}
			}
		}
	}

	@Override
	public int tickRate(World world) {
		return 10;
	}

	@Override
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
		if ((world.canLightningStrike(pos.offset(EnumFacing.UP))) && (!World.doesBlockHaveSolidTopSurface(world, pos.offset(EnumFacing.DOWN))) && (random.nextInt(15) == 1)) {
			double d0 = pos.getX() + random.nextFloat();
			double d1 = pos.getY() - 0.05D;
			double d2 = pos.getZ() + random.nextFloat();
			world.spawnParticle(EnumParticleTypes.WATER_DROP, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		super.randomDisplayTick(world, pos, state, random);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, EnumFacing face) {
		return 150;
	}

	@Override
	public void beginLeavesDecay(World world, BlockPos pos) {
	}

	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos posz) {
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList();
		switch (leafType) {
		case 0:
			ret.add(new ItemStack(Calculator.amethystLeaves, 1, 0));
			break;
		case 1:
			ret.add(new ItemStack(Calculator.tanzaniteLeaves, 1, 0));
			break;
		case 2:
			ret.add(new ItemStack(Calculator.pearLeaves, 1, 0));
			break;
		case 3:
			ret.add(new ItemStack(Calculator.diamondLeaves, 1, 0));
			break;
		}
		return ret;
	}

	public int damageDropped(IBlockState state) {
		return 0;
	}

	public LeafGrowth getVariant(int metadata) {
		for (LeafGrowth colour : LeafGrowth.values()) {
			if (colour.meta == metadata) {
				return colour;
			}
		}
		return LeafGrowth.FRESH;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(GROWTH, getVariant(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((LeafGrowth) state.getValue(GROWTH)).getMeta();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { GROWTH });
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return Minecraft.isFancyGraphicsEnabled() ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
	}
}
