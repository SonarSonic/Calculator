package sonar.calculator.mod.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.utils.helpers.CalculatorTreeBuilder;
import sonar.core.utils.ISpecialTooltip;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorSaplings extends BlockFlower implements ISpecialTooltip {
	int type;

	public CalculatorSaplings(int type) {
		super(0);
		this.type = type;
		this.stepSound = soundTypeGrass;
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
	}

	@Override
	public boolean canPlaceBlockOn(Block block) {
		return type==3 ? block == Calculator.end_diamond_block : super.canPlaceBlockOn(block);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return type==3 ? canPlaceBlockOn(world.getBlock(x, y - 1, z)) : super.canBlockStay(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg) {
		if (type == 0) {
			blockIcon = iconReg.registerIcon("Calculator:wood/sapling_amethyst");
		}
		if (type == 1) {
			blockIcon = iconReg.registerIcon("Calculator:wood/sapling_tanzanite");
		}
		if (type == 2) {
			blockIcon = iconReg.registerIcon("Calculator:wood/sapling_pear");
		}
		if (type == 3) {
			blockIcon = iconReg.registerIcon("Calculator:wood/sapling_diamond");
		}

	}

	public boolean isSameSapling(World world, int x, int y, int z, int meta) {
		return (world.getBlock(x, y, z) == this) && ((world.getBlockMetadata(x, y, z) & 0x3) == meta);
	}

	public int setUnlocalizedName(int type) {
		return type & 0x3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List listSaplings) {
		listSaplings.add(new ItemStack(item, 1, 0));
	}

	public void markOrGrowMarked(World world, int x, int y, int z, Random rand) {
		int l = world.getBlockMetadata(x, y, z);
		if ((l & 0x8) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, l | 0x8, 4);
		} else {
			growTree(world, x, y, z, rand);
		}
	}

	public void growTree(World world, int x, int y, int z, Random rand) {
		if (!TerrainGen.saplingGrowTree(world, rand, x, y, z)) {
			return;
		}
		int l = world.getBlockMetadata(x, y, z) & 0x7;
		Object object = null;
		int i1 = 0;
		int j1 = 0;

		switch (type) {
		case 0:
			object = new CalculatorTreeBuilder(true, Calculator.AmethystSapling, Calculator.amethystLeaf, Calculator.amethystLog);
			break;

		case 1:
			object = new CalculatorTreeBuilder(true, Calculator.tanzaniteSapling, Calculator.tanzaniteLeaf, Calculator.tanzaniteLog);
			break;

		case 2:
			object = new CalculatorTreeBuilder(true, Calculator.PearSapling, Calculator.pearLeaf, Calculator.pearLog);
			break;

		case 3:
			object = new CalculatorTreeBuilder(true, Calculator.diamondSapling, Calculator.diamondLeaf, Calculator.diamondLog);
			break;
		}

		world.setBlock(x, y, z, Blocks.air, 0, 4);
		if (!((WorldGenerator) object).generate(world, rand, x + i1, y, z + j1)) {
			world.setBlock(x, y, z, this, l, 4);
		}else{
			world.setBlock(x, y-1, z, Blocks.grass);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			super.updateTick(world, x, y, z, rand);
			if (world.getBlock(x, y, z) == this) {
				if ((world.getBlockLightValue(x, y + 1, z) >= 9) && (rand.nextInt(7) == 0)) {
					markOrGrowMarked(world, x, y, z, rand);
				}
			}
		}
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		if(type==3){
			list.add("Must be planted on an End Diamond Block");
		}
		
	}
}
