package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;

public class HealthProcessor extends SonarSidedBlock {

	public HealthProcessor() {
		super(SonarMaterials.machine, true, true);
	}

	public boolean hasAnimatedFront() {
		return false;
	}
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if ((player.getHeldItem() != null) && (player.getHeldItem().getItem() == Calculator.wrench)) {
			return false;
		}
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, CalculatorGui.HealthProcessor, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityHealthProcessor();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		int health = stack.getTagCompound().getInteger("Food");
		if (health != 0) {
			list.add(FontHelper.translate("points.health") + ": " + health);
		}
	}

}
