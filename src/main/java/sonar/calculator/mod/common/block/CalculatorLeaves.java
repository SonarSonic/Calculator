package sonar.calculator.mod.common.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorLeaves extends BlockLeavesBase implements IShearable {

	int leafType;
	int[] adjacentTreeBlocks;
	@SideOnly(Side.CLIENT)
	private IIcon opaqueLeaves, transparentLeaves, emptyOpaqueLeaves, emptyTransparentLeaves;

	public CalculatorLeaves(int type) {
		super(Material.leaves, true);
		this.leafType = type;
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {

	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		// they don't disappear anymore
		int blocks = 0;
		for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (!block.isAir(world, x, y, z)) {
				blocks++;
			}
		}
		if (blocks != 6) {
			int randInt = 0;
			if (leafType == 3) {
				randInt = rand.nextInt(25);
			}else{
				randInt = rand.nextInt(10);
			}
			if (randInt == 2) {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta != 5) {
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
				}
			}
		}
	}

	@Override
	public int tickRate(World world) {
		return 10;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if ((world.canLightningStrikeAt(x, y + 1, z)) && (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) && (random.nextInt(15) == 1)) {
			double d0 = x + random.nextFloat();
			double d1 = y - 0.05D;
			double d2 = z + random.nextFloat();
			world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		super.randomDisplayTick(world, x, y, z, random);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		return 150;
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta > 2) {
			if (!Minecraft.isFancyGraphicsEnabled()) {
				return this.opaqueLeaves;
			}
			return this.transparentLeaves;
		} else {
			if (!Minecraft.isFancyGraphicsEnabled()) {
				return this.emptyOpaqueLeaves;
			}
			return this.emptyTransparentLeaves;
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		switch (leafType) {
		case 0:
			this.opaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst_opaque");
			this.transparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst");
			this.emptyOpaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst_empty_opaque");
			this.emptyTransparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst_empty");
			break;
		case 1:
			this.opaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite_opaque");
			this.transparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite");
			this.emptyOpaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite_empty_opaque");
			this.emptyTransparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite_empty");
			break;

		case 2:
			this.opaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_pear_opaque");
			this.transparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_pear");
			this.emptyOpaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_opaque");
			this.emptyTransparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves");
			break;

		case 3:
			this.opaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_diamond_opaque");
			this.transparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_diamond");
			this.emptyOpaqueLeaves = iconRegister.registerIcon("Calculator:wood/leaves_dia_opaque");
			this.emptyTransparentLeaves = iconRegister.registerIcon("Calculator:wood/leaves_dia");
			break;
		}
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList();
		int meta = damageDropped(world.getBlockMetadata(x, y, z));

		switch (leafType) {
		case 0:
			ret.add(new ItemStack(Calculator.amethystLeaf, 1, 0));
			break;
		case 1:
			ret.add(new ItemStack(Calculator.tanzaniteLeaf, 1, 0));
			break;
		case 2:
			ret.add(new ItemStack(Calculator.pearLeaf, 1, 0));
			break;
		case 3:
			ret.add(new ItemStack(Calculator.diamondLeaf, 1, 0));
			break;
		}
		return ret;
	}
}
