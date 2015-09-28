package sonar.calculator.mod.common.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
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
	private IIcon opaqueleaves, transparentleaves;

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
		byte radius = 1;
		int bounds = radius + 1;
		if (world.checkChunksExist(x - bounds, y - bounds, z - bounds, x + bounds, y + bounds, z + bounds)) {
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					for (int k = -radius; k <= radius; k++) {
						Block block = world.getBlock(x + i, y + j, z + k);
						if (block.isLeaves(world, x, y, z)) {
							block.beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			int l = world.getBlockMetadata(x, y, z);
			if (((l & 0x8) != 0) && ((l & 0x4) == 0)) {
				byte b0 = 4;
				int i1 = b0 + 1;
				byte b1 = 32;
				int j1 = b1 * b1;
				int k1 = b1 / 2;
				if (this.adjacentTreeBlocks == null) {
					this.adjacentTreeBlocks = new int[b1 * b1 * b1];
				}
				if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
					for (int l1 = -b0; l1 <= b0; l1++) {
						for (int i2 = -b0; i2 <= b0; i2++) {
							for (int j2 = -b0; j2 <= b0; j2++) {
								Block block = world.getBlock(x + l1, y + i2, z + j2);
								if ((block != null) && (block.canSustainLeaves(world, x + l1, y + i2, z + j2))) {
									this.adjacentTreeBlocks[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = 0;
								} else if ((block != null) && (block.isLeaves(world, x + l1, y + i2, z + j2))) {
									this.adjacentTreeBlocks[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = -2;
								} else {
									this.adjacentTreeBlocks[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = -1;
								}
							}
						}
					}
					for (i1 = 1; i1 <= 4; i1++) {
						for (int i2 = -b0; i2 <= b0; i2++) {
							for (int j2 = -b0; j2 <= b0; j2++) {
								for (int k2 = -b0; k2 <= b0; k2++) {
									if (this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1)] == i1 - 1) {
										if (this.adjacentTreeBlocks[((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1)] = i1;
										}
										if (this.adjacentTreeBlocks[((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1)] = i1;
										}
										if (this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1)] = i1;
										}
										if (this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1)] = i1;
										}
										if (this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1)] = i1;
										}
										if (this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1)] == -2) {
											this.adjacentTreeBlocks[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1)] = i1;
										}
									}
								}
							}
						}
					}
				}
				int l1 = this.adjacentTreeBlocks[(k1 * j1 + k1 * b1 + k1)];
				if (l1 >= 0) {
					world.setBlockMetadataWithNotify(x, y, z, l & 0xFFFFFFF7, 4);
				} else {
					removeLeaves(world, x, y, z);
				}
			}
		}
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

	private void removeLeaves(World world, int x, int y, int z) {
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
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
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 0x8, 4);
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (!Minecraft.isFancyGraphicsEnabled()) {
			return this.opaqueleaves;
		}
		return this.transparentleaves;
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
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves_amethyst");
			break;
		case 1:
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves_tanzanite");
			break;

		case 2:
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_pear_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves_pear");
			break;

		case 3:
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_diamond_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves_diamond");
			break;
		case 4:
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves");
			break;
		case 5:
			this.opaqueleaves = iconRegister.registerIcon("Calculator:wood/leaves_dia_opaque");
			this.transparentleaves = iconRegister.registerIcon("Calculator:wood/leaves_dia");
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
		case 4:
			ret.add(new ItemStack(Calculator.leaves, 1, 0));
			break;
		case 5:
			ret.add(new ItemStack(Calculator.diamondleaves, 1, 0));
			break;
		}
		return ret;
	}

}
