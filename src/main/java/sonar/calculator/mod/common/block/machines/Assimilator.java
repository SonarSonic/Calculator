package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;

public class Assimilator extends SonarMachineBlock {

	public int type;

	public Assimilator(int type) {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 3, 1.0F);
		this.type = type;
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				if (type == 0) {
					player.openGui(Calculator.instance, CalculatorGui.sAssimilator, world, x, y, z);
				} else {
					player.openGui(Calculator.instance, CalculatorGui.aAssimilator, world, x, y, z);

				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		if (this.type == 0) {
			return new TileEntityAssimilator.Stone();
		} else {
			return new TileEntityAssimilator.Algorithm();
		}
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		super.addSpecialToolTip(stack, player, list);
		if (type == 0) {
			if (stack.hasTagCompound()) {
				int hunger = stack.getTagCompound().getInteger("hunger");
				if (hunger != 0) {
					list.add(FontHelper.translate("points.hunger") + ": " + hunger);
				}
				int health = stack.getTagCompound().getInteger("health");
				if (health != 0) {
					list.add(FontHelper.translate("points.health") + ": " + health);
				}
			}
		}
		// CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

}
