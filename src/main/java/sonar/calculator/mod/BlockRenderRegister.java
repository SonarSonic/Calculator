package sonar.calculator.mod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import sonar.core.common.block.properties.IMetaRenderer;

public class BlockRenderRegister {
	public static void register() {
		for (Block block : CalculatorBlocks.registeredBlocks) {
			Item item = Item.getItemFromBlock(block);
			if (item.getHasSubtypes()) {				
				NonNullList<ItemStack> stacks = NonNullList.create();
				item.getSubItems(item, Calculator.Calculator, stacks);
				for (ItemStack stack : stacks) {
					String variant = "variant=meta" + stack.getItemDamage();
					if(block instanceof IMetaRenderer){
						IMetaRenderer meta = (IMetaRenderer) block;
						variant= "variant=" + meta.getVariant(stack.getItemDamage()).getName();
					}
					ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(new ResourceLocation(Calculator.modid, item.getUnlocalizedName().substring(5)), variant));
				}
			} else {
				registerBlock(block);
			}
		}
	}

	public static void registerBlock(Block block) {
		if (block != null) {
			Item item = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Calculator.modid, item.getUnlocalizedName().substring(5)), "inventory"));
		}
	}
}
