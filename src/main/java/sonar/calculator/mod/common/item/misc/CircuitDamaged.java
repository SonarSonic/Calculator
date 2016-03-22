package sonar.calculator.mod.common.item.misc;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.core.common.item.SonarItem;

public class CircuitDamaged extends SonarItem {

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 14; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "." + stack.getItemDamage();
	}
}
