package sonar.calculator.mod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister {
	public static void register() {
		for (Item item : CalculatorItems.registeredItems) {
			if (item.getHasSubtypes()) {
				List<ItemStack> stacks = new ArrayList();
				item.getSubItems(item, Calculator.Calculator, stacks);
				for (ItemStack stack : stacks) {
					//Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(stack.getItem(), stack.getItemDamage(), new ModelResourceLocation(Calculator.modid + ":" + item.getUnlocalizedName().substring(5) + "_" + stack.getItemDamage(), "inventory"));
					ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(Calculator.modid + ":" + item.getUnlocalizedName().substring(5),"variant=meta" + stack.getItemDamage()));
					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, stack.getItemDamage(), new ModelResourceLocation(Calculator.modid + ":" + item.getUnlocalizedName().substring(5),"inventory"));
					
					// ModelLoader.setCustomModelResourceLocation(stack.getItem(), stack.getItemDamage(), new ModelResourceLocation(new ResourceLocation(Calculator.modid, "item/" + "CircuitBoard"), "variant=meta" + stack.getItemDamage()));
				}
			} else {
				registerItem(item);
			}
		}

	}

	public static void registerItem(Item item) {
		if (item != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Calculator.modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

}
