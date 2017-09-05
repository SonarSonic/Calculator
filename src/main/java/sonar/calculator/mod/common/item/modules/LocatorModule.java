package sonar.calculator.mod.common.item.modules;

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
import sonar.calculator.mod.api.items.ILocatorModule;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;

import java.util.List;

public class LocatorModule extends SonarItem implements ILocatorModule {

	public LocatorModule() {
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			nbtData = new NBTTagCompound();
			nbtData.setString("Player", "None");
			stack.setTagCompound(nbtData);
		}
		String name = player.getGameProfile().getName();
		if (name != null) {
			if (world.getPlayerEntityByName(name) != null) {
				stack.getTagCompound().setString("Player", name);
				FontHelper.sendMessage(FontHelper.translate("locator.owner") + ": " + player.getGameProfile().getName(), world, player);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
        super.addInformation(stack, world, list, par4);
		if (stack.hasTagCompound()) {
			NBTTagCompound nbtData = stack.getTagCompound();
            if (!stack.getTagCompound().getString("Player").equals("None")) {
				list.add(FontHelper.translate("locator.owner") + ": " + stack.getTagCompound().getString("Player"));
			} else {
				list.add(FontHelper.translate("locator.owner") + ": " + FontHelper.translate("locator.none"));
			}
		}
	}

    @Override
	public String getPlayer(ItemStack stack) {
		if (stack.hasTagCompound()) {
			String playerName = stack.getTagCompound().getString("Player");
            if (playerName.equals("None")) {
				return null;
			} else {
				return playerName;
			}
		}
		return null;
	}
}
