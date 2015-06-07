package sonar.calculator.mod.integration.nei;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiPortableDynamic;
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.client.gui.generators.GuiGlowstoneExtractor;
import sonar.calculator.mod.client.gui.generators.GuiRedstoneExtractor;
import sonar.calculator.mod.client.gui.generators.GuiStarchExtractor;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;
import sonar.calculator.mod.integration.nei.handlers.ASeperatorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.AdvancedPowerCubeRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.AtomicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CalculatorDischargeHandler;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CircuitBoardHandler;
import sonar.calculator.mod.integration.nei.handlers.CircuitExtractionRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ConductorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CraftingCalculatorHandler;
import sonar.calculator.mod.integration.nei.handlers.DynamicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ExtractionRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.FlawlessRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.GlowstoneExtractorHandler;
import sonar.calculator.mod.integration.nei.handlers.HealthProcessorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.InfoUsageHandler;
import sonar.calculator.mod.integration.nei.handlers.PortableDynamicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.PowerCubeRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.PrecisionRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ProcessingRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ReassemblyRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.RecipeInfoHandler;
import sonar.calculator.mod.integration.nei.handlers.RedstoneExtractorHandler;
import sonar.calculator.mod.integration.nei.handlers.ReinforcedFurnaceRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.RestorationRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.SSeperatorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ScientificRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.StarchExtractorHandler;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemFilter.ItemFilterProvider;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NEICalculatorConfig
  implements IConfigureNEI
{
  @Override
  public void loadConfig()
  	{
	API.registerUsageHandler(new CalculatorDischargeHandler());
	API.registerRecipeHandler(new CalculatorDischargeHandler());
    API.registerRecipeHandler(new ReassemblyRecipeHandler());
    API.registerUsageHandler(new ReassemblyRecipeHandler());
    API.registerRecipeHandler(new RestorationRecipeHandler());
    API.registerUsageHandler(new RestorationRecipeHandler());
    API.registerRecipeHandler(new ExtractionRecipeHandler());
    API.registerUsageHandler(new ExtractionRecipeHandler());
    API.registerRecipeHandler(new ASeperatorRecipeHandler());
    API.registerUsageHandler(new ASeperatorRecipeHandler());
    API.registerRecipeHandler(new SSeperatorRecipeHandler());
    API.registerUsageHandler(new SSeperatorRecipeHandler());
    API.registerRecipeHandler(new CalculatorRecipeHandler());
    API.registerUsageHandler(new CalculatorRecipeHandler());
    API.registerRecipeHandler(new ScientificRecipeHandler());
    API.registerUsageHandler(new ScientificRecipeHandler());
    API.registerRecipeHandler(new AtomicRecipeHandler());
    API.registerUsageHandler(new AtomicRecipeHandler());
    API.registerRecipeHandler(new FlawlessRecipeHandler());
    API.registerUsageHandler(new FlawlessRecipeHandler());
    API.registerUsageHandler(new DynamicRecipeHandler());
    API.registerUsageHandler(new PortableDynamicRecipeHandler());
    API.registerUsageHandler(new CraftingCalculatorHandler());
    API.registerRecipeHandler(new StarchExtractorHandler());
    API.registerUsageHandler(new StarchExtractorHandler());
    API.registerRecipeHandler(new RedstoneExtractorHandler());
    API.registerUsageHandler(new RedstoneExtractorHandler());
    API.registerRecipeHandler(new GlowstoneExtractorHandler());
    API.registerUsageHandler(new GlowstoneExtractorHandler());    
    API.registerRecipeHandler(new ConductorRecipeHandler());
    API.registerUsageHandler(new ConductorRecipeHandler());
    API.registerRecipeHandler(new ProcessingRecipeHandler());
    API.registerUsageHandler(new ProcessingRecipeHandler());
    API.registerUsageHandler(new PowerCubeRecipeHandler());
    API.registerUsageHandler(new AdvancedPowerCubeRecipeHandler());
    API.registerUsageHandler(new ReinforcedFurnaceRecipeHandler());
    API.registerRecipeHandler(new PrecisionRecipeHandler());
    API.registerUsageHandler(new PrecisionRecipeHandler());
    API.registerUsageHandler(new CircuitBoardHandler());
    API.registerRecipeHandler(new CircuitExtractionRecipeHandler());
    API.registerUsageHandler(new CircuitExtractionRecipeHandler());
    API.registerRecipeHandler(new HealthProcessorRecipeHandler());
    API.registerUsageHandler(new HealthProcessorRecipeHandler());
    

    API.registerUsageHandler(new InfoUsageHandler());
    API.registerRecipeHandler(new InfoUsageHandler());
    API.registerRecipeHandler(new RecipeInfoHandler());

    API.registerGuiOverlay(GuiSmeltingBlock.ReinforcedFurnace.class, "reinforced");    
    API.registerGuiOverlay(GuiSmeltingBlock.ReassemblyChamber.class, "reassembly");    
    API.registerGuiOverlay(GuiSmeltingBlock.RestorationChamber.class, "restoration");    
    API.registerGuiOverlay(GuiDualOutputSmelting.ExtractionChamber.class, "extraction");    
    API.registerGuiOverlay(GuiDualOutputSmelting.AlgorithmSeperator.class, "algorithm");    
    API.registerGuiOverlay(GuiDualOutputSmelting.StoneSeperator.class, "stone");   

    API.registerGuiOverlay(GuiCraftingCalculator.class, "craftingcalc"); 
    API.registerGuiOverlay(GuiCalculator.class, "calculator");    
    API.registerGuiOverlay(GuiScientificCalculator.class, "scientific");    
    API.registerGuiOverlay(GuiAtomicCalculator.class, "atomic");    
    API.registerGuiOverlay(GuiDynamicCalculator.class, "dynamic");   
    API.registerGuiOverlay(GuiPortableDynamic.class, "portable_dynamic");     
    API.registerGuiOverlay(GuiFlawlessCalculator.class, "flawless");
    
    API.registerGuiOverlay(GuiStarchExtractor.class, "starch");
    API.registerGuiOverlay(GuiGlowstoneExtractor.class, "glowstone");
    API.registerGuiOverlay(GuiRedstoneExtractor.class, "redstone");
    API.registerGuiOverlay(GuiConductorMast.class, "conductor");

    API.registerGuiOverlay(GuiInfoCalculator.class, "info");
    API.registerGuiOverlay(GuiRecipeInfo.class, "RecipeInfo");
    API.registerGuiOverlay(GuiSmeltingBlock.ProcessingChamber.class, "processing");
    API.registerGuiOverlay(GuiPowerCube.class, "powercube");
    API.registerGuiOverlay(GuiAdvancedPowerCube.class, "advancedpowercube");
    API.registerGuiOverlay(GuiDualOutputSmelting.PrecisionChamber.class, "precision");
    API.registerGuiOverlayHandler(GuiCraftingCalculator.class, new DefaultOverlayHandler(), "crafting");
    
    API.hideItem(new ItemStack(Calculator.cropBroccoliPlant));
    API.hideItem(new ItemStack(Calculator.cropPrunaePlant));
    API.hideItem(new ItemStack(Calculator.cropFiddledewPlant));
    API.hideItem(new ItemStack(Calculator.leaves));
    API.hideItem(new ItemStack(Calculator.diamondleaves));
    API.hideItem(new ItemStack(Calculator.conductormastBlock));
    API.hideItem(new ItemStack(Calculator.scarecrowBlock));
    API.hideItem(new ItemStack(Calculator.gas_lantern_on));
    API.hideItem(new ItemStack(Calculator.weatherStationBlock));
  }
  
  @Override
public String getName()
  {
    return "Calculator";
  }
  
  @Override
public String getVersion()
  {
    return "Universal";
  }
}
