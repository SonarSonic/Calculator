package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessCapacitor;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;

public class FlawlessCapacitor extends SonarMachineBlock {

	public FlawlessCapacitor() {
		super(SonarMaterials.machine);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public IIcon getIcon(int side, int metadata) {
		return blockIcon;

	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!player.isSneaking()) {
				if (!world.isRemote) {
					player.openGui(Calculator.instance, CalculatorGui.AtomicEnergyCube, world, x, y, z);
				}
			} else {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null && te instanceof TileEntityFlawlessCapacitor) {
					TileEntityFlawlessCapacitor cube = (TileEntityFlawlessCapacitor) te;
					cube.incrementSide(side);
					FontHelper.sendMessage("Current Stored: " + cube.storage.getEnergyStored(), world, player);
				}

			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFlawlessCapacitor();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}
}
