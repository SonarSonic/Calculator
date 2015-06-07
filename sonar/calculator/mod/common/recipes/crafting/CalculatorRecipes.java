package sonar.calculator.mod.common.recipes.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import sonar.calculator.mod.utils.helpers.ResearchPlayer;

public class CalculatorRecipes {
	private static final CalculatorRecipes smeltingBase = new CalculatorRecipes();

	private Map<Integer, CalculatorRecipe>  standard  = new HashMap();
	private Map idList = new HashMap();

	public static CalculatorRecipes recipes() {
		return smeltingBase;
	}

	private CalculatorRecipes() {
		
		addRecipe(Calculator.baby_grenade, Calculator.grenadecasing,Blocks.tnt, false);		
		addRecipe(Calculator.reinforcedstoneBlock, Blocks.cobblestone,	new ItemStack(Blocks.planks,1), false);
		addRecipe(Calculator.wrench, Calculator.reinforced_sword,Calculator.reinforced_pickaxe, false);
		addRecipe(Calculator.reinforceddirtBlock, new ItemStack(Blocks.dirt,1), new ItemStack(Blocks.planks,1), false);
		addRecipe(new ItemStack(Calculator.enrichedgold, 4), Items.gold_ingot,	Items.redstone, false);
		addRecipe(Calculator.reinforcediron_ingot, Items.iron_ingot,Calculator.reinforcedstoneBlock, false);
		addRecipe(Calculator.enriched_coal, Items.coal, Items.redstone, false);
		addRecipe(Calculator.broccoliSeeds, Items.wheat_seeds, Items.pumpkin_seeds, false);
		addRecipe(Calculator.sickle, Calculator.reinforced_shovel,	Calculator.reinforced_axe, false);
		addRecipe(Calculator.scarecrow, Blocks.pumpkin, Blocks.hay_block, false);
		addRecipe(Calculator.basic_lantern, Blocks.torch,Calculator.reinforcediron_ingot, false);
		addRecipe(Calculator.gas_lantern_off, Calculator.basic_lantern,Calculator.basic_lantern, false);
		addRecipe(Calculator.prunaeSeeds, Calculator.enriched_coal,Items.wheat_seeds, false);
		addRecipe(Calculator.enriched_coal, Calculator.coal_dust,Calculator.coal_dust, false);
		addRecipe(Calculator.researchChamber, Calculator.reinforced_iron_block,Calculator.powerCube, false);
	
		
		 //calculator recipes
	    addRecipe(new ItemStack(Calculator.reinforcedstoneBlock,8), Blocks.cobblestone, Items.clay_ball,true);
	    addRecipe(new ItemStack(Calculator.reinforceddirtBlock, 2, 2), Blocks.stone, new ItemStack(Blocks.dirt, 1, 2),true );
	    addRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1, 2), Blocks.cobblestone, new ItemStack(Blocks.dirt, 1, 2),true );
	    addRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), new ItemStack(Blocks.grass,1), Blocks.log,true );
	    addRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), new ItemStack(Blocks.grass,1), Blocks.log2 ,true);
	    addRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.planks,1) ,true);
	    addRecipe(Calculator.powerCube, Blocks.furnace, Blocks.stone ,true);
	    addRecipe(Calculator.powerCube, Blocks.furnace, Blocks.cobblestone ,true);
	    addRecipe(Calculator.powerCube, Blocks.furnace, Calculator.reinforcedstoneBlock ,true);
	    addRecipe(Calculator.stoneSeperator, Blocks.furnace, Blocks.furnace,true );
	    
	    
	    addOreDictRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1), new ItemStack(Blocks.dirt,1), "plankWood",true);	
	    addOreDictRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), new ItemStack(Blocks.dirt,1), "logWood",true);	
	    addOreDictRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 1),  new ItemStack(Blocks.cobblestone,1), "plankWood",true);	
	    addOreDictRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 4),  new ItemStack(Blocks.cobblestone,1), "logWood",true);	
	    addOreDictRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 6),  new ItemStack(Blocks.stone,1), "logWood",true);	
	    addOreDictRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 2),  new ItemStack(Blocks.stone,1), "plankWood",true);	
	
	    //standard blocks
	    addRecipe(new ItemStack(Blocks.dirt,1), Calculator.soil, true);
	    addRecipe(new ItemStack(Blocks.dirt,1), Blocks.gravel, Blocks.sand ,true);
	    addRecipe(new ItemStack(Blocks.dirt,1), Blocks.sand, Blocks.sand ,true);
	    addRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.red_mushroom_block, 1, 5),true );
	    addRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.brown_mushroom_block, 1, 5),true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), Blocks.stone, Blocks.sapling,true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.stone, Blocks.leaves,true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.stone, Blocks.leaves2,true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.cobblestone, Blocks.sapling,true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 1), Blocks.cobblestone, Blocks.leaves,true );
	    addRecipe(Blocks.mossy_cobblestone, Blocks.cobblestone, Blocks.leaves2,true );
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.vine, Blocks.cobblestone,true);
	    addRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), Blocks.vine, Blocks.stone,true);
	    addRecipe(Blocks.stone, Calculator.small_stone,true);
	    addRecipe(Blocks.stone, Blocks.gravel, Blocks.gravel ,true);
		addRecipe(Blocks.stone, Blocks.cobblestone, Blocks.cobblestone, true);
	    addRecipe(new ItemStack(Blocks.stonebrick, 1, 2), Blocks.cobblestone, Blocks.stone,true );
	    addRecipe(Blocks.sand, Blocks.gravel, new ItemStack(Blocks.grass,1),true );  
	    addRecipe(new ItemStack(Blocks.sand, 1), Blocks.cobblestone, Blocks.gravel,true );
	    addRecipe(new ItemStack(Blocks.sand, 2), Blocks.stone, Blocks.gravel,true );
		addRecipe(Blocks.sand, Blocks.gravel, new ItemStack(Blocks.dirt,1), true);
	    addRecipe(new ItemStack(Blocks.sandstone, 1, 2), Blocks.stone, Blocks.sandstone,true );
	    addRecipe(new ItemStack(Blocks.sandstone, 1, 1), Blocks.cobblestone, Blocks.sandstone,true );
	    addRecipe(new ItemStack(Blocks.grass, 2), Items.wheat_seeds, new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.grass, 2), Items.pumpkin_seeds, new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.grass, 4), Calculator.broccoliSeeds, new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.gravel, 3), Blocks.stone, new ItemStack(Blocks.grass,1),true);
	    addRecipe(Blocks.gravel, Blocks.sand,  new ItemStack(Blocks.grass,1) ,true);
	    addRecipe(Blocks.gravel, Blocks.cobblestone, new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(Blocks.gravel, Blocks.stone, new ItemStack(Blocks.dirt,1) ,true);
		addRecipe(Blocks.gravel, Blocks.sand, new ItemStack(Blocks.dirt,1), true);
		addRecipe(Blocks.gravel, Blocks.sand, new ItemStack(Blocks.dirt,1), true);
	    addRecipe(new ItemStack(Blocks.gravel, 2), Blocks.stone, Blocks.sand,true );
	    addRecipe(new ItemStack(Blocks.gravel, 1), Blocks.cobblestone, Blocks.sand,true );
	    addRecipe(Blocks.gravel, new ItemStack(Blocks.grass,1), Blocks.cobblestone,true );
	    addRecipe(new ItemStack(Blocks.gravel, 2), new ItemStack(Blocks.dirt,1), new ItemStack(Blocks.dirt,1),true);
	    addRecipe(new ItemStack(Blocks.leaves, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.leaves, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves, 4, 2), Blocks.vine, new ItemStack(Blocks.leaves, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.leaves, 4, 3), Blocks.vine, new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 1),true );
		addRecipe(Items.clay_ball, Calculator.soil, Calculator.small_stone, true);
	    
	    //special blocks
	    addRecipe(new ItemStack(Blocks.stained_glass, 1, 8), Blocks.stone, Blocks.glass,true );
	    addRecipe(new ItemStack(Blocks.stained_glass, 1, 7), Blocks.cobblestone, Blocks.glass,true );
	    addRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 8), Blocks.stone, Blocks.glass_pane,true );
	    addRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 7), Blocks.cobblestone, Blocks.glass_pane,true );
	    addRecipe(Blocks.cactus, Blocks.sand, Blocks.sapling ,true);
	    addRecipe(Blocks.cactus, Blocks.sand, Items.wheat_seeds ,true);
	    addRecipe(Blocks.cactus, Blocks.sand, Blocks.leaves ,true);
	    addRecipe(new ItemStack(Blocks.netherrack, 64), Blocks.obsidian, Blocks.stone ,true);
	    addRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.obsidian, Blocks.cobblestone ,true);
	    addRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.soul_sand, Blocks.stone ,true);
	    addRecipe(new ItemStack(Blocks.netherrack, 16), Blocks.soul_sand, Blocks.cobblestone ,true);
	    addRecipe(new ItemStack(Blocks.soul_sand, 64), Blocks.netherrack, Items.ghast_tear ,true);
	    addRecipe(new ItemStack(Blocks.soul_sand, 32), Blocks.sand, Items.ghast_tear ,true);
	    addRecipe(new ItemStack(Blocks.obsidian, 32), Items.diamond, Blocks.stone,true );
	    addRecipe(Blocks.mycelium, new ItemStack(Blocks.dirt, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.dirt, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass,1), new ItemStack(Blocks.grass,1),true);
	    
	    //standard items
	    addRecipe(Items.sugar, Items.water_bucket, Blocks.sapling,true);
	    addRecipe(Items.egg, Items.feather,true );
	    addRecipe(new ItemStack(Items.bone, 16), Items.skull, Items.iron_ingot,true );
	    addRecipe(new ItemStack(Items.rotten_flesh, 16), new ItemStack(Items.skull, 1, 2), Items.iron_ingot, true );
	    addRecipe(new ItemStack(Items.gunpowder, 16), new ItemStack(Items.skull, 1, 4), Items.iron_ingot, true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), Items.blaze_powder,true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Items.dye, 1, 14),true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_hardened_clay, 1, 1),true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.wool, 1, 1),true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_glass, 1, 1),true );
	    addRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.red_flower, 1, 5),true );
	    
	    //specials
	    addRecipe(Blocks.sticky_piston, Blocks.piston, Calculator.rotten_pear,true );
	    addRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, Blocks.stone,true );
	    
	    addRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, Blocks.cobblestone,true );
	    addRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, Blocks.stone,true );
	    addRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, Blocks.cobblestone,true );
	    addRecipe(new ItemStack(Blocks.crafting_table, 2), Blocks.furnace, new ItemStack(Blocks.planks,1),true );
	    addRecipe(new ItemStack(Blocks.anvil, 1), Blocks.iron_block, Blocks.iron_block ,true);
	    addRecipe(new ItemStack(Items.minecart, 1), Items.iron_ingot, Items.iron_ingot,true);
	    addRecipe(new ItemStack(Blocks.end_portal_frame, 3), Blocks.enchanting_table, Items.ender_eye,true );
	    addRecipe(Items.brewing_stand,Items.blaze_powder, Blocks.furnace,true);
	    addRecipe(Blocks.fence, Items.stick , Items.stick,true);
	    addRecipe(Items.bed, Blocks.leaves, new ItemStack(Blocks.planks,1),true);
	    
	    
	    //log recipes
	    for(int i=0; i<6; i++){
	    	if(i!=3 && i !=4 && i !=5){
	    	addRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log,1, i+1),true);
	    	}if(i==3){
	        	addRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log2,1, 0),true);
	    	}else if(i==4){
	        	addRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log2,1,1),true);
	    	}else if(i==5){
	        	addRecipe(new ItemStack(Blocks.log2, 2,1), new ItemStack(Blocks.log,1, 0),true);
	    	}
	    }
	    addRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 2) ,true);
	    addRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 1),true );    
	    addRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 3) ,true);
	    addRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 0) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log2, 1, 0) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 0) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 0) ,true);

	   
	    //plank recipes
	    for(int i=0; i<6; i++){
	    	if(i!=6){
	    	addRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks,1, 0),true);
	    	}
	    }
	    for(int i=0; i<6; i++){
	    	if(i!=6){
	    	addRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab,1, 0),true);
	    	}
	    }
	    for(int i=0; i<7; i++){
	    	if(i!=7){
	    	addRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab,1, 0),true);
	    	}
	    }
	    addRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 2) ,true);
	    addRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 5) ,true);
	    addRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 4),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 0) ,true);
	    addRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1),true );
	    addRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1),true );
	    
	    //sapling recipes
	    for(int i=0; i<6; i++){
	    	if(i!=6){
	    	addRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling,1, 0),true);
	    	}
	    }
	    addRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 5),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 4),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.sapling, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.leaves, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.leaves, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.leaves, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.leaves, 1, 4),true );
	    addRecipe(new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.leaves, 1, 5),true );
	    
	     
	    //leave recipes
	    for(int i=0; i<6; i++){
	    	if(i!=3 && i !=4 && i !=5){
	    	addRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves,1, i+1),true);
	    	}if(i==3){
	        	addRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves2,1),true);
	    	}else if(i==4){
	        	addRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves2,1,1),true);
	    	}else if(i==5){
	        	addRecipe(new ItemStack(Blocks.leaves2, 2,1), new ItemStack(Blocks.leaves,1),true);
	    	}
	    }
	    
	    addRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves2, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves2, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1) ,true);
	    addRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 3),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1),true );
	    addRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1),true );
	    
	    //special log recipes
	    addRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.dirt,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.sapling, 1), new ItemStack(Blocks.grass,1),true );
	    addRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.grass,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.grass,1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.grass,1),true );
	    addRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.grass,1),true );
	    addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.grass,1) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.planks,1) ,true);
	   	
	    addRecipe(new ItemStack(Blocks.log, 4, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.leaves, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.log, 4, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.leaves, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 4, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.leaves, 1, 2) ,true);
	    addRecipe(new ItemStack(Blocks.log, 4, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.leaves, 1, 3) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 4, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.leaves2, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.log2, 4, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.leaves2, 1, 1),true );
	    addRecipe(new ItemStack(Blocks.log, 4, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.planks, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.log, 4, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.planks, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 4, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.planks, 1, 2) ,true);
	    addRecipe(new ItemStack(Blocks.log, 4, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.planks, 1, 3) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 4, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.planks, 1, 4),true );
	    addRecipe(new ItemStack(Blocks.log2, 4, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.planks, 1, 5),true );
	    addRecipe(new ItemStack(Blocks.log, 8, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.log, 1, 0) ,true);
	    addRecipe(new ItemStack(Blocks.log, 8, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.log, 1, 1) ,true);
	    addRecipe(new ItemStack(Blocks.log, 8, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.log, 1, 2) ,true);
	    addRecipe(new ItemStack(Blocks.log, 8, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.log, 1, 3) ,true);
	    addRecipe(new ItemStack(Blocks.log2, 8, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.log2, 1, 0),true );
	    addRecipe(new ItemStack(Blocks.log2, 8, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.log2, 1, 1),true );
	    
	    //new
	    addRecipe(new ItemStack(Calculator.itemCalculator, 1), Items.iron_ingot, Items.redstone,true);
	    addRecipe(Blocks.grass, Blocks.dirt, new ItemStack(Blocks.tallgrass,1,1),true);
	    addRecipe(Blocks.gravel, Blocks.dirt, Calculator.small_stone,true);
	    addRecipe(Blocks.grass, Blocks.dirt, Calculator.soil,true);
	    addRecipe(Blocks.dirt, Blocks.sand, Calculator.soil,true);
	    addRecipe(new ItemStack(Calculator.enrichedgold_ingot, 2), Calculator.reinforcediron_ingot, new ItemStack(Items.dye, 1, 11), true);
	    addRecipe(new ItemStack(Calculator.weakeneddiamond, 6), Items.diamond, new ItemStack(Items.dye,1, 4), true);
	    addRecipe(Items.ghast_tear, Items.blaze_rod, Items.ender_eye, true);
	    addRecipe(new ItemStack(Items.blaze_powder, 16), Items.blaze_rod, Items.ghast_tear, true);
	    addRecipe(new ItemStack(Blocks.end_stone, 16), Items.ender_eye, Blocks.stone, true);
	    addRecipe(new ItemStack(Blocks.obsidian, 16), Items.magma_cream, Blocks.stone, true);
	    addRecipe(new ItemStack(Blocks.netherrack, 32), Items.blaze_rod, Blocks.stone, true);
	    addRecipe(Blocks.end_stone, Blocks.obsidian, Blocks.soul_sand, true);
	    addRecipe(new ItemStack(Calculator.calculator_assembly, 8), Items.iron_ingot, Blocks.stone_button, true);
	    addRecipe(new ItemStack(Calculator.calculator_screen, 8), Calculator.redstone_ingot, Blocks.cobblestone, true);
	    addRecipe(new ItemStack(Items.iron_ingot, 8), Items.ender_pearl, Items.ender_pearl, true);
	    addRecipe(new ItemStack(Items.gold_ingot, 8), Items.diamond, Items.diamond, true);
	    addRecipe(new ItemStack(Blocks.log, 8, 0), Blocks.obsidian, Blocks.obsidian, true);
	    addRecipe(new ItemStack(Items.blaze_rod, 8), Items.ghast_tear, Items.ghast_tear, true);
	    addRecipe(Blocks.vine, new ItemStack(Blocks.tallgrass,1,1), new ItemStack(Blocks.tallgrass,1,1), true);
	    addRecipe(Items.melon_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, true);
	    addRecipe(new ItemStack(Items.pumpkin_seeds, 2), Items.melon_seeds, Items.melon_seeds, true);
	    addRecipe(Items.pumpkin_seeds, Items.carrot, Items.potato, true);

	    addOreDictRecipe(new ItemStack(Blocks.piston,1), new ItemStack(Calculator.reinforcediron_ingot,1), "logWood", true);
	    addOreDictRecipe(new ItemStack(Blocks.noteblock,8), new ItemStack(Calculator.redstone_ingot,1), "logWood", true);
	    addOreDictRecipe(new ItemStack(Blocks.noteblock,2), new ItemStack(Calculator.redstone_ingot,1), "plankWood", true);	    
	    addOreDictRecipe(new ItemStack(Items.reeds,1), new ItemStack(Blocks.grass,1), "treeSapling", true);

	    
	    addRecipe(new ItemStack(Items.fire_charge, 4), Items.snowball, Items.blaze_powder, true);
	    addRecipe(new ItemStack(Items.leather, 2), Items.rotten_flesh, Items.slime_ball, true);
	    addRecipe(Items.slime_ball, Items.clay_ball, Items.rotten_flesh, true);
	    addRecipe(Items.slime_ball, Calculator.pear, Items.clay_ball, true);
	    
	    addRecipe(new ItemStack(Items.slime_ball,2), Calculator.rotten_pear, Calculator.rotten_pear, true);
	    addRecipe(new ItemStack(Calculator.rotten_pear,2), Items.slime_ball, Items.slime_ball, true);
	    addRecipe(new ItemStack(Items.netherbrick,4), Items.brick, Items.brick, true);
	    addRecipe(new ItemStack(Items.brick,1), Items.netherbrick, Items.netherbrick, true);
	    addRecipe(new ItemStack(Items.nether_wart,1), Items.reeds, Blocks.obsidian, true);
	    
	    addOreDictRecipe(new ItemStack(Items.wheat,8), new ItemStack(Blocks.tallgrass,1,1), "treeSapling", true);
	    addOreDictRecipe(new ItemStack(Items.wheat_seeds,1), new ItemStack(Blocks.vine), "treeSapling", true);
	    
	    addRecipe(new ItemStack(Blocks.ender_chest,1), Blocks.end_stone, Items.ender_eye, true);
	    addRecipe(new ItemStack(Blocks.ender_chest,16), Blocks.chest, Items.nether_star, true);
	    		
	    for(int i=0; i<9; i++){
		    addRecipe(new ItemStack(Blocks.vine,16), new ItemStack(Blocks.red_flower,1, i), Blocks.sapling, true);
	    }
	    
	    for(int i=0; i<9; i++){
	    	if(i!=8){
	    	addRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower,1, 0),true);
	    	}
	    }
	    
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    	addRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool,1, 0),true);
	    	}
	    }
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    	addRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass,1, 0),true);
	    	}
	    }
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    	addRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane,1, 0),true);
	    	}
	    }
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    	addRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay,1, 0),true);
	    	}
	    }
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    	addRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet,1, 0),true);
	    	}
	    }
	    for(int i=0; i<16; i++){
	    	if(i!=15){
	    		
	    	addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye,1, i+1),true);
	    	}else{
		    	addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye,1, 0),true);
	    	}
	    }
	    for(int i=0; i<6; i++){
	    	if(i!=5){
	    	if(i==1){
		    	addRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.tallgrass,1, 1),true);	
	    	}
	    	else if(i==2){
		    	addRecipe(new ItemStack(Blocks.tallgrass, 2, 1), new ItemStack(Blocks.tallgrass,1, 2),true);		
	    	}
	    	else if(i==3){
		    	addRecipe(new ItemStack(Blocks.tallgrass, 2, 2), new ItemStack(Blocks.double_plant,1,  i+1),true);		
	    	} else{
	    	addRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant,1, i+1),true);
		    	
	    	}
	    	}else{
		    	addRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant,1, 0),true);
	    	}
	    }

		addRecipe(new ItemStack(Calculator.reinforcedstoneBrick, 2), Calculator.reinforcedstoneBlock,	Calculator.reinforcedstoneBlock, false);
		addRecipe(new ItemStack(Calculator.reinforceddirtBrick, 2), Calculator.reinforceddirtBlock,	Calculator.reinforceddirtBlock, false);
	    
	}

	public void addOreDictRecipe(ItemStack output, ItemStack input, String name, boolean hidden){
		  int s = OreDictionary.getOres(name).size();
			for(int i=0; i<s; i++){
				addRecipe(output, input, OreDictionary.getOres(name).get(i), hidden);
			}  
	  }
	
	public void addRecipe(Object output, Object input,boolean hidden) {
		addFinal(newItemStack(output),newItemStack(input),newItemStack(input),hidden);	 
	}
	public void addRecipe(Object output, Object input, Object input2,boolean hidden) {
		addFinal(newItemStack(output),newItemStack(input),newItemStack(input2),hidden);	 
	}
	public ItemStack newItemStack(Object obj){
		 if (obj instanceof ItemStack)
         {
             return ((ItemStack)obj).copy();
         }
         else if (obj instanceof Item)
         {
            return new ItemStack((Item)obj, 1);
         }
         else
         {
             if (!(obj instanceof Block))
             {
                 throw new RuntimeException("Invalid Calculator recipe!");
             }


             return new ItemStack((Block)obj, 1);
         }
	}
	public void addFinal(ItemStack output, ItemStack input, ItemStack input2, boolean hidden) {
		if(!idList.containsValue(GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()))){
		this.idList.put(idList.size()+1, GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()));
		}
		if(!idList.containsValue(GameRegistry.findUniqueIdentifierFor(input2.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()))){
			this.idList.put(idList.size()+1, GameRegistry.findUniqueIdentifierFor(input2.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()));
			}
		if(!idList.containsValue(GameRegistry.findUniqueIdentifierFor(output.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()))){
			this.idList.put(idList.size()+1, GameRegistry.findUniqueIdentifierFor(output.getItem()).toString() + ":"+String.valueOf(input.getItemDamage()));
			}
			int size = standard.size();
			this.standard.put(size, new CalculatorRecipe(output,input,input2,hidden));
			
	}
	
	public boolean recipeFull(InventoryCrafting matrix){
	     int i = 0;	     
		 for (int j = 0; j < 2; ++j)
	      {
	          ItemStack itemstack2 = matrix.getStackInSlot(j);

	          if (itemstack2 != null)
	          {
	              ++i;
	          }
	      }
		return i==2;
		
	}
	
	  public ItemStack findMatchingRecipe(InventoryCrafting matrix, EntityPlayer entity)
	  {
		  if(entity!=null){
			  System.out.print("hey");
		  ResearchPlayer player =ResearchPlayer.get(entity);
		  int[] unblocked = player.unblocked(entity);
		  if(!recipeFull(matrix)){
			  return null;
		  }
		  ItemStack result = null;
		  CalculatorRecipe recipe = null;
		  result = findMatch(matrix.getStackInSlot(0), matrix.getStackInSlot(1));
		  recipe = findRecipe(matrix.getStackInSlot(0), matrix.getStackInSlot(1));
		  if(result==null){
			  result = findMatch(matrix.getStackInSlot(1), matrix.getStackInSlot(0));
			  recipe = findRecipe(matrix.getStackInSlot(1), matrix.getStackInSlot(0));
		  }		  
		  
		  if(result!=null && recipe!=null){
			  if(!recipe.hidden){
				  if(CalculatorConfig.isEnabled(result)){
					  return result;
				  }else{
					  return null;	   
				  }
	          
			  }else if(recipe.hidden && unblocked != null && unblocked.length >= 1 && recipe.hidden && unblocked[CalculatorRecipes.recipes().getID(
									recipe.input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipe.input2)] != 0
							|| unblocked[CalculatorRecipes.recipes().getID(
									recipe.output)] != 0){
				  if(CalculatorConfig.isEnabled(result)){
					  return result;
				  }else{
					  return null;	   
				  }    				  
			  }
		  }
		  }

		return null;
	  }
	  

		public CalculatorRecipe getRecipe(int size) {
			Iterator iterator = this.standard.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return null;
				}

				entry = (Map.Entry) iterator.next();
			} while (!(size == (Integer) entry.getKey()));

			return (CalculatorRecipe) entry.getValue();

		}

		public CalculatorRecipe getRecipe(ItemStack stack) {
			Iterator iterator = this.standard.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return null;
				}

				entry = (Map.Entry) iterator.next();
			} while (!(this.matchingStack(stack,
					((CalculatorRecipe) entry.getValue()).output)));

			return (CalculatorRecipe) entry.getValue();
		}
		public ItemStack getRegisteredStack(int stack){
			String string = getStack(stack);
			if(string!=null){
				String[] parts = string.split(":");
				return new ItemStack(GameRegistry.findItem(parts[0], parts[1]), 1, Integer.valueOf(parts[2]));
				
				
			}else{
				return null;
			}
		}
		public String getStack(int stack) {
			Iterator iterator = this.idList.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return null;
				}

				entry = (Map.Entry) iterator.next();
			} while (!(stack == (Integer)entry.getKey()));

			return (String) entry.getValue();
		}

		public int getID(ItemStack stack) {
			if (stack != null) {
				return getID(GameRegistry.findUniqueIdentifierFor(stack.getItem())
						.toString() + ":" + String.valueOf(stack.getItemDamage()));
			} else {
				return 0;
			}

		}

		public int getID(String stack) {
			Iterator iterator = this.idList.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return 0;
				}

				entry = (Map.Entry) iterator.next();
			} while (!stack.equals((String) entry.getValue()));

			return (Integer) entry.getKey();

		}
		public ItemStack findMatch(ItemStack input, ItemStack input2) {
			Iterator iterator = this.standard.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return null;
				}

				entry = (Map.Entry) iterator.next();
			} while (!(matchingStack(input,
					((CalculatorRecipe) entry.getValue()).input) && matchingStack(
					input2, ((CalculatorRecipe) entry.getValue()).input2)));

			return ((CalculatorRecipe) entry.getValue()).output.copy();

		}

		public CalculatorRecipe findRecipe(ItemStack input, ItemStack input2) {
			Iterator iterator = this.standard.entrySet().iterator();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return null;
				}

				entry = (Map.Entry) iterator.next();
			} while (!(matchingStack(input,
					((CalculatorRecipe) entry.getValue()).input) && matchingStack(
					input2, ((CalculatorRecipe) entry.getValue()).input2)));

			return ((CalculatorRecipe) entry.getValue());

		}
		
		public boolean discovery(EntityPlayer entity, int discover) {
			ResearchPlayer player =ResearchPlayer.get(entity);
			int[] unblocked = player.unblocked(entity);
			int[] newRecipes = player.newRecipes(entity);
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (newRecipes != null && newRecipes.length >= 1) {
					if (recipe.getValue().hidden
							&& newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().input)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().input2)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().output)] != 0) {
						if(unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().input)] != 0
									&& unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().input2)] != 0
									|| unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().output)] != 0){
							return true;
						}
					}
				}
			}
			return false;

		}

		private boolean matchingStack(ItemStack stack1, ItemStack stack2) {
			if(stack1!=null && stack2!=null){
			return (stack2.getItem() == stack1.getItem())
					&& ((stack2.getItemDamage() == 32767) || (stack2
							.getItemDamage() == stack1.getItemDamage()));
			}else{
				return false;
			}
		}

		public Map getStandardList() {
			return this.standard;
		}

		public Map getIDList() {
			return this.idList;
		}

}
