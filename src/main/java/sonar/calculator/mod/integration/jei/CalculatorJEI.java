package sonar.calculator.mod.integration.jei;

import java.util.ArrayList;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.core.helpers.ISonarRecipeHelper;
import sonar.core.integration.jei.JEIRecipe;
import sonar.core.integration.jei.JEIRecipeHelper;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;

@JEIPlugin
public class CalculatorJEI extends BlankModPlugin {

	@Override
	public void register(IModRegistry registry) {
		Calculator.logger.error("Starting JEI Integration");
		ArrayList<IRecipeCategory> categories = new ArrayList<IRecipeCategory>();
		ArrayList<ISonarRecipeHelper> handlers = new ArrayList<ISonarRecipeHelper>();
		IItemRegistry itemRegistry = registry.getItemRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		jeiHelpers.getNbtIgnoreList().ignoreNbtTagNames(Calculator.circuitBoard, "Energy", "Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Stable");

		for (Handlers machine : Handlers.getSingleProcessHandlers()) {
			categories.add(new SingleProcessCategory(guiHelper, machine.helper, machine.unlocalizedName, machine.textureName));
			handlers.add(machine.helper);
		}
		for (Handlers machine : Handlers.getDualProcessHandlers()) {
			//categories.add(new DualProcessCategory(guiHelper, machine.helper, machine.unlocalizedName, machine.textureName));
			//handlers.add(machine.helper);
		}
		IRecipeCategory[] array = new IRecipeCategory[categories.size()];
		registry.addRecipeCategories(categories.toArray(array));
		for (ISonarRecipeHelper handler : handlers) {
			registry.addRecipes(handler.getJEIRecipes());
			registry.addRecipeHandlers(new JEIRecipeHelper(handler));
			Calculator.logger.error("Registering Recipe Handler: " + handler.getRecipeID());
		}
		Calculator.logger.error("Finished JEI Integration");
	}

	public enum Handlers {
		PROCESSING(ProcessingChamberRecipes.instance(), "tile.ProcessingChamber.name", "restorationchamber"), 
		RESTORATION(RestorationChamberRecipes.instance(), "tile.RestorationChamber.name", "restorationchamber"), 
		REASSEMBLY(ReassemblyChamberRecipes.instance(), "tile.ReassemblyChamber.name", "restorationchamber"), 
		EXTRACTION(ExtractionChamberRecipes.instance(), "tile.ExtractionChamber.name", "extractionchamber"), 
		PRECISION(PrecisionChamberRecipes.instance(), "tile.PrecisionChamber.name", "extractionchamber"), 
		STONE(StoneSeparatorRecipes.instance(), "tile.StoneSeparator.name", "stoneseperator"), 
		ALGORITHM(AlgorithmSeparatorRecipes.instance(), "tile.AlgorithmSeparator.name", "stoneseperator");
		public ISonarRecipeHelper helper;
		public String unlocalizedName;
		public String textureName;

		Handlers(ISonarRecipeHelper helper, String unlocalizedName, String textureName) {
			this.helper = helper;
			this.unlocalizedName = unlocalizedName;
			this.textureName = textureName;
		}

		public static Handlers[] getSingleProcessHandlers() {
			return new Handlers[] { PROCESSING, RESTORATION, REASSEMBLY };
		}

		public static Handlers[] getDualProcessHandlers() {
			return new Handlers[] { EXTRACTION, PRECISION, STONE, ALGORITHM };
		}
	}
}
