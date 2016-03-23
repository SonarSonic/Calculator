package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.blocks.IStableBlock;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;

public class CalculatorLocator extends SonarMachineBlock {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public CalculatorLocator() {
		super(SonarMaterials.machine,false,true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, true));
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public boolean isFullCube(){
		return false;
	}
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.CalculatorLocator, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (state.getValue(ACTIVE)) {
			float x1 = pos.getX() + random.nextFloat();
			float y1 = pos.getY() + 0.5F;
			float z1 = pos.getZ() + random.nextFloat();			
			
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
		}
	}

	public static int multiBlockStructure(World world, BlockPos pos) {
		for (int size = 1; size < 12; size++) {
			if (checkSize(world, pos, size)) {
				return size;
			}
		}
		return 0;
	}

	public static boolean checkSize(World world, BlockPos pos, int size) {
		for (int X = -size; X <= size; X++) {
			for (int Z = -size; Z <= size; Z++) {
				if (!(X == 0 && Z == 0)) {
					if (!(world.getBlockState(pos.add(X, -1, Z)).getBlock() instanceof IStableBlock)) {
						return false;
					}
				}
			}
		}

		for (int XZ = -(size); XZ <= (size); XZ++) {
			for (int Y = -1; Y <= 0; Y++) {
				if (!(world.getBlockState(pos.add(XZ, Y, size + 1)).getBlock() instanceof IStableBlock)) {
					return false;
				} else if (!(world.getBlockState(pos.add(XZ, Y, -(size + 1))).getBlock() instanceof IStableBlock)) {
					return false;
				} else if (!(world.getBlockState(pos.add((size + 1), Y, XZ)).getBlock() instanceof IStableBlock)) {
					return false;
				} else if (!(world.getBlockState(pos.add(-(size + 1), Y, XZ)).getBlock() instanceof IStableBlock)) {
					return false;
				}
			}
		}

		for (int X = -(size); X <= (size); X++) {
			for (int Z = -(size); Z <= (size); Z++) {
				if (!(X == 0 && Z == 0)) {
					if (!(world.getBlockState(pos.add(X, 0, Z)).getBlock() == Calculator.calculatorplug)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCalculatorLocator();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity target = world.getTileEntity(pos);
		if(target !=null && target instanceof TileEntityCalculatorLocator){
			TileEntityCalculatorLocator locator = (TileEntityCalculatorLocator) target;
			return state.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, locator.active.getObject());
		}
		return state;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, ACTIVE });
	}
}
