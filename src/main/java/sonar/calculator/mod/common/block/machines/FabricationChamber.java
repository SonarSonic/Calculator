package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.upgrades.MachineUpgrade;

public class FabricationChamber extends SonarMachineBlock {

	public FabricationChamber() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof MachineUpgrade) {
				return false;
			} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Calculator.wrench) {
				return false;
			} else {
				if (!world.isRemote) {
					player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
					TileEntity target = world.getTileEntity(pos);
					if (target != null) {
						target.markDirty();
					}
				}
				return true;
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
		/*
		TileEntity target = world.getTileEntity(pos);
		if (target != null && target instanceof TileEntityFabricationChamber) {
			TileEntityFabricationChamber chamber = (TileEntityFabricationChamber) target;
			if (chamber.currentFabricateTime != 0) {
				float x1 = pos.getX() + random.nextFloat();
				float y1 = pos.getY() + 0.5F;
				float z1 = pos.getZ() + random.nextFloat();

				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()+0.38, y1+0.1, pos.getZ()+0.38, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()+0.38, y1+0.1, pos.getZ()+0.38+0.25, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+0.38, y1+0.1, pos.getZ()+0.38, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+0.38, y1+0.1, pos.getZ()+0.38+0.25, 0.0D, 0.0D, 0.0D);
			}
		}
		*/
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityFabricationChamber();
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
		// list.add("Doesn't require power to opperate");
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

}
