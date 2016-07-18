package sonar.calculator.mod.common.block.generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.IGuiTile;

public class InvisibleBlock extends SonarBlock {

	public int type;

	public InvisibleBlock(int type) {
		super(SonarMaterials.machine, false, true);
		this.type = type;
		if (type == 2) {
			//this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
		}
		if (type == 0) {
			//this.setBlockBounds(0.20F, 0.0F, 0.20F, 0.80F, 1.0F, 0.80F);
		}
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote && type == 0) {
			for (int i = 1; i < 4; i++) {
				BlockPos offset = pos.offset(EnumFacing.DOWN, i);
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
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 2)).getBlock() == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(pos.offset(EnumFacing.DOWN, 2));
				SonarHelper.dropTile(player, world.getBlockState(pos.offset(EnumFacing.DOWN, 2)).getBlock(), world, pos.offset(EnumFacing.DOWN, 2));
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 3)).getBlock() == Calculator.conductorMast) {
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
	
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
    	switch(type){
    	case 0: 
        	return new ItemStack(Calculator.conductorMast, 1);
    	case 1: 
        	return new ItemStack(Calculator.weatherStation, 1);
    	case 2: 
        	return new ItemStack(Calculator.transmitter, 1);
    	default:
    		return super.getPickBlock(state, target, world, pos, player);
    	}
    }

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState();
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();

	}

	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}
