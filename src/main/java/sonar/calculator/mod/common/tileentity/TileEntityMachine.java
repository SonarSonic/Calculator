package sonar.calculator.mod.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.*;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.recipes.RecipeHelperV2;

import javax.annotation.Nonnull;

public class TileEntityMachine {

	public static class DualOutput extends TileEntityAbstractProcess {

		public DualOutput(int inputSize, int outputSize, int baseProcess, int baseEnergy) {
			super(inputSize, outputSize, baseProcess, baseEnergy);
		}

		@Override
		public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiDualOutputSmelting(player.inventory, this);
		}
	}

	public abstract static class SingleOutput extends TileEntityAbstractProcess {

		public SingleOutput(int inputSize, int outputSize, int baseProcess, int baseEnergy) {
			super(inputSize, outputSize, baseProcess, baseEnergy);
		}

		@Override
		public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiSmeltingBlock(player.inventory, this);
		}
	}

	public static class ReinforcedFurnace extends SingleOutput {

		public ReinforcedFurnace() {
			super(1, 1, CalculatorConfig.REINFORCED_FURNACE_SPEED, CalculatorConfig.REINFORCED_FURNACE_USAGE);
			super.inv.getInsertFilters().put((SLOT,STACK,FACE)-> SLOT < this.inputSize(), EnumFilterType.EXTERNAL);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiSmeltingBlock.ReinforcedFurnace(player.inventory, this);
		}

		@Nonnull
		public ItemStack getFurnaceOutput(ItemStack stack) {
			if (!stack.isEmpty()) {
				return FurnaceRecipes.instance().getSmeltingResult(stack);
			}
			return ItemStack.EMPTY;
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
	}

    public static class StoneSeparator extends DualOutput {

        public StoneSeparator() {
            super(1, 2, CalculatorConfig.STONE_SEPERATOR_SPEED, CalculatorConfig.STONE_SEPERATOR_USAGE);
            super.storage.setCapacity(CalculatorConfig.STONE_SEPERATOR_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.STONE_SEPERATOR_TRANSFER_RATE);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return StoneSeparatorRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiDualOutputSmelting.StoneSeperator(player.inventory, this);
		}
	}

    /**
     * single process machines
     */
	public static class ReassemblyChamber extends SingleOutput {

		public ReassemblyChamber() {
			super(1, 1, CalculatorConfig.REASSEMBLY_CHAMBER_SPEED, CalculatorConfig.REASSEMBLY_CHAMBER_USAGE);
			super.storage.setCapacity(CalculatorConfig.REASSEMBLY_CHAMBER_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.REASSEMBLY_CHAMBER_TRANSFER_RATE);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ReassemblyChamberRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiSmeltingBlock.ReassemblyChamber(player.inventory, this);
		}
	}

	public static class RestorationChamber extends SingleOutput {

		public RestorationChamber() {
			super(1, 1, CalculatorConfig.RESTORATION_CHAMBER_SPEED, CalculatorConfig.RESTORATION_CHAMBER_USAGE);
			super.storage.setCapacity(CalculatorConfig.RESTORATION_CHAMBER_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.RESTORATION_CHAMBER_TRANSFER_RATE);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return RestorationChamberRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiSmeltingBlock.RestorationChamber(player.inventory, this);
		}
	}

	public static class ProcessingChamber extends SingleOutput {

		public ProcessingChamber() {
			super(1, 1, CalculatorConfig.PROCESSING_CHAMBER_SPEED, CalculatorConfig.PROCESSING_CHAMBER_USAGE);
			super.storage.setCapacity(CalculatorConfig.PROCESSING_CHAMBER_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.PROCESSING_CHAMBER_TRANSFER_RATE);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ProcessingChamberRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiSmeltingBlock.ProcessingChamber(player.inventory, this);
		}
	}

    /**
     * dual process machines
     */
    public static class AlgorithmSeparator extends DualOutput {

        public AlgorithmSeparator() {
            super(1, 2, CalculatorConfig.ALGORITHM_SEPERATOR_SPEED, CalculatorConfig.ALGORITHM_SEPERATOR_USAGE);
			super.storage.setCapacity(CalculatorConfig.ALGORITHM_SEPERATOR_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.ALGORITHM_SEPERATOR_TRANSFER_RATE);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return AlgorithmSeparatorRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiDualOutputSmelting.AlgorithmSeperator(player.inventory, this);
		}
	}

	public static class ExtractionChamber extends DualOutput {

		public ExtractionChamber() {
			super(1, 2, CalculatorConfig.EXTRACTION_CHAMBER_SPEED, CalculatorConfig.EXTRACTION_CHAMBER_USAGE);
			super.storage.setCapacity(CalculatorConfig.EXTRACTION_CHAMBER_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.EXTRACTION_CHAMBER_TRANSFER_RATE);
			upgrades = upgrades.setAllowed(16, "ENERGY", "SPEED", "TRANSFER", "VOID").addMaxiumum("TRANSFER", 1).addMaxiumum("VOID", 1);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return ExtractionChamberRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiDualOutputSmelting.ExtractionChamber(player.inventory, this);
		}

        @Override
		public boolean isOutputVoided(int slot, ItemStack outputStack) {
			return slot == 2 && upgrades.getUpgradesInstalled("VOID") == 1;
		}
	}

	public static class PrecisionChamber extends DualOutput {

		public PrecisionChamber() {
			super(1, 2, CalculatorConfig.PRECISION_CHAMBER_SPEED, CalculatorConfig.PRECISION_CHAMBER_USAGE);
			super.storage.setCapacity(CalculatorConfig.PRECISION_CHAMBER_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.PRECISION_CHAMBER_TRANSFER_RATE);
			upgrades = upgrades.setAllowed(16, "ENERGY", "SPEED", "TRANSFER", "VOID").addMaxiumum("TRANSFER", 1).addMaxiumum("VOID", 1);
		}

		@Override
		public RecipeHelperV2 recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiDualOutputSmelting.PrecisionChamber(player.inventory, this);
		}

        @Override
		public boolean isOutputVoided(int slot, ItemStack outputStack) {
			return slot == 2 && upgrades.getUpgradesInstalled("VOID") == 1;
		}
	}
}
