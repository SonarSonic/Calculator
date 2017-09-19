package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.machines.TileEntityCreativePowerCube;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;

public class CreativePowerCube extends SonarMachineBlock {

	public CreativePowerCube() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			FontHelper.sendMessage("Transfers: " + FontHelper.formatOutput(Integer.MAX_VALUE), world, player);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCreativePowerCube();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List<String> list) {}

	@Override
	public void addSpecialToolTip(ItemStack stack, World world, List<String> list) {}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List<String> list) {}

	@Override
	public void standardInfo(ItemStack stack, World world, List<String> list) {}
}
