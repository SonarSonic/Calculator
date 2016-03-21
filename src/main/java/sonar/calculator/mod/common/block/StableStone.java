package sonar.calculator.mod.common.block;

import java.util.List;

import sonar.calculator.mod.api.blocks.IConnectedBlock;
import sonar.calculator.mod.common.block.ConnectedBlock.StableStoneColours;
import sonar.core.utils.helpers.SonarHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StableStone extends Block implements IConnectedBlock {

	public int target = 0;
	public static final PropertyEnum<ConnectedBlock.StableStoneColours> COLOUR = PropertyEnum.<ConnectedBlock.StableStoneColours> create("colour", ConnectedBlock.StableStoneColours.class);
	/*public static final PropertyBool NORTH = PropertyBool.create("north"); public static final PropertyBool EAST = PropertyBool.create("east"); public static final PropertyBool SOUTH = PropertyBool.create("south"); public static final PropertyBool WEST = PropertyBool.create("west"); public static final PropertyBool UP = PropertyBool.create("up"); public static final PropertyBool DOWN = PropertyBool.create("down"); */
	
	public static final PropertyEnum<TextureTypes> NORTH = PropertyEnum.<TextureTypes>create("north", TextureTypes.class);
	public static final PropertyEnum<TextureTypes> EAST = PropertyEnum.<TextureTypes>create("east", TextureTypes.class);
	public static final PropertyEnum<TextureTypes> SOUTH = PropertyEnum.<TextureTypes>create("south", TextureTypes.class);
	public static final PropertyEnum<TextureTypes> WEST = PropertyEnum.<TextureTypes>create("west", TextureTypes.class);
	public static final PropertyEnum<TextureTypes> DOWN = PropertyEnum.<TextureTypes>create("down", TextureTypes.class);
	public static final PropertyEnum<TextureTypes> UP = PropertyEnum.<TextureTypes>create("up", TextureTypes.class);

	public enum TextureTypes implements IStringSerializable {
		T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
		
	}
	public static TextureTypes getType(int id){
		for(TextureTypes type : TextureTypes.values()){
			if(type.getName().equals("T"+(id+1))){
				return type;
			}
		}
		return TextureTypes.T1;
	}
	public StableStone(Material material) {
		super(material);
		// this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, StableStoneColours.NONE));
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, StableStoneColours.NONE).withProperty(NORTH, TextureTypes.T1).withProperty(EAST, TextureTypes.T1).withProperty(SOUTH, TextureTypes.T1).withProperty(WEST, TextureTypes.T1).withProperty(UP, TextureTypes.T1).withProperty(DOWN, TextureTypes.T1));
	}

	public static boolean checkBlockInDirection(IBlockAccess world, int x, int y, int z, EnumFacing side) {
		EnumFacing dir = side;
		IBlockState state = world.getBlockState(new BlockPos(x, y, z));
		IBlockState block = world.getBlockState(new BlockPos(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ()));
		int meta = state.getBlock().getMetaFromState(state);
		if (block != null) {
			if (type(state, block, meta, block.getBlock().getMetaFromState(block))) {
				return true;
			}
		}
		return false;
	}

	public int getSide(IBlockAccess w, int x, int y, int z, int s) {
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 0;
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 1;
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 2;
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 3;
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 4;
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 5;
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 6;
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 7;
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 8;
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 9;
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 10;
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 11;
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 12;
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 13;
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return 14;
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return 15;
		}
		return 0;
	}

	public int getSideT(IBlockAccess w, int x, int y, int z, int s) {
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 0;
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 1;
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 2;
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 3;
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 4;
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 5;
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 6;
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 7;
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 8;
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 9;
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 10;
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 11;
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 12;
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 13;
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return 14;
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return 15;
		}
		return 0;
	}

	public static boolean up(IBlockAccess world, int x, int y, int z) {
		/* int meta = world.getBlockMetadata(x, y, z); Block target = world.getBlock(x, y + 1, z); int blockMeta = world.getBlockMetadata(x, y + 1, z); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.getFront(1));
	}

	public static boolean down(IBlockAccess world, int x, int y, int z) {
		/* int meta = world.getBlockMetadata(x, y, z); Block target = world.getBlock(x, y - 1, z); int blockMeta = world.getBlockMetadata(x, y - 1, z); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.getFront(0));
	}

	public static boolean right(IBlockAccess world, int x, int y, int z, int dir) {
		if (dir != 0 && dir != 1) {
			return checkBlockInDirection(world, x, y, z, SonarHelper.getHorizontal(EnumFacing.getFront(dir)));
			/* EnumFacing hoz = getHorizontal(dir).getOpposite(); if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null) { Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } } */
		}
		return false;
	}

	public static boolean left(IBlockAccess world, int x, int y, int z, int dir) {
		if (dir != 0 && dir != 1) {
			return checkBlockInDirection(world, x, y, z, SonarHelper.getHorizontal(EnumFacing.getFront(dir)));
			/* EnumFacing hoz = getHorizontal(dir); Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		}
		return false;
	}

	public static boolean upT(IBlockAccess world, int x, int y, int z) {
		/* int meta = world.getBlockMetadata(x, y, z); EnumFacing hoz = EnumFacing.NORTH; Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.NORTH);

	}

	public static boolean downT(IBlockAccess world, int x, int y, int z) {
		/* int meta = world.getBlockMetadata(x, y, z); EnumFacing hoz = EnumFacing.NORTH.getOpposite(); Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.NORTH.getOpposite());
	}

	public static boolean rightT(IBlockAccess world, int x, int y, int z, int dir) {
		/* int meta = world.getBlockMetadata(x, y, z); EnumFacing hoz = EnumFacing.EAST; int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.EAST);
	}

	public static boolean leftT(IBlockAccess world, int x, int y, int z, int dir) {
		/* BlockPos pos = new BlockPos(x,y,z); world.getBlockState(pos); int meta = world.getBlockMetadata(x, y, z); EnumFacing hoz = EnumFacing.EAST.getOpposite(); Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } */
		return checkBlockInDirection(world, x, y, z, EnumFacing.EAST.getOpposite());
	}

	public static boolean type(IBlockState state1, IBlockState state2, int m1, int m2) {
		Block block1 = state1.getBlock();
		Block block2 = state2.getBlock();
		if (!(block1 instanceof StableStone) || !(block2 instanceof StableStone) || m1 == m2) {

			if (block1 instanceof StableStone) {
				StableStone c1 = (StableStone) block1;
				if (block2 instanceof StableStone) {
					if (c1.target == ((StableStone) block2).target)
						return true;
				}
				if (block2 instanceof IConnectedBlock) {
					int[] connections = ((IConnectedBlock) block2).getConnections();
					for (int i = 0; i < connections.length; i++) {
						if (connections[i] == c1.target)
							return true;

					}

				}

			} else if (block1 instanceof IConnectedBlock) {
				IConnectedBlock c1 = (IConnectedBlock) block1;
				int[] connections1 = ((IConnectedBlock) block1).getConnections();

				if (block2 instanceof ConnectedBlock) {
					for (int i = 0; i < connections1.length; i++) {
						if (connections1[i] == ((ConnectedBlock) block2).target)
							return true;
					}
				}
				if (block2 instanceof IConnectedBlock) {
					int[] connections2 = ((IConnectedBlock) block2).getConnections();
					for (int i = 0; i < connections1.length; i++) {
						for (int i2 = 0; i2 < connections2.length; i2++) {
							if (connections1[i] == connections2[i2])
								return true;
						}

					}

				}

			}
		}
		return false;

	}

	public int damageDropped(IBlockState state) {
		return ((StableStoneColours) state.getValue(COLOUR)).getMetaData();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < StableStoneColours.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOUR, StableStoneColours.getColour(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((StableStoneColours) state.getValue(COLOUR)).getMetaData();
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		return state.withProperty(COLOUR, StableStoneColours.BLUE).withProperty(NORTH, getType(getSide(w, x, y, z, EnumFacing.NORTH.getIndex()))).withProperty(SOUTH, getType(getSide(w, x, y, z, EnumFacing.SOUTH.getIndex()))).withProperty(WEST, getType(getSide(w, x, y, z, EnumFacing.WEST.getIndex()))).withProperty(EAST, getType(getSide(w, x, y, z, EnumFacing.EAST.getIndex()))).withProperty(UP, getType(getSide(w, x, y, z, EnumFacing.UP.getIndex()))).withProperty(DOWN, getType(getSide(w, x, y, z, EnumFacing.DOWN.getIndex())));
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { COLOUR, NORTH, EAST, SOUTH, WEST, DOWN, UP });
	}

	@Override
	public int[] getConnections() {
		return new int[] { target };
	}
}
