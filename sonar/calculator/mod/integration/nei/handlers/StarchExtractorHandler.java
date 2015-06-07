package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.client.gui.generators.GuiStarchExtractor;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class StarchExtractorHandler extends TemplateRecipeHandler
{
    public class StarchPair extends CachedRecipe
    {

        public StarchPair(ItemStack starch, int value) {
        	this.value = value;
            this.starch = new PositionedStack(starch, 8-5, 14-11);
        }


        public int starch(){
			return value;
        	
        }
        @Override
		public PositionedStack getResult() {
            return afuels.get((cycleticks / 150) % afuels.size()).stack;
        }
        @Override
        public PositionedStack getOtherStack() {
            return starch;
        }
        PositionedStack starch;
        public int value;  
        }
    public static class FuelPair
    {
        public FuelPair(ItemStack ingred, int burnTime) {
            this.stack = new PositionedStack(ingred, 8-5, 38-11, false);
            this.burnTime = burnTime;
        }

        public PositionedStack stack;
        public int burnTime;
    }

    public static ArrayList<FuelPair> afuels;
    public static HashSet<Block> efuels;
    
    private ArrayList<FuelPair> mfurnace = new ArrayList<StarchExtractorHandler.FuelPair>();

    private ArrayList<StarchPair> astarch;

    
    @Override
    public String getRecipeName()
      {
        return StatCollector.translateToLocal("tile.starchextractor.name");
      }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (afuels == null)
            findFuels();
        return super.newInstance();
    }
    @Override
	public void loadTransferRects() {

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(30 - 5, 41 - 11, 78, 10), "fuel",
				new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(30 - 5, 17 - 11, 78, 10), "starch",
				new Object[0]));

	}
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("starch"))
				&& (getClass() == StarchExtractorHandler.class)) {
			Map<ItemStack, Integer> recipes = StarchExtractorRecipes
					.discharge().getPowerList();
			for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
				this.arecipes.add(new StarchPair(recipe.getKey(),
						recipe.getValue()));
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
      
    @Override
	public void loadUsageRecipes(ItemStack ingredient) {
        	int starch = StarchExtractorRecipes.discharge().value(ingredient);
        	if(starch>0){
                arecipes.add(new StarchPair(ingredient, starch));}
        	int fuel = TileEntityFurnace.getItemBurnTime(ingredient);
        	if(fuel>0){
                afuels.add(new FuelPair(ingredient, fuel));}
        
    }

    @Override
	public String getOverlayIdentifier() {
        return "starch";
    }
    
    @Override
	public void drawExtras(int i)
    {
          drawProgressBar(30-5, 17-11, 0, 166, 138, 10, 150, 0);
          
          drawProgressBar(30-5, 41-11, 0, 176, 138, 10, 150, 0);

    	  drawProgressBar(8-5, 63-11, 0, 186, 160, 10, 150, 0);         
      
    }
    
    private static Set<Item> excludedFuels() {
        Set<Item> efuels = new HashSet<Item>();
        efuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
        efuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
        efuels.add(Item.getItemFromBlock(Blocks.standing_sign));
        efuels.add(Item.getItemFromBlock(Blocks.wall_sign));
        efuels.add(Item.getItemFromBlock(Blocks.wooden_door));
        efuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
        return efuels;
    }

    private static void findFuels() {
        afuels = new ArrayList<FuelPair>();
        Set<Item> efuels = excludedFuels();
        for (ItemStack item : ItemList.items)
            if (!efuels.contains(item.getItem())) {
                int burnTime = TileEntityFurnace.getItemBurnTime(item);
                if (burnTime > 0)
                    afuels.add(new FuelPair(item.copy(), burnTime));
            }
    }
	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/starchgenerator.png";
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass()
	  {
	    return GuiStarchExtractor.class;
	  }
	
	@Override
	public int recipiesPerPage(){
		return 1;
		
	}
}
