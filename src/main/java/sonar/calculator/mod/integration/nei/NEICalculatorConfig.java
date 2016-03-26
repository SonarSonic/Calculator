package sonar.calculator.mod.integration.nei;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.API;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicModule;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.client.gui.generators.GuiExtractor;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;
import sonar.calculator.mod.integration.nei.handlers.AtomicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CalculatorDischargeHandler;
import sonar.calculator.mod.integration.nei.handlers.CalculatorOverlay;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CircuitBoardHandler;
import sonar.calculator.mod.integration.nei.handlers.CircuitExtractionRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ConductorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.CraftingCalculatorHandler;
import sonar.calculator.mod.integration.nei.handlers.DualOutputHandlers;
import sonar.calculator.mod.integration.nei.handlers.DynamicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ExtractionRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.ExtractorHandlers;
import sonar.calculator.mod.integration.nei.handlers.FlawlessRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.HealthProcessorRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.InfoUsageHandler;
import sonar.calculator.mod.integration.nei.handlers.PortableDynamicRecipeHandler;
import sonar.calculator.mod.integration.nei.handlers.PowerCubeHandler;
import sonar.calculator.mod.integration.nei.handlers.ProcessingHandlers;
import sonar.calculator.mod.integration.nei.handlers.RecipeInfoHandler;
import sonar.calculator.mod.integration.nei.handlers.ScientificRecipeHandler;

public class NEICalculatorConfig implements IConfigureNEI {
	@Override
	public void loadConfig() {
		API.registerUsageHandler(new CalculatorDischargeHandler());
		API.registerRecipeHandler(new CalculatorDischargeHandler());
		API.registerRecipeHandler(new ProcessingHandlers.ReassemblyChamber());
		API.registerUsageHandler(new ProcessingHandlers.ReassemblyChamber());
		API.registerRecipeHandler(new ProcessingHandlers.RestorationChamber());
		API.registerUsageHandler(new ProcessingHandlers.RestorationChamber());
		API.registerRecipeHandler(new ExtractionRecipeHandler());
		API.registerUsageHandler(new ExtractionRecipeHandler());
		API.registerRecipeHandler(new DualOutputHandlers.Algorithm());
		API.registerUsageHandler(new DualOutputHandlers.Algorithm());
		API.registerRecipeHandler(new DualOutputHandlers.Stone());
		API.registerUsageHandler(new DualOutputHandlers.Stone());
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
		API.registerRecipeHandler(new ExtractorHandlers.Starch());
		API.registerUsageHandler(new ExtractorHandlers.Starch());
		API.registerRecipeHandler(new ExtractorHandlers.Redstone());
		API.registerUsageHandler(new ExtractorHandlers.Redstone());
		API.registerRecipeHandler(new ExtractorHandlers.Glowstone());
		API.registerUsageHandler(new ExtractorHandlers.Glowstone());
		API.registerRecipeHandler(new ConductorRecipeHandler());
		API.registerUsageHandler(new ConductorRecipeHandler());
		API.registerRecipeHandler(new ProcessingHandlers.ProcessingChamber());
		API.registerUsageHandler(new ProcessingHandlers.ProcessingChamber());
		API.registerUsageHandler(new PowerCubeHandler());
		API.registerUsageHandler(new PowerCubeHandler.Advanced());
		API.registerUsageHandler(new ProcessingHandlers.ReinforcedFurnace());
		API.registerRecipeHandler(new DualOutputHandlers.Precision());
		API.registerUsageHandler(new DualOutputHandlers.Precision());
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
		API.registerGuiOverlay(GuiDynamicModule.class, "portable_dynamic");
		API.registerGuiOverlay(GuiFlawlessCalculator.class, "flawless");

		API.registerGuiOverlay(GuiExtractor.Starch.class, "starch");
		API.registerGuiOverlay(GuiExtractor.Glowstone.class, "glowstone");
		API.registerGuiOverlay(GuiExtractor.Redstone.class, "redstone");
		API.registerGuiOverlay(GuiConductorMast.class, "conductor");

		API.registerGuiOverlay(GuiInfoCalculator.class, "info");
		API.registerGuiOverlay(GuiRecipeInfo.class, "RecipeInfo");
		API.registerGuiOverlay(GuiSmeltingBlock.ProcessingChamber.class, "processing");
		API.registerGuiOverlay(GuiPowerCube.class, "powercube");
		API.registerGuiOverlay(GuiAdvancedPowerCube.class, "advancedpowercube");
		API.registerGuiOverlay(GuiDualOutputSmelting.PrecisionChamber.class, "precision");
		API.registerGuiOverlayHandler(GuiCraftingCalculator.class, new DefaultOverlayHandler(), "crafting");

		API.registerGuiOverlayHandler(GuiCalculator.class, new CalculatorOverlay(), "calculator");
		API.registerGuiOverlayHandler(GuiScientificCalculator.class, new CalculatorOverlay(), "scientific");
		API.registerGuiOverlayHandler(GuiFlawlessCalculator.class, new CalculatorOverlay(), "flawless");

		API.hideItem(new ItemStack(Calculator.cropBroccoliPlant));
		API.hideItem(new ItemStack(Calculator.cropPrunaePlant));
		API.hideItem(new ItemStack(Calculator.cropFiddledewPlant));
		API.hideItem(new ItemStack(Calculator.conductormastBlock));
		API.hideItem(new ItemStack(Calculator.scarecrowBlock));
		API.hideItem(new ItemStack(Calculator.gas_lantern_on));
		API.hideItem(new ItemStack(Calculator.weatherStationBlock));
		API.hideItem(new ItemStack(Calculator.calculatorScreen));
		API.hideItem(new ItemStack(Calculator.researchChamber));
		API.hideItem(new ItemStack(Calculator.transmitterBlock));
	}

	@Override
	public String getName() {
		return "Calculator";
	}

	@Override
	public String getVersion() {
		return "Universal";
	}
}
