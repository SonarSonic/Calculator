package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.api.items.ILocatorModule;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LocatorModule extends SonarItem implements ILocatorModule {
	
	public LocatorModule() {
		setTextureName("Calculator:locator_module_on");
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
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

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			NBTTagCompound nbtData = stack.getTagCompound();
			if (stack.getTagCompound().getString("Player") != "None") {
				list.add(FontHelper.translate("locator.owner") + ": " + stack.getTagCompound().getString("Player"));
			} else {
				list.add(FontHelper.translate("locator.owner") + ": " + FontHelper.translate("locator.none"));
			}
		}
	}

	public String getPlayer(ItemStack stack) {
		if (stack.hasTagCompound()) {
			String playerName = stack.getTagCompound().getString("Player");
			if (playerName == "None") {
				return null;
			} else {
				return playerName;
			}
		}
		return null;

	}

}
