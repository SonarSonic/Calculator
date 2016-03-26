package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.blocks.IConnectedBlock;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;

public class FlawlessGreenhouse extends SonarMachineBlock implements IConnectedBlock {

	public int[] connections = new int[]{0,5,6};
	
	public FlawlessGreenhouse() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlawlessGreenhouse) {
			TileEntityFlawlessGreenhouse house = (TileEntityFlawlessGreenhouse) tile;
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (!house.isBeingBuilt() && house.isIncomplete()) {
					FailedCoords coords = house.isComplete();
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(), world, player);
					}
				} else if (house.isCompleted()) {
					FontHelper.sendMessage(FontHelper.translate("greenhouse.complete"), world, player);

				}

			} else {
				if (player != null) {
					if (!world.isRemote) {
						player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
					}
				}
			}
		}
		return true;

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFlawlessGreenhouse();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public int[] getConnections() {
		return connections;
	}

}
