package sonar.calculator.mod.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.core.helpers.RecipeHelper;
import sonar.core.recipes.RecipeHelperV2;

public class TileEntityMachine {

	public static class DualOutput extends TileEntityAbstractProcess {

		public DualOutput(int inputSize, int outputSize, int baseProcess, int baseEnergy) {
			super(inputSize, outputSize, baseProcess, baseEnergy);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting(player.inventory, this);
		}
	}

	public static class SingleOutput extends TileEntityAbstractProcess {

		public SingleOutput(int inputSize, int outputSize, int baseProcess, int baseEnergy) {
			super(inputSize, outputSize, baseProcess, baseEnergy);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock(player.inventory, this);
		}
	}
	//FIXME
	public static class ReinforcedFurnace extends SingleOutput {

		public ReinforcedFurnace() {
			super(1, 1, CalculatorConfig.getInteger("Reinforced Furnace" + "Base Speed"), CalculatorConfig.getInteger("Reinforced Furnace" + "Energy Usage"));
		}

		@Override
		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			return new ItemStack[] { FurnaceRecipes.instance().getSmeltingResult(stacks[0]) };
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ReinforcedFurnace(player.inventory, this);
		}

	}

	public static class StoneSeperator extends DualOutput {

		public StoneSeperator() {
			super(1, 2, CalculatorConfig.getInteger("Stone Seperator" + "Base Speed"), CalculatorConfig.getInteger("Stone Seperator" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return StoneSeparatorRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.StoneSeperator(player.inventory, this);
		}
	}

	/** single process machines */
	public static class ReassemblyChamber extends SingleOutput {

		public ReassemblyChamber() {
			super(1, 1, CalculatorConfig.getInteger("Reassembly Chamber" + "Base Speed"), CalculatorConfig.getInteger("Reassembly Chamber" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ReassemblyChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ReassemblyChamber(player.inventory, this);
		}

	}

	public static class RestorationChamber extends SingleOutput {

		public RestorationChamber() {
			super(1, 1, CalculatorConfig.getInteger("Restoration Chamber" + "Base Speed"), CalculatorConfig.getInteger("Restoration Chamber" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return RestorationChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.RestorationChamber(player.inventory, this);
		}
	}

	public static class ProcessingChamber extends SingleOutput {

		public ProcessingChamber() {
			super(1, 1, CalculatorConfig.getInteger("Processing Chamber" + "Base Speed"), CalculatorConfig.getInteger("Processing Chamber" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ProcessingChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ProcessingChamber(player.inventory, this);
		}
	}

	/** dual process machines */
	public static class AlgorithmSeperator extends DualOutput {

		public AlgorithmSeperator() {
			super(1, 2, CalculatorConfig.getInteger("Algorithm Seperator" + "Base Speed"), CalculatorConfig.getInteger("Algorithm Seperator" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return AlgorithmSeparatorRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.AlgorithmSeperator(player.inventory, this);
		}
	}

	public static class ExtractionChamber extends DualOutput {

		public ExtractionChamber() {
			super(1, 2, CalculatorConfig.getInteger("Extraction Chamber" + "Base Speed"), CalculatorConfig.getInteger("Extraction Chamber" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ExtractionChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.ExtractionChamber(player.inventory, this);
		}
		/*
		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			if (simulate) {
				return recipeHelper().getOutput(stacks);
			} else {
				ItemStack[] outputs = recipeHelper().getOutput(stacks);
				if (outputs[1].getItem()==Calculator.circuitBoard || outputs[1].getItem()==Calculator.circuitDamaged || outputs[1].getItem()==Calculator.circuitDirty) {
					outputs[1] = rand.nextInt(8 + 1) == 8 ? new ItemStack(outputs[1].getItem(), 1, rand.nextInt(13 + 1)) : null;
				}
				return outputs;
			}
		}
		*/
	}

	public static class PrecisionChamber extends DualOutput {

		public PrecisionChamber() {
			super(1, 2, CalculatorConfig.getInteger("Precision Chamber" + "Base Speed"), CalculatorConfig.getInteger("Precision Chamber" + "Energy Usage"));
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.PrecisionChamber(player.inventory, this);
		}
		/*
		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			if (simulate) {
				return recipeHelper().getOutput(stacks);
			} else {
				ItemStack[] outputs = recipeHelper().getOutput(stacks);
				if (recipeHelper().containsStack(new ItemStack(Blocks.COBBLESTONE, 1), stacks, false) != -1 || recipeHelper().containsStack(new ItemStack(Blocks.DIRT, 1), stacks, false) != -1) {
					outputs[1] = new ItemStack(outputs[1].getItem(), 1, rand.nextInt(13 + 1));
				}
				return outputs;
			}
		}
		*/
	}
}