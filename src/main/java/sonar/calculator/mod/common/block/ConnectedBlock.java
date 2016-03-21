package sonar.calculator.mod.common.block;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.blocks.IConnectedBlock;
import sonar.calculator.mod.api.blocks.IObsidianDrop;
import sonar.calculator.mod.api.blocks.IStableBlock;
import sonar.calculator.mod.api.blocks.IStableGlass;
import sonar.calculator.mod.api.blocks.properties.PropertyStableStoneColours;

public class ConnectedBlock extends Block implements IConnectedBlock {

	public String type;
	public int target;
	public boolean hasColours = false;
	public static final PropertyStableStoneColours COLOUR = PropertyStableStoneColours.create("colour");
	public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 15);
	public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 15);
	public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 15);
	public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 15);
	public static final PropertyInteger DOWN = PropertyInteger.create("down", 0, 15);
	public static final PropertyInteger UP = PropertyInteger.create("up", 0, 15);

	// @SideOnly(Side.CLIENT)
	// private IIcon[] colours, normal;

	public ConnectedBlock(Material material, String name, int block, boolean hasColours) {
		super(material);
		this.target = block;
		this.type = name;
		this.hasColours = hasColours;
		System.out.print("DEFAULT");
        //this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Integer.valueOf(0)).withProperty(EAST, Integer.valueOf(0)).withProperty(SOUTH, Integer.valueOf(0)).withProperty(WEST, Integer.valueOf(0)));
		System.out.print(" DEFAULT");
		if (block == 2) {
			setBlockUnbreakable();
			setResistance(6000000.0F);
			setHardness(50.0F);
		}
	}
	 public int getMetaFromState(IBlockState state)
	    {
	        return 0;
	    }
	/*
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOUR).getMetaData();
	}

	public IBlockState getStateFromMeta(int meta){
		return this.blockState.getBaseState().withProperty(COLOUR, StableStoneColours.getColour(meta));
		
	}/* @Override public int damageDropped(int meta) { return meta; }
	 * 
	 * public boolean hasColours() { return hasColours; }
	 * 
	 * public MapColor getMapColor(int meta) { if (hasColours) { return MapColor.getMapColorForBlockColored(meta); } else { return super.getMapColor(meta); } }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
	 * 
	 * if (this.target == 1 || target == 3 || target == 4) { Block i1 = world.getBlock(x, y, z); return i1 == this ? false : super.shouldSideBeRendered(world, x, y, z, side); }
	 * 
	 * return true; } */


	@Override
	public boolean isOpaqueCube() {
		if (target == 1 || target == 3 || target == 4) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isFullCube() {
		if (target == 1 || target == 3 || target == 4) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		if (target == 1 || target == 3 || target == 4) {
			return false;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		//for (int i = 0; i < 16; ++i) {
		//	list.add(new ItemStack(item, 1, i));
		//}
		super.getSubBlocks(item, tab, list);
	}

	/* @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister iconRegister) { if (hasColours()) { if (colours == null) { colours = new IIcon[256]; } String colourType = ""; for (int meta = 0; meta < 16; meta++) { if (meta != 0) { colourType = "_" + getColour(meta); } for (int texID = 0; texID < 16; texID++) { if (texID == 0) { this.colours[texID + (meta * 16)] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + colourType); } else { this.colours[texID + (meta * 16)] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_" + texID + colourType);
	 * 
	 * } } } } else { if (normal == null) { normal = new IIcon[16]; } for (int i = 0; i < 16; i++) { if (i == 0) { this.normal[i] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type); } else { this.normal[i] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_" + i); } } } this.blockIcon = iconRegister.registerIcon(Calculator.modid + ":connected/" + "flawlessglass_main"); } */
	
	public enum StableStoneColours implements IStringSerializable, Predicate<StableStoneColours> {
		NONE(0), ORANGE(1), MAGENTA(2), LIGHTBLUE(3), YELLOW(4), LIME(5), PINK(6), PLAIN(7), LIGHTGREY(8), CYAN(9), PURPLE(10), BLUE(11), BROWN(12), GREEN(13), RED(14), BLACK(15);

		private int meta;

		StableStoneColours(int meta) {
			this.meta = meta;
		}

		public int getMetaData() {
			return meta;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		@Override
		public boolean apply(StableStoneColours input) {
			return input.meta==this.meta;
		}

		public static StableStoneColours getColour(int meta) {			
			for (StableStoneColours colour : StableStoneColours.values()) {
				if (colour.meta == meta) {
					return colour;
				}
			}
			return StableStoneColours.NONE;
		}

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

	/* @Override
	 * 
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) { int meta = w.getBlockMetadata(x, y, z); if (s != 1 && s != 0) { return this.getSide(w, x, y, z, s, meta); } if (s == 1) { return this.getSideT(w, x, y, z, s, meta); } if (s == 0) { return this.getSideT(w, x, y, z, s, meta); } return getI(0, meta); }
	 * 
	 * @SideOnly(Side.CLIENT) public IIcon getSpecialIcon(IBlockAccess w, int x, int y, int z, int s) { int meta = w.getBlockMetadata(x, y + 1, z); if (s != 1 && s != 0) { return this.getSide(w, x, y, z, s, meta); } else if (s == 1) { return this.getSideT(w, x, y, z, s, meta); } else if (s == 0) { return this.getSideT(w, x, y, z, s, meta); } return getI(0, meta); }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(int side, int meta) { if (this.target == 1) { return blockIcon; } return getI(0, meta); } */
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
			return checkBlockInDirection(world, x, y, z, getHorizontal(dir));
			/* EnumFacing hoz = getHorizontal(dir).getOpposite(); if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null) { Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)); int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ)); if (target != null) { if (type(world.getBlock(x, y, z), target, meta, blockMeta)) { return true; } } } */
		}
		return false;
	}

	public static boolean left(IBlockAccess world, int x, int y, int z, int dir) {
		if (dir != 0 && dir != 1) {
			return checkBlockInDirection(world, x, y, z, getHorizontal(dir));
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
		if (!(block1 instanceof ConnectedBlock) || !(block2 instanceof ConnectedBlock) || m1 == m2) {

			if (block1 instanceof ConnectedBlock) {
				ConnectedBlock c1 = (ConnectedBlock) block1;
				if (block2 instanceof ConnectedBlock) {
					if (c1.target == ((ConnectedBlock) block2).target)
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

	public static EnumFacing getHorizontal(int no) {
		EnumFacing dir = EnumFacing.getFront(no);
		if (dir == EnumFacing.NORTH) {
			return EnumFacing.EAST;
		}
		if (dir == EnumFacing.EAST) {
			return EnumFacing.SOUTH;
		}
		if (dir == EnumFacing.SOUTH) {
			return EnumFacing.WEST;
		}
		if (dir == EnumFacing.WEST) {
			return EnumFacing.NORTH;
		}
		return null;

	}

	/* @SideOnly(Side.CLIENT) public IIcon getI(int num, int meta) { if (this.hasColours()) { return this.colours[num + meta * 16]; } return this.normal[num];
	 * 
	 * }
	 * 
	 * @Override public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) if (target == 3 || target == 4) { if (player != null) { ItemStack target = player.getHeldItem(); if (target != null && (target.getItem() == Calculator.wrench)) { if (this.target == 3) { world.setBlock(x, y, z, Calculator.clearstableglassBlock); } else { world.setBlock(x, y, z, Calculator.stableglassBlock); } } } } return false; } */
	public static class Stable extends ConnectedBlock implements IStableBlock {
		public Stable() {
			super(Material.rock, "stablestone", 0, true);
		}
	}

	public static class StableRimmed extends ConnectedBlock implements IStableBlock {
		public StableRimmed() {
			super(Material.rock, "stablestonerimmed", 5, true);
		}
	}

	public static class StableBlackRimmed extends ConnectedBlock implements IStableBlock {
		public StableBlackRimmed() {
			super(Material.rock, "stablestonerimmedblack", 6, true);
		}
	}

	public static class StableGlass extends ConnectedBlock implements IStableGlass {

		public StableGlass(String string, int type) {
			super(Material.glass, string, type, false);
		}
	}

	public static class PurifiedObsidian extends ConnectedBlock implements IObsidianDrop {

		public PurifiedObsidian() {
			super(Material.rock, "purifiedobsidian", 2, false);
		}

		@Override
		public boolean canKeyDrop(World world, BlockPos pos) {
			return true;
		}

	}

	@Override
	public int[] getConnections() {
		return new int[] { target };
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos) {
		System.out.print("ACTUAL");
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		return state.withProperty(NORTH, getSide(w, x, y, z, 2)).withProperty(EAST, getSide(w, x, y, z, 5)).withProperty(SOUTH, getSide(w, x, y, z, 3)).withProperty(WEST, getSide(w, x, y, z, 4)).withProperty(DOWN, getSideT(w, x, y, z, 0)).withProperty(UP, getSideT(w, x, y, z, 1));
		//return super.getActualState(state, w, pos);
	}
	

	    /**
	     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	     * metadata, such as fence connections.
	     */
	  /*
	    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	    {
	        return state.withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north()))).withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east()))).withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south()))).withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west())));
	    }
	*/
	    protected BlockState createBlockState()
	    {
	        return new BlockState(this, new IProperty[] {NORTH, EAST, WEST, SOUTH});
	    }
}
