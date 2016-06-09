package sonar.calculator.mod.common.block.generators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.api.ActionType;
import sonar.core.api.SonarAPI;
import sonar.core.api.StoredItemStack;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;

public class CalculatorPlug extends SonarMachineBlock {

	public CalculatorPlug() {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {
		if (!world.isRemote) {
			ItemStack held = player.getHeldItem();
			if (held != null && held.getItem() instanceof IStability) {
				TileEntity tile = world.getTileEntity(x, y, z);
				StoredItemStack item = new StoredItemStack(held).setStackSize(1);
				StoredItemStack stack = SonarAPI.getItemHelper().getStackToAdd(1, item, SonarAPI.getItemHelper().addItems(tile, item.copy(), ForgeDirection.UP, ActionType.PERFORM, null));
				if (stack == null || stack.getStackSize()==0)
					held.stackSize-=1;
					return true;
			}
			player.openGui(Calculator.instance, CalculatorGui.CalculatorPlug, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCalculatorPlug();
	}

}
