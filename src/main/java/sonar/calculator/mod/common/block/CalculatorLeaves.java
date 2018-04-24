package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import sonar.calculator.mod.Calculator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalculatorLeaves extends Block implements IShearable {

	int leafType;

    public static final PropertyEnum<LeafGrowth> GROWTH = PropertyEnum.create("growth", LeafGrowth.class);

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
		super(Material.LEAVES);
		this.leafType = type;
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		//setStepSound(Block.soundTypeGrass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, LeafGrowth.FRESH));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		int blocks = 0;
		for (int i = 0; i < EnumFacing.VALUES.length; i++) {
			EnumFacing dir = EnumFacing.VALUES[i];
			IBlockState neighbour = world.getBlockState(pos.offset(dir));
			Block block = neighbour.getBlock();
			if (!block.isAir(neighbour, world, pos)) {
				blocks++;
			}
		}
		if (blocks != 6) {
            int randInt;
			if (leafType == 3) {
				randInt = rand.nextInt(10);
			} else if (leafType < 2) {
				randInt = rand.nextInt(8);
			} else {
				randInt = rand.nextInt(9);
			}
			if (randInt <= 2) {
				LeafGrowth leaf = state.getValue(GROWTH);
				if (leaf.canGrow()) {
					world.setBlockState(pos, state.cycleProperty(GROWTH));
				}
			}
		}
	}

	@Override
	public int tickRate(World world) {
		return 8;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, EnumFacing face) {
		return 150;
	}

	@Override
	public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
	}

	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos posz) {
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Nonnull
    @Override
	public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
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

    @Override
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

    @Nonnull
    @Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(GROWTH, getVariant(meta));
	}

    @Override
	public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWTH).getMeta();
	}

    @Nonnull
    @Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH);
	}

    @Nonnull
    @Override
	public BlockRenderLayer getBlockLayer() {
		return Minecraft.isFancyGraphicsEnabled() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}	

    public boolean isVisuallyOpaque() {
        return false;
    }
}
