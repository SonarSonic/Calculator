package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;

public class DockingStation extends SonarMachineBlock {

	public DockingStation() {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!player.isSneaking()) {
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof UpgradeCircuit) {
					return false;
				}
				if (!insertCalculator(player, world, x, y, z)) {
					if (!world.isRemote) {
						if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityDockingStation) {
							TileEntityDockingStation station = (TileEntityDockingStation) world.getTileEntity(x, y, z);
							if (station.isCalculator(station.calcStack) != 0) {
								player.openGui(Calculator.instance, CalculatorGui.DockingStation, world, x, y, z);
							} else {
								FontHelper.sendMessage(FontHelper.translate("docking.noCalculator"), world, player);
							}
						}
					}
				}
			}

		}
		return true;
	}

	public boolean insertCalculator(EntityPlayer player, World world, int x, int y, int z) {
		if (player.getHeldItem() != null && TileEntityDockingStation.isCalculator(player.getHeldItem()) > 0) {
			if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityDockingStation) {
				TileEntityDockingStation station = (TileEntityDockingStation) world.getTileEntity(x, y, z);
				if (station.getStackInSlot(0) == null) {
					station.calcStack = player.getHeldItem().copy();
					player.getHeldItem().stackSize--;
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDockingStation();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	public boolean hasSpecialRenderer() {
		return true;
	}

}
