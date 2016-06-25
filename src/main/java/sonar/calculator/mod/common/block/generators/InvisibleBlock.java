package sonar.calculator.mod.common.block.generators;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.IGuiTile;

public class InvisibleBlock extends Block {

	public int type;

	public InvisibleBlock(int type) {
		super(SonarMaterials.machine);
		this.type = type;
		if (type == 2) {
			//this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
		}
		if (type == 0) {
			//this.setBlockBounds(0.20F, 0.0F, 0.20F, 0.80F, 1.0F, 0.80F);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote && type == 0) {
			for (int i = 1; i < 4; i++) {
				BlockPos offset = pos.offset(EnumFacing.DOWN, 1);
				if (world.getBlockState(offset).getBlock() == Calculator.conductorMast) {
					player.openGui(Calculator.instance, IGuiTile.ID, world, offset.getX(), offset.getY(), offset.getZ());
					break;
				}
			}
		}
		return true;
	}

	@Override
	public final boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (this.type == 0) {
			if (world.getBlockState(pos.offset(EnumFacing.DOWN, 1)).getBlock() == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(pos.offset(EnumFacing.DOWN, 1));
				SonarHelper.dropTile(player, world.getBlockState(pos.offset(EnumFacing.DOWN, 1)).getBlock(), world, pos.offset(EnumFacing.DOWN, 1));
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 2)) == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(pos.offset(EnumFacing.DOWN, 2));
				SonarHelper.dropTile(player, world.getBlockState(pos.offset(EnumFacing.DOWN, 2)).getBlock(), world, pos.offset(EnumFacing.DOWN, 2));
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 3)) == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(pos.offset(EnumFacing.DOWN, 3));
				SonarHelper.dropTile(player, world.getBlockState(pos.offset(EnumFacing.DOWN, 3)).getBlock(), world, pos.offset(EnumFacing.DOWN, 3));
			}
		} else if (this.type == 1) {
			for (int X = -1; X < 2; X++) {
				for (int Z = -1; Z < 2; Z++) {
					IBlockState station = world.getBlockState(pos.add(X, -1, Z));
					if (station.getBlock() == Calculator.weatherStation) {
						TileEntity i = world.getTileEntity(pos.add(X, -1, Z));
						Block bi = world.getBlockState(pos.add(X, -1, Z)).getBlock();
						bi.dropBlockAsItem(world, pos.add(X, -1, Z), state, 0);
						world.setBlockToAir(pos.add(X, -1, Z));
					}
				}
			}
		} else if (this.type == 2) {
			BlockPos offset = pos.offset(EnumFacing.DOWN);
			IBlockState transmitter = world.getBlockState(offset);
			Block block = transmitter.getBlock();
			if (block == Calculator.transmitter) {
				block.dropBlockAsItem(world, offset, state, 0);
				world.setBlockToAir(offset);

			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (type == 0) {
			TileEntityConductorMast.setWeatherStationAngles(true, world, pos);
		}
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}
}
