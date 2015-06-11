package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConnectedBlock extends Block {

	public String type;
	public int target;

	@SideOnly(Side.CLIENT)
	private IIcon a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p;

	public ConnectedBlock(Material material, String name, int block) {
		super(material);
		this.target = block;
		this.type = name;
		if (block == 2) {
			setBlockUnbreakable();
			setResistance(6000000.0F);
			setHardness(50.0F);

		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		if (this.target == 1) {
			Block i1 = world.getBlock(x, y, z);
			return i1 == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
		}
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		if (target == 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		if (target == 1) {
			return false;
		}
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		if (target == 1) {
			return 1;
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.a = iconRegister.registerIcon(Calculator.modid + ":connected/" + type);
		this.b = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_1");
		this.c = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_2");
		this.d = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_3");
		this.e = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_4");
		this.f = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_5");
		this.g = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_6");
		this.h = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_7");
		this.i = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_8");
		this.j = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_9");
		this.k = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_10");
		this.l = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_11");
		this.m = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_12");
		this.n = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_13");
		this.o = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_14");
		this.p = iconRegister.registerIcon(Calculator.modid + ":connected/" + type + "_15");

		this.blockIcon = iconRegister.registerIcon(Calculator.modid + ":connected/" + "flawlessglass_main");
	}

	public IIcon getSide(IBlockAccess w, int x, int y, int z, int s) {
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(0);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(1);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(2);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(3);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(4);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(5);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(6);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(7);
		}
		if (!up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(8);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(9);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(10);
		}
		if (!up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(11);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && !right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(12);
		}
		if (up(w, x, y, z) && !down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(13);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && !left(w, x, y, z, s)) {
			return getI(14);
		}
		if (up(w, x, y, z) && down(w, x, y, z) && right(w, x, y, z, s) && left(w, x, y, z, s)) {
			return getI(15);
		}
		return a;
	}

	public IIcon getSideT(IBlockAccess w, int x, int y, int z, int s) {
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(0);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(1);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(2);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(3);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(4);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(5);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(6);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(7);
		}
		if (!upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(8);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(9);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(10);
		}
		if (!upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(11);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && !rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(12);
		}
		if (upT(w, x, y, z) && !downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(13);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && !leftT(w, x, y, z, s)) {
			return getI(14);
		}
		if (upT(w, x, y, z) && downT(w, x, y, z) && rightT(w, x, y, z, s) && leftT(w, x, y, z, s)) {
			return getI(15);
		}
		return a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		if (s != 1 && s != 0) {
			return this.getSide(w, x, y, z, s);
		}
		if (s == 1) {
			return this.getSideT(w, x, y, z, s);
		}
		if (s == 0) {
			return this.getSideT(w, x, y, z, s);
		}
		return a;

	}

	@Override
	public IIcon getIcon(int meta, int side) {
		if (this.target == 1) {
			return blockIcon;
		}
		return a;
	}

	public boolean up(IBlockAccess world, int x, int y, int z) {
		Block target = world.getBlock(x, y + 1, z);
		if (target != null) {
			if (type(target)) {
				return true;
			}
		}
		return false;
	}

	public boolean down(IBlockAccess world, int x, int y, int z) {
		Block target = world.getBlock(x, y - 1, z);
		if (target != null) {
			if (type(target)) {
				return true;
			}
		}
		return false;
	}

	public boolean right(IBlockAccess world, int x, int y, int z, int dir) {
		if (dir != 0 && dir != 1) {
			ForgeDirection hoz = getHorizontal(dir).getOpposite();
			if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null) {
				Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
				if (type(target)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean left(IBlockAccess world, int x, int y, int z, int dir) {
		if (dir != 0 && dir != 1) {
			ForgeDirection hoz = getHorizontal(dir);
			Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
			if (target != null) {
				if (type(target)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean upT(IBlockAccess world, int x, int y, int z) {

		ForgeDirection hoz = ForgeDirection.NORTH;
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null) {
			if (type(target)) {
				return true;
			}
		}
		return false;
	}

	public boolean downT(IBlockAccess world, int x, int y, int z) {

		ForgeDirection hoz = ForgeDirection.NORTH.getOpposite();
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null) {
			if (type(target)) {
				return true;
			}
		}
		return false;
	}

	public boolean rightT(IBlockAccess world, int x, int y, int z, int dir) {
		ForgeDirection hoz = ForgeDirection.EAST;
		if (world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ)) != null) {
			Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
			if (type(target)) {
				return true;
			}

		}
		return false;
	}

	public boolean leftT(IBlockAccess world, int x, int y, int z, int dir) {
		ForgeDirection hoz = ForgeDirection.EAST.getOpposite();
		Block target = world.getBlock(x + (hoz.offsetX), y, z + (hoz.offsetZ));
		if (target != null) {
			if (type(target)) {
				return true;
			}

		}
		return false;
	}

	public boolean type(Block block) {
		if (this.target == 0 && block == Calculator.stablestoneBlock) {
			return true;
		}
		if (this.target == 0 && block == Calculator.flawlessGreenhouse) {
			return true;
		}
		if (this.target == 0 && block == Calculator.carbondioxideGenerator) {
			return true;
		}
		if (this.target == 1 && block == Calculator.flawlessGlass) {
			return true;
		}
		if (this.target == 2 && block == Calculator.purifiedobsidianBlock) {
			return true;
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

	public IIcon getI(int num) {
		switch (num) {
		case 0:
			return a;
		case 1:
			return b;
		case 2:
			return c;
		case 3:
			return d;
		case 4:
			return e;
		case 5:
			return f;
		case 6:
			return g;
		case 7:
			return h;
		case 8:
			return i;
		case 9:
			return j;
		case 10:
			return k;
		case 11:
			return l;
		case 12:
			return m;
		case 13:
			return n;
		case 14:
			return o;
		case 15:
			return p;
		}
		return a;

	}
}
