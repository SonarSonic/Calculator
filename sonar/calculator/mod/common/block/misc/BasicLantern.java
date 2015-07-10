package sonar.calculator.mod.common.block.misc;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.misc.TileEntityBasicLantern;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicLantern extends BlockContainer implements IWrench {

	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public BasicLantern() {
		super(SonarMaterials.machine);
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		this.icon = register.registerIcon("Calculator:reinforcedstone");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return this.icon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityplayer, ItemStack itemstack) {
		if(world.isRemote){
			System.out.print("client" +world.getBiomeGenForCoordsBody(x, z).getFloatTemperature(x, y, z));
			}else{
				System.out.print("server" +world.getBiomeGenForCoordsBody(x, z).getFloatTemperature(x, y, z));
			}
			
		
		Block north = world.getBlock(x + (ForgeDirection.NORTH.offsetX), y, z
				+ (ForgeDirection.NORTH.offsetZ));
		Block south = world.getBlock(x + (ForgeDirection.SOUTH.offsetX), y, z
				+ (ForgeDirection.SOUTH.offsetZ));
		Block east = world.getBlock(x + (ForgeDirection.EAST.offsetX), y, z
				+ (ForgeDirection.EAST.offsetZ));
		Block west = world.getBlock(x + (ForgeDirection.WEST.offsetX), y, z
				+ (ForgeDirection.WEST.offsetZ));
		Block down = world.getBlock(x, y - 1, z);

		if (down != null && down.getMaterial().isSolid()
				&& down != Calculator.gas_lantern_on
				&& down != Calculator.gas_lantern_off
				&& down != Calculator.basic_lantern) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		} else if (north != null
				&& north.getMaterial().isSolid()
				&& north != Calculator.gas_lantern_on
				&& north != Calculator.gas_lantern_off

				&& north != Calculator.basic_lantern
				&& world.isSideSolid(x + (ForgeDirection.NORTH.offsetX), y, z
						+ (ForgeDirection.NORTH.offsetZ), ForgeDirection.NORTH)) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		} else if (south != null
				&& south.getMaterial().isSolid()
				&& south != Calculator.gas_lantern_on
				&& south != Calculator.gas_lantern_off

				&& south != Calculator.basic_lantern
				&& world.isSideSolid(x + (ForgeDirection.SOUTH.offsetX), y, z
						+ (ForgeDirection.SOUTH.offsetZ), ForgeDirection.SOUTH)) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		} else if (east != null
				&& east.getMaterial().isSolid()
				&& east != Calculator.gas_lantern_on
				&& east != Calculator.gas_lantern_off

				&& east != Calculator.basic_lantern
				&& world.isSideSolid(x + (ForgeDirection.EAST.offsetX), y, z
						+ (ForgeDirection.EAST.offsetZ), ForgeDirection.EAST)) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);

		} else if (west != null
				&& west.getMaterial().isSolid()
				&& west != Calculator.gas_lantern_on
				&& west != Calculator.gas_lantern_off

				&& west != Calculator.basic_lantern
				&& world.isSideSolid(x + (ForgeDirection.WEST.offsetX), y, z
						+ (ForgeDirection.WEST.offsetZ), ForgeDirection.WEST)) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		else {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		}

	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z,
			Block block) {
		this.onBlockPlacedBy(world, x, y, z, null, null);
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(
				world.getBlockMetadata(x, y, z)).getOpposite();

		setBlockBounds(0.3F + (dir.offsetX * 0.32F), 0.0F + getY(metadata),
				0.3F + (dir.offsetZ * 0.32F), 0.7F + (dir.offsetX * 0.32F),
				0.7F + getY(metadata), 0.7F + (dir.offsetZ * 0.32F));

	}
	@Override
	 public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(
				world.getBlockMetadata(x, y, z)).getOpposite();

		this.setBlockBounds(0.3F + (dir.offsetX * 0.32F), 0.0F + getY(metadata),
				0.3F + (dir.offsetZ * 0.32F), 0.7F + (dir.offsetX * 0.32F),
				0.7F + getY(metadata), 0.7F + (dir.offsetZ * 0.32F));
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

	}

	public float getY(int meta) {
		if (meta == 0) {
			return 0.0F;
		} else {
			return 0.1F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z,
			Random random) {
		float x1 = x + random.nextFloat();
		float y1 = y + 0.5F;
		float z1 = z + random.nextFloat();
		world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityBasicLantern();
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Calculator.basic_lantern);
	}

	@Override
	public boolean canWrench() {
		return true;
	}


}
