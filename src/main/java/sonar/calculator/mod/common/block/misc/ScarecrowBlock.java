package sonar.calculator.mod.common.block.misc;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.SonarHelper;

public class ScarecrowBlock extends Block implements IDismantleable {
	
	public ScarecrowBlock() {
		super(Material.cloth);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		world.setBlockToAir(x, y, z);
		if (world.getBlock(x, y - 1, z) == Calculator.scarecrow) {
			TileEntity i = world.getTileEntity(x, y - 1, z);
			Block bi = world.getBlock(x, y - 1, z);
			bi.dropBlockAsItem(world, x, y - 1, z, world.getBlockMetadata(x, y - 1, z), 0);
			world.setBlockToAir(x, y - 1, z);
		} else if (world.getBlock(x, y - 2, z) == Calculator.scarecrow) {
			TileEntity i = world.getTileEntity(x, y - 2, z);
			Block bi = world.getBlock(x, y - 2, z);
			bi.dropBlockAsItem(world, x, y - 2, z, world.getBlockMetadata(x, y - 2, z), 0);
			world.setBlockToAir(x, y - 2, z);
		}

	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		EnumFacing dir = EnumFacing.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();

		if (world.getBlock(x, y - 2, z) == Calculator.scarecrow) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);
		}
		if (world.getBlock(x, y - 1, z) == Calculator.scarecrow) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {

		SonarHelper.dropTile(player, world.getBlock(x, y, z), world, x, y, z);
		return null;
	}

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
		return true;
	}
}
