package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IGuiTile;

public class Assimilator extends SonarMachineBlock {

	public int type;

	public Assimilator(int type) {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 3, 1.0F);
		this.type = type;
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			if (type == 0) {
				player.openGui(Calculator.instance, IGuiTile.ID, world, x, y, z);
			} else {
				player.openGui(Calculator.instance, IGuiTile.ID, world, x, y, z);

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
