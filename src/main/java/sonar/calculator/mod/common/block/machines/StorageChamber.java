package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;

public class StorageChamber extends SonarSidedBlock {

	public StorageChamber() {
		super(SonarMaterials.machine, true, true);
	}

	public boolean hasAnimatedFront() {
		return false;
	}
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.StorageChamber, world, pos.getX(), pos.getY(), pos.getZ());
			}

		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityStorageChamber();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.removeTileEntity(pos);
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		switch (stack.getTagCompound().getInteger("type")) {
		case 1:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.analysed"));
			break;
		case 2:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.stable"));
			break;

		case 3:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.damaged"));
			break;

		case 4:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.dirty"));
			break;
		}

		int[] stored = stack.getTagCompound().getIntArray("stored");
		int total = 0;
		for (int i = 0; i < stored.length; i++) {
			total += stored[i];
		}
		if (total != 0) {
			list.add(FontHelper.translate("circuit.stored") + ": " + total);
		}
	}

	public boolean hasSpecialRenderer() {
		return true;
	}
}
