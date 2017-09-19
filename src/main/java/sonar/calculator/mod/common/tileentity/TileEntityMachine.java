package sonar.calculator.mod.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.StoneSeparatorRecipes;
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

	public abstract static class SingleOutput extends TileEntityAbstractProcess {

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

	public static class ReinforcedFurnace extends SingleOutput {

		public ReinforcedFurnace() {
			super(1, 1, CalculatorConfig.getInteger("Reinforced Furnace" + "Base Speed"), CalculatorConfig.getInteger("Reinforced Furnace" + "Energy Usage"));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ReinforcedFurnace(player.inventory, this);
		}

		public ItemStack getFurnaceOutput(ItemStack stack) {
			if (stack != null) {
				return FurnaceRecipes.instance().getSmeltingResult(stack);
			}
			return null;
		}

        @Override
		public boolean canProcess() {
            if (slots().get(0) == null || cookTime.getObject() == 0 && storage.getEnergyStored() < requiredEnergy()) {
				return false;
			}
			ItemStack result = getFurnaceOutput(inputStacks()[0]);
			if (result.isEmpty()) {
				return false;
			}
			for (int o = 0; o < outputSize(); o++) {
				ItemStack output = slots().get(o + inputSize() + 1);
				if (!output.isEmpty()) {
					if (!output.isItemEqual(result)) {
						return false;
					} else if (output.getCount() + result.getCount() > output.getMaxStackSize()) {
						return false;
					}
				}
			}
			return true;
		}

        @Override
		public void finishProcess() {
			ItemStack stack = getFurnaceOutput(inputStacks()[0]);
			if (!stack.isEmpty()) {
				ItemStack currentO = slots().get(inputSize() + 1);
				if (currentO.isEmpty()) {
					ItemStack outputStack = stack.copy();
					if (outputStack.getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					slots().set(inputSize() + 1, outputStack);
				} else if (currentO.isItemEqual(stack)) {
					currentO.grow(stack.getCount());
				}
			}
			slots().get(0).shrink(1);
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
            return slot < this.inputSize() && FurnaceRecipes.instance().getSmeltingResult(stack) != null;
		}
	}

    public static class StoneSeparator extends DualOutput {

        public StoneSeparator() {
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

    /**
     * single process machines
     */
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

    /**
     * dual process machines
     */
    public static class AlgorithmSeparator extends DualOutput {

        public AlgorithmSeparator() {
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
			upgrades = upgrades.setAllowed(16, "ENERGY", "SPEED", "TRANSFER", "VOID").addMaxiumum("TRANSFER", 1).addMaxiumum("VOID", 1);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ExtractionChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.ExtractionChamber(player.inventory, this);
		}

        @Override
		public boolean isOutputVoided(int slot, ItemStack outputStack) {
			return slot == 2 && upgrades.getUpgradesInstalled("VOID") == 1;
		}
	}

	public static class PrecisionChamber extends DualOutput {

		public PrecisionChamber() {
			super(1, 2, CalculatorConfig.getInteger("Precision Chamber" + "Base Speed"), CalculatorConfig.getInteger("Precision Chamber" + "Energy Usage"));
			upgrades = upgrades.setAllowed(16, "ENERGY", "SPEED", "TRANSFER", "VOID").addMaxiumum("TRANSFER", 1).addMaxiumum("VOID", 1);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.PrecisionChamber(player.inventory, this);
		}

        @Override
		public boolean isOutputVoided(int slot, ItemStack outputStack) {
			return slot == 2 && upgrades.getUpgradesInstalled("VOID") == 1;
		}
	}
}
