package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.IGuiItem;

public class InfoCalculator extends SonarItem implements IGuiItem {

	public InfoCalculator() {
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) {
			player.openGui(Calculator.instance, IGuiItem.ID, world, -1000, -1000, -1000);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
		return new ContainerInfoCalculator(player);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
		return new GuiInfoCalculator(player);
	}
}
