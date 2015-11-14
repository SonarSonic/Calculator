package sonar.calculator.mod.common.block;

import java.util.List;

import cofh.api.item.IToolHammer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStableBlock;
import sonar.calculator.mod.api.IStableGlass;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConnectedBlock extends Block {

	public String type;
	public int target;
	public boolean hasColours = false;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] colours,normal;
	
	public ConnectedBlock(Material material, String name, int block, boolean hasColours) {
		super(material);
		this.target = block;
		this.type = name;
		this.hasColours = hasColours;
		if (block == 2) {
			setBlockUnbreakable();
			setResistance(6000000.0F);
			setHardness(50.0F);

		}
	}
	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	public boolean hasColours() {
		return hasColours;
	}

	public MapColor getMapColor(int meta) {
		if (hasColours) {
			return MapColor.getMapColorForBlockColored(meta);
		} else {
			return super.getMapColor(meta);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {

		if (this.target == 1 || target == 3 || target == 4) {
			Block i1 = world.getBlock(x, y, z);
			return i1 == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
		}

		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		if (target == 1 || target == 3 || target == 4) {
			return false;
		}
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		if (target == 1 || target == 3 || target == 4) {
			return false;
		}
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		if (target == 1 || target == 3 || target == 4) {
			return 1;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (hasColours()) {
			for (int i = 0; i < 16; ++i) {
				list.add(new ItemStack(item, 1, i));
			}
		} else {
			super.getSubBlocks(item, tab, list);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if (hasColours()) {
			if(colours==null){
				colours= new IIcon[256];
			}
			String colourType = "";
			for (int meta = 0; meta < 16; meta++) {
				if (meta != 0) {
					colourType = "_" + getColour(meta);
				}
				for (int texID = 0; texID < 16; texID++) {
					if (texID == 0) {
						this.colours[texID + (meta * 16)] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + colourType);
					} else {
						this.colours[texID + (meta * 16)] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_" + texID + colourType);

					}
				}
			}
		} else {
			if(normal==null){
				normal= new IIcon[16];
			}
			for (int i = 0; i < 16; i++) {
				if (i == 0) {
					this.normal[i] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type);
				} else {
					this.normal[i] = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_" + i);
				}
			}
		}
		this.blockIcon = iconRegister.registerIcon(Calculator.modid + ":connected/" + "flawlessglass_main");
	}

	public String getColour(int meta) {
		switch (meta) {
		case 1:
			return "orange";
		case 2:
			return "magenta";
		case 3:
			return "lightblue";
		case 4:
			return "yellow";
		case 5:
			return "lime";
		case 6:
			return "pink";
		case 7:
			return "plain";
		case 8:
			return "lightgrey";
		case 9:
			return "cyan";
		case 10:
			return "purple";
		case 11:
			return "blue";
		case 12:
			return "brown";
		case 13:
			return "green";
		case 14:
			return "red";
		case 15:
			return "black";
		default:
			return "";
		}
	}

	public IIcon getSide(IBlockAccess w, int x, int y, int z, int s, int meta) {
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(0, meta);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(1, meta);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(2, meta);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(3, meta);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(4, meta);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(5, meta);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(6, meta);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(7, meta);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(8, meta);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(9, meta);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(10, meta);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(11, meta);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(12, meta);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(13, meta);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(14, meta);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(15, meta);
		}
		return getI(0, meta);
	}

	public IIcon getSideT(IBlockAccess w, int x, int y, int z, int s, int meta) {
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(0, meta);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(1, meta);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(2, meta);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(3, meta);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(4, meta);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(5, meta);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(6, meta);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(7, meta);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(8, meta);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(9, meta);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(10, meta);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(11, meta);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(12, meta);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(13, meta);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(14, meta);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(15, meta);
		}
		return getI(0, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		int meta = w.getBlockMetadata(x, y, z);
		if (s != 1 && s != 0) {
			return this.getSide(w, x, y, z, s, meta);
		}
		if (s == 1) {
			return this.getSideT(w, x, y, z, s, meta);
		}
		if (s == 0) {
			return this.getSideT(w, x, y, z, s, meta);
		}
		return getI(0, meta);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (this.target == 1) {
			return blockIcon;
		}
		return getI(0, meta);
	}

	public boolean up(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		Block target = world.getBlock(x, y + 1, z);
		int blockMeta = world.getBlockMetadata(x, y + 1, z);
		if (target != null) {
			if (type(target) && blockMeta==meta) {
				return true;
			}
		}
		return false;
	}

	public boolean down(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		Block target = world.getBlock(x, y - 1, z);
		int blockMeta = world.getBlockMetadata(x, y - 1, z);
		if (target != null) {
			if (type(target) && blockMeta==meta) {
				return true;
			}
		}
		return false;
	}

	public boolean right(IBlockAccess world, int x, int y, int z, int dir) {
		int meta = world.getBlockMetadata(x, y, z);
		if (dir != 0 && dir != 1) {
			ForgeDirection hoz = getHorizontal(dir).getOpposite();
			if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null) {
				Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
				int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
				if (type(target) && blockMeta==meta) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean left(IBlockAccess world, int x, int y, int z, int dir) {
		int meta = world.getBlockMetadata(x, y, z);
		if (dir != 0 && dir != 1) {
			ForgeDirection hoz = getHorizontal(dir);
			Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
			int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
			if (target != null && blockMeta==meta) {
				return type(target);

			}
		}
		return false;
	}

	public boolean upT(IBlockAccess world, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection hoz = ForgeDirection.NORTH;
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null && blockMeta==meta) {
			return type(target);

		}
		return false;
	}

	public boolean downT(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection hoz = ForgeDirection.NORTH.getOpposite();
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null && blockMeta==meta) {
			return type(target);

		}
		return false;
	}

	public boolean rightT(IBlockAccess world, int x, int y, int z, int dir) {
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection hoz = ForgeDirection.EAST;
		int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null && blockMeta==meta) {
			Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
			return type(target);

		}
		return false;
	}

	public boolean leftT(IBlockAccess world, int x, int y, int z, int dir) {
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection hoz = ForgeDirection.EAST.getOpposite();
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		int blockMeta = world.getBlockMetadata(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null && blockMeta==meta) {
			return type(target);

		}
		return false;
	}

	public boolean type(Block block) {
		switch (target) {
		case 0:
			if (block == Calculator.stablestoneBlock || block == Calculator.flawlessGreenhouse || block == Calculator.carbondioxideGenerator) {
				return true;
			}
			break;
		case 1:
			if (block == Calculator.flawlessGlass) {
				return true;
			}
			break;
		case 2:
			if (block == Calculator.purifiedobsidianBlock) {
				return true;
			}
			break;
		case 3:
			if (block == Calculator.stableglassBlock) {
				return true;
			}
			break;
		case 4:
			if (block == Calculator.clearstableglassBlock) {
				return true;
			}
			break;
		case 5:
			if (block == Calculator.stablestonerimmedBlock || block == Calculator.flawlessGreenhouse || block == Calculator.carbondioxideGenerator) {
				return true;
			}
			break;
		case 6:
			if (block == Calculator.stablestonerimmedblackBlock || block == Calculator.flawlessGreenhouse || block == Calculator.carbondioxideGenerator) {
				return true;
			}
			break;
		}

		return false;

	}

	public ForgeDirection getHorizontal(int no) {
		ForgeDirection dir = ForgeDirection.getOrientation(no);
		if (dir == ForgeDirection.NORTH) {
			return ForgeDirection.EAST;
		}
		if (dir == ForgeDirection.EAST) {
			return ForgeDirection.SOUTH;
		}
		if (dir == ForgeDirection.SOUTH) {
			return ForgeDirection.WEST;
		}
		if (dir == ForgeDirection.WEST) {
			return ForgeDirection.NORTH;
		}
		return null;

	}

	@SideOnly(Side.CLIENT)
	public IIcon getI(int num, int meta) {
		if (this.hasColours()) {
			return this.colours[num + meta * 16];
		}
		return this.normal[num];

	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (target == 3 || target == 4) {
			if (player != null) {
				ItemStack target = player.getHeldItem();
				if (target != null && (target.getItem() == Calculator.wrench || target.getItem() instanceof IToolHammer)) {
					if (this.target == 3) {
						world.setBlock(x, y, z, Calculator.clearstableglassBlock);
					} else {
						world.setBlock(x, y, z, Calculator.stableglassBlock);
					}
				}
			}
		}
		return false;
	}

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
}
