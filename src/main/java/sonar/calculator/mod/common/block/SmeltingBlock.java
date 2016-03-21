package sonar.calculator.mod.common.block;

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
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.common.tileentity.TileEntityMachines;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.BlockInteraction;

public class SmeltingBlock extends SonarSidedBlock {

	public enum BlockTypes {
		/** extraction chamber */
		EXTRACTION(CalculatorGui.ExtractionChamber),
		/** restoration chamber */
		RESTORATION(CalculatorGui.RestorationChamber),
		/** reassembly chamber */
		REASSEMBLY(CalculatorGui.ReassemblyChamber),
		/** processing chamber */
		PROCESSING(CalculatorGui.ProcessingChamber),
		/** stone seperator */
		STONE(CalculatorGui.StoneSeperator),
		/** algorithm seperator */
		ALGORITHM(CalculatorGui.AlgorithmSeperator),
		/** precision chamber */
		PRECISION(CalculatorGui.PrecisionChamber),
		/** reinforced furnace */
		FURNACE(CalculatorGui.ReinforcedFurnace);

		private int guiID;

		BlockTypes(int guiID) {
			this.guiID = guiID;
		}

		public int getBlockGui() {
			return guiID;
		}

		public boolean isOpaqueCube() {
			if (this == STONE || this == ALGORITHM || this == FURNACE) {
				return true;
			}
			return false;
		}

		public TileEntity getTile(World world, int meta) {
			switch (this) {
			case EXTRACTION:
				return new TileEntityMachines.ExtractionChamber();
			case RESTORATION:
				return new TileEntityMachines.RestorationChamber();
			case REASSEMBLY:
				return new TileEntityMachines.ReassemblyChamber();
			case PROCESSING:
				return new TileEntityMachines.ProcessingChamber();
			case STONE:
				return new TileEntityMachines.StoneSeperator();
			case ALGORITHM:
				return new TileEntityMachines.AlgorithmSeperator();
			case PRECISION:
				return new TileEntityMachines.PrecisionChamber();
			case FURNACE:
				return new TileEntityMachines.ReinforcedFurnace();

			default:
				return null;

			}
		}
	}

	public BlockTypes type;

	public SmeltingBlock(BlockTypes type) {
		super(SonarMaterials.machine, true, true);
		this.type = type;
	}

	@Override
	public boolean isOpaqueCube() {
		return type != null && type.isOpaqueCube();
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(isAnimated(state, world, pos)){
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.8D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			switch (enumfacing) {
			case WEST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			default:
				break;
			}
		}
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof UpgradeCircuit) {
				return false;
			} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Calculator.wrench) {
				return false;
			} else {
				if (!world.isRemote) {
					player.openGui(Calculator.instance, type.getBlockGui(), world, pos.getX(), pos.getY(), pos.getZ());
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return type.getTile(world, i);
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
	}

	public boolean isAnimated(IBlockState state, IBlockAccess w, BlockPos pos) {
		TileEntity target = w.getTileEntity(pos);
		if (target instanceof TileEntityAbstractProcess) {
			TileEntityAbstractProcess te = (TileEntityAbstractProcess) target;
			return te.isActive();
		}
		return false;
	}
}
