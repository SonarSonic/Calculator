package sonar.calculator.mod.common.block.misc;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.nutrition.IHealthProcessor;
import sonar.calculator.mod.api.nutrition.IHungerProcessor;
import sonar.core.common.block.ConnectedBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.ISpecialTooltip;

public class Piping extends ConnectedBlock implements ISpecialTooltip {

	public Piping(Material material, int target) {
		super(material, target);
		this.setBlockBounds(0.2625F, 0.2625F, 0.2625F, 0.7375F, 0.7375F, 0.7375F);
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		float minX = 0.2625F, maxX = 0.7375F;
		float minZ = 0.2625F, maxZ = 0.7375F;
		float minY = 0.2625F, maxY = 0.7375F;

		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.NORTH)) {
			minZ = 0;
		}
		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.SOUTH)) {
			maxZ = 1;
		}
		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.EAST)) {
			maxX = 1;
		}
		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.WEST)) {
			minX = 0;
		}
		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.UP)) {
			maxY = 1;
		}
		if (checkBlockInDirection(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.DOWN)) {
			minY = 0;
		}
		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);

	}
	/*
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List list, Entity entity) {
		double minX = 0.2625, maxX = 0.7375;
		double minZ = 0.2625, maxZ = 0.7375;
		double minY = 0.2625, maxY = 0.7375;

		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.NORTH)) {
			maxX = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.SOUTH)) {
			minX = 0;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.EAST)) {
			maxZ = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.WEST)) {
			minZ = 0;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.UP)) {
			maxY = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.DOWN)) {
			minY = 0;
		}
		list.add(axis);
		list.add(AxisAlignedBB.fromBounds(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX, pos.getY() + maxY, pos.getZ() + maxZ));
	}

	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		double minX = 0.2625, maxX = 0.7375;
		double minZ = 0.2625, maxZ = 0.7375;
		double minY = 0.2625, maxY = 0.7375;
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.NORTH)) {
			maxX = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.SOUTH)) {
			minX = 0;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.EAST)) {
			maxZ = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.WEST)) {
			minZ = 0;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.UP)) {
			maxY = 1;
		}
		if (checkBlockInDirection(world, pos.getX(), pos.getY(), pos.getZ(), EnumFacing.DOWN)) {
			minY = 0;
		}

		return AxisAlignedBB.fromBounds(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX, pos.getY() + maxY, pos.getZ() + maxZ);
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
		return getCollisionBoundingBox(world, pos, world.getBlockState(pos));
	}
	*/
	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add("W.I.P");
	}
	
	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {}
	
	public static class Amethyst extends Piping {

		public Amethyst() {
			super(SonarMaterials.machine, -1);
		}

		public boolean checkBlockInDirection(IBlockAccess world, int x, int y, int z, EnumFacing dir) {
			IBlockState state =  world.getBlockState(new BlockPos(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ()));
			TileEntity tile = world.getTileEntity(new BlockPos(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ()));
			if (tile != null && tile instanceof IHungerProcessor || state.getBlock()==Calculator.amethystLog) {
				return true;
			}
			return super.checkBlockInDirection(world, x, y, z, dir);
		}
	}

	public static class Tanzanite extends Piping {

		public Tanzanite() {
			super(SonarMaterials.machine, -2);
		}

		public boolean checkBlockInDirection(IBlockAccess world, int x, int y, int z, EnumFacing dir) {
			IBlockState state =  world.getBlockState(new BlockPos(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ()));
			TileEntity tile = world.getTileEntity(new BlockPos(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ()));
			if (tile != null && tile instanceof IHealthProcessor || state.getBlock()==Calculator.tanzaniteLog) {
				return true;
			}
			return super.checkBlockInDirection(world, x, y, z, dir);
		}
	}

}
