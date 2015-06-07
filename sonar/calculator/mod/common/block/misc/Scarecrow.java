package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Scarecrow extends BlockContainer implements IWrench {

	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public Scarecrow() {
		super(Material.cloth);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.6F, 1.0F);
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



	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block b1 = world.getBlock(x, y, z - 1);
			Block b2 = world.getBlock(x, y, z + 1);
			Block b3 = world.getBlock(x - 1, y, z);
			Block b4 = world.getBlock(x + 1, y, z);

			byte b0 = 3;

			if ((b1.func_149730_j()) && (!b2.func_149730_j())) {
				b0 = 3;
			}

			if ((b2.func_149730_j()) && (!b1.func_149730_j())) {
				b0 = 2;
			}

			if ((b3.func_149730_j()) && (!b4.func_149730_j())) {
				b0 = 5;
			}

			if ((b4.func_149730_j()) && (!b3.func_149730_j())) {
				b0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, x, b0, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityplayer, ItemStack itemstack) {
		int l = MathHelper
				.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	private void setBlocks(World world, int x, int y, int z, int i) {
		world.setBlock(x, y + 1, z, Calculator.scarecrowBlock,i,2);
		world.setBlock(x, y + 2, z, Calculator.scarecrowBlock,i,2);

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
		setBlocks(world, x, y, z, world.getBlockMetadata(x, y, z));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock,
			int oldMetadata) {
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
		world.setBlockToAir(x, y + 1, z);
		world.setBlockToAir(x, y + 2, z);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityScarecrow();
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Calculator.scarecrow);
	}

	@Override
	public boolean canWrench() {
		return true;
	}

}
