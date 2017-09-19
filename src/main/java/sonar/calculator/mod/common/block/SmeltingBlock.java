package sonar.calculator.mod.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.helpers.FontHelper;
import sonar.core.upgrades.MachineUpgrade;
import sonar.core.utils.IGuiTile;

import java.util.List;
import java.util.Random;

public class SmeltingBlock extends SonarSidedBlock {

	public enum BlockTypes {
        /**
         * extraction chamber
         */
		EXTRACTION,
        /**
         * restoration chamber
         */
		RESTORATION,
        /**
         * reassembly chamber
         */
		REASSEMBLY,
        /**
         * processing chamber
         */
		PROCESSING,
        /**
         * stone seperator
         */
		STONE,
        /**
         * algorithm seperator
         */
		ALGORITHM,
        /**
         * precision chamber
         */
		PRECISION,
        /**
         * reinforced furnace
         */
		FURNACE;

		public boolean isOpaqueCube() {
            return this == STONE || this == ALGORITHM || this == FURNACE;
		}

		public TileEntity getTile(World world, int meta) {
			switch (this) {
			case EXTRACTION:
				return new TileEntityMachine.ExtractionChamber();
			case RESTORATION:
				return new TileEntityMachine.RestorationChamber();
			case REASSEMBLY:
				return new TileEntityMachine.ReassemblyChamber();
			case PROCESSING:
				return new TileEntityMachine.ProcessingChamber();
			case STONE:
                    return new TileEntityMachine.StoneSeparator();
			case ALGORITHM:
                    return new TileEntityMachine.AlgorithmSeparator();
			case PRECISION:
				return new TileEntityMachine.PrecisionChamber();
			case FURNACE:
				return new TileEntityMachine.ReinforcedFurnace();

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
	public boolean isOpaqueCube(IBlockState state) {
		return type != null && type.isOpaqueCube();
	}

    @Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (isAnimated(state, world, pos)) {
            EnumFacing enumfacing = state.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.8D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			switch (enumfacing) {
			case WEST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
			default:
				break;
			}
		}
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null) {
			if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof MachineUpgrade) {
				return false;
			} else if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == Calculator.wrench) {
				return false;
			} else {
				if (!world.isRemote) {
					player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
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
    public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List<String> list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
    }

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
    }

    @Override
    public void standardInfo(ItemStack stack, EntityPlayer player, List<String> list) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            int energyUsage = 0;
            int speed = 0;

            switch (type.ordinal()) {
                case 0:
                    energyUsage = CalculatorConfig.getInteger("Extraction Chamber" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Extraction Chamber" + "Base Speed");
                    break;
                case 1:
                    energyUsage = CalculatorConfig.getInteger("Restoration Chamber" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Restoration Chamber" + "Base Speed");
                    break;
                case 2:
                    energyUsage = CalculatorConfig.getInteger("Reassembly Chamber" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Reassembly Chamber" + "Base Speed");
                    break;
                case 3:
                    energyUsage = CalculatorConfig.getInteger("Processing Chamber" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Processing Chamber" + "Base Speed");
                    break;
                case 4:
                    energyUsage = CalculatorConfig.getInteger("Stone Seperator" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Stone Seperator" + "Base Speed");
                    break;
                case 5:
                    energyUsage = CalculatorConfig.getInteger("Algorithm Seperator" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Algorithm Seperator" + "Base Speed");
                    break;
                case 6:
                    energyUsage = CalculatorConfig.getInteger("Precision Chamber" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Precision Chamber" + "Base Speed");
                    break;
                case 7:
                    energyUsage = CalculatorConfig.getInteger("Reinforced Furnace" + "Energy Usage");
                    speed = CalculatorConfig.getInteger("Reinforced Furnace" + "Base Speed");
                    break;
            }
            list.add(FontHelper.translate("Process Speed: ") + TextFormatting.WHITE + speed + " ticks");
            list.add(FontHelper.translate("Energy Usage: ") + TextFormatting.WHITE + energyUsage + " RF per operation");
            list.add(FontHelper.translate("Consumption: ") + TextFormatting.WHITE + energyUsage / speed + " RF/t");
        } else {
            list.add("Hold" + TextFormatting.YELLOW + " SHIFT " + TextFormatting.RESET + "for more info");
        }
	}

	@Override
    public void standardInfo(ItemStack stack, World world, List<String> list) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			int energyUsage = 0;
			int speed = 0;

			switch (type.ordinal()) {
			case 0:
				energyUsage = CalculatorConfig.getInteger("Extraction Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Extraction Chamber" + "Base Speed");
				break;
			case 1:
				energyUsage = CalculatorConfig.getInteger("Restoration Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Restoration Chamber" + "Base Speed");
				break;
			case 2:
				energyUsage = CalculatorConfig.getInteger("Reassembly Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Reassembly Chamber" + "Base Speed");
				break;
			case 3:
				energyUsage = CalculatorConfig.getInteger("Processing Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Processing Chamber" + "Base Speed");
				break;
			case 4:
				energyUsage = CalculatorConfig.getInteger("Stone Seperator" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Stone Seperator" + "Base Speed");
				break;
			case 5:
				energyUsage = CalculatorConfig.getInteger("Algorithm Seperator" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Algorithm Seperator" + "Base Speed");
				break;
			case 6:
				energyUsage = CalculatorConfig.getInteger("Precision Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Precision Chamber" + "Base Speed");
				break;
			case 7:
				energyUsage = CalculatorConfig.getInteger("Reinforced Furnace" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Reinforced Furnace" + "Base Speed");
				break;
			}
			list.add(FontHelper.translate("Process Speed: ") + TextFormatting.WHITE + speed + " ticks");
			list.add(FontHelper.translate("Energy Usage: ") + TextFormatting.WHITE + energyUsage + " RF per operation");
			list.add(FontHelper.translate("Consumption: ") + TextFormatting.WHITE + energyUsage / speed + " RF/t");
		} else {
			list.add("Hold" + TextFormatting.YELLOW +  " SHIFT " + TextFormatting.RESET + "for more info");
		}
	}

    @Override
	public boolean isAnimated(IBlockState state, IBlockAccess w, BlockPos pos) {
		TileEntity target = w.getTileEntity(pos);
		if (target instanceof TileEntityAbstractProcess) {
			TileEntityAbstractProcess te = (TileEntityAbstractProcess) target;
			return te.isActive();
		}
		return false;
	}

    @Override
	public BlockRenderLayer getBlockLayer() {
		return !type.isOpaqueCube() ? BlockRenderLayer.CUTOUT_MIPPED : super.getBlockLayer();
	}

	public static class ChamberBlock extends SmeltingBlock {

		public ChamberBlock(BlockTypes type) {
			super(type);
		}

        @Override
		public boolean hasAnimatedFront() {
			return false;
		}
	}
}
