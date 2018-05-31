package sonar.calculator.mod.common.item.calculators;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.item.SonarItem;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class InfoCalculator extends SonarItem implements IFlexibleGui<ItemStack> {

	public InfoCalculator() {
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
        super.addInformation(stack, world, list, par4);
	}

	@Nonnull
    @Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote) {
			FlexibleGuiHandler.instance().openBasicItemStack(false, stack, player, world, player.getPosition(), 0);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public Object getServerElement(ItemStack obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerInfoCalculator(player);
	}

	@Override
	public Object getClientElement(ItemStack obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiInfoCalculator(player);
	}
}
