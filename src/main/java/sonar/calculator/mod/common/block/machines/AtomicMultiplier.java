package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

import java.util.List;
import java.util.Random;

public class AtomicMultiplier extends SonarMachineBlock {

	public AtomicMultiplier() {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 3, 1.0F);
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityAtomicMultiplier();
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		TileEntityAtomicMultiplier te = (TileEntityAtomicMultiplier) world.getTileEntity(pos);
		if (te.active.getObject() == 1) {
			float x1 = pos.getX() + random.nextFloat();
			float y1 = pos.getY() + 0.5F;
			float z1 = pos.getZ() + random.nextFloat();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
    public void standardInfo(ItemStack stack, EntityPlayer player, List<String> list) {
		list.add(FontHelper.translate("energy.required") + ": " + FontHelper.formatStorage(TileEntityAtomicMultiplier.requiredEnergy));
    }

    @Override
    public void standardInfo(ItemStack stack, World world, List<String> list) {
        list.add(FontHelper.translate("energy.required") + ": " + FontHelper.formatStorage(TileEntityAtomicMultiplier.requiredEnergy));
	}

    @Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
