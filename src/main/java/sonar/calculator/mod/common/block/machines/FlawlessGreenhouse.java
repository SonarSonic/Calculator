package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.blocks.IConnectedBlock;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;

public class FlawlessGreenhouse extends SonarMachineBlock implements IConnectedBlock {

	public int[] connections = new int[]{0,5,6};
	
	public FlawlessGreenhouse() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlawlessGreenhouse) {
			TileEntityFlawlessGreenhouse house = (TileEntityFlawlessGreenhouse) tile;
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (house.state.getObject() == State.INCOMPLETE) {
					FailedCoords coords = house.checkStructure(null);
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(), world, player);
					}
				} else if (house.state.getObject() == State.COMPLETED) {
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
