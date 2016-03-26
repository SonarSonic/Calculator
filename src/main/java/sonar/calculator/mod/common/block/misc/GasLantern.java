package sonar.calculator.mod.common.block.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.BlockInteraction;
import cofh.api.block.IDismantleable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GasLantern extends SonarMachineBlock implements IDismantleable {

	private static boolean keepInventory;
	private Random rand = new Random();

	public final boolean isActive;

	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public GasLantern(boolean active) {
		super(SonarMaterials.machine);
		this.isActive = active;
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.Lantern, world, x, y, z);
			}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (isActive) {
			float x1 = x + random.nextFloat();
			float y1 = y + 0.5F;
			float z1 = z + random.nextFloat();

			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		}
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
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		if (!keepInventory) {
			TileEntity entity = world.getTileEntity(x, y, z);

			if (entity != null && entity instanceof TileEntityGasLantern) {
				TileEntityGasLantern tileentity = (TileEntityGasLantern) world.getTileEntity(x, y, z);
				for (int i = 0; i < tileentity.getSizeInventory(); i++) {
					ItemStack itemstack = tileentity.getStackInSlot(i);

					if (itemstack != null) {
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int j = this.rand.nextInt(21) + 10;

							if (j > itemstack.stackSize) {
								j = itemstack.stackSize;
							}

							itemstack.stackSize -= j;

							EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound()) {
								item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							world.spawnEntityInWorld(item);
						}
					}
				}

				world.func_147453_f(x, y, z, oldblock);
			}
		}
			world.removeTileEntity(x, y, z);		
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack) {

		Block north = world.getBlock(x + (ForgeDirection.NORTH.offsetX), y, z + (ForgeDirection.NORTH.offsetZ));
		Block south = world.getBlock(x + (ForgeDirection.SOUTH.offsetX), y, z + (ForgeDirection.SOUTH.offsetZ));
		Block east = world.getBlock(x + (ForgeDirection.EAST.offsetX), y, z + (ForgeDirection.EAST.offsetZ));
		Block west = world.getBlock(x + (ForgeDirection.WEST.offsetX), y, z + (ForgeDirection.WEST.offsetZ));
		Block down = world.getBlock(x, y - 1, z);

		if (down != null && down.getMaterial().isSolid() && down != Calculator.gas_lantern_on && down != Calculator.gas_lantern_off && down != Calculator.basic_lantern) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		} else if (north != null && north.getMaterial().isSolid() && north != Calculator.gas_lantern_on && north != Calculator.gas_lantern_off

		&& north != Calculator.basic_lantern && world.isSideSolid(x + (ForgeDirection.NORTH.offsetX), y, z + (ForgeDirection.NORTH.offsetZ), ForgeDirection.NORTH)) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		} else if (south != null && south.getMaterial().isSolid() && south != Calculator.gas_lantern_on && south != Calculator.gas_lantern_off

		&& south != Calculator.basic_lantern && world.isSideSolid(x + (ForgeDirection.SOUTH.offsetX), y, z + (ForgeDirection.SOUTH.offsetZ), ForgeDirection.SOUTH)) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		} else if (east != null && east.getMaterial().isSolid() && east != Calculator.gas_lantern_on && east != Calculator.gas_lantern_off

		&& east != Calculator.basic_lantern && world.isSideSolid(x + (ForgeDirection.EAST.offsetX), y, z + (ForgeDirection.EAST.offsetZ), ForgeDirection.EAST)) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);

		} else if (west != null && west.getMaterial().isSolid() && west != Calculator.gas_lantern_on && west != Calculator.gas_lantern_off

		&& west != Calculator.basic_lantern && world.isSideSolid(x + (ForgeDirection.WEST.offsetX), y, z + (ForgeDirection.WEST.offsetZ), ForgeDirection.WEST)) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		else {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		this.onBlockPlacedBy(world, x, y, z, null, null);
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	public static void updateLatternBlockState(boolean bool, World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepInventory = true;

		if (bool) {
			world.setBlock(x, y, z, Calculator.gas_lantern_on);
		} else {
			world.setBlock(x, y, z, Calculator.gas_lantern_off);
		}

		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();

		setBlockBounds(0.3F + (dir.offsetX * 0.32F), 0.0F + getY(metadata), 0.3F + (dir.offsetZ * 0.32F), 0.7F + (dir.offsetX * 0.32F), 0.7F + getY(metadata), 0.7F + (dir.offsetZ * 0.32F));

	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity) {
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();

		this.setBlockBounds(0.3F + (dir.offsetX * 0.32F), 0.0F + getY(metadata), 0.3F + (dir.offsetZ * 0.32F), 0.7F + (dir.offsetX * 0.32F), 0.7F + getY(metadata), 0.7F + (dir.offsetZ * 0.32F));
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
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityGasLantern();
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Calculator.gas_lantern_off);
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
