package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.helpers.FontHelper;

public class HungerProcessor extends SonarSidedBlock {

	public HungerProcessor() {
		super(SonarMaterials.machine, true, true);
	}

	public boolean hasAnimatedFront() {
		return false;
	}

	/*@Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister iconRegister) { this.front = iconRegister.registerIcon("Calculator:hungerprocessor_front2"); this.front2 = iconRegister.registerIcon("Calculator:hungerprocessor_front1"); this.slot1 = iconRegister.registerIcon("Calculator:hungerprocessor_slot2"); this.slot2 = iconRegister.registerIcon("Calculator:hungerprocessor_slot1"); } */
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if ((player.getHeldItem() != null) && (player.getHeldItem().getItem() == Calculator.wrench)) {
			return false;
		}
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());

		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityHungerProcessor();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		int hunger = stack.getTagCompound().getInteger("Food");
		if (hunger != 0) {
			list.add(FontHelper.translate("points.hunger") + ": " + hunger);
		}
	}

}
