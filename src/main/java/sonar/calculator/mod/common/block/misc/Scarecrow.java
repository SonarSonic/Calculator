package sonar.calculator.mod.common.block.misc;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.SonarHelper;

public class Scarecrow extends SonarMachineBlock {

	public Scarecrow() {
		super(Material.cloth, true, true);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.6F, 1.0F);
	}

	@Override
	public boolean hasSpecialRenderer() {
		return false;
	}

	private void setBlocks(World world, BlockPos pos) {
		//world.setBlockState(pos.offset(EnumFacing.UP, 1), Calculator.scarecrowBlock.getDefaultState(), 3);
		//world.setBlockState(pos.offset(EnumFacing.UP, 2), Calculator.scarecrowBlock.getDefaultState(), 3);

	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.isAirBlock(pos.offset(EnumFacing.UP, 1)) && world.isAirBlock(pos.offset(EnumFacing.UP, 2));
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.setBlockToAir(pos.offset(EnumFacing.UP, 1));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 2));
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityScarecrow();
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (!world.isRemote)
			FontHelper.sendMessage("Wow, that's an impressive Scarecrow", world, player);
		return false;
	}

}
