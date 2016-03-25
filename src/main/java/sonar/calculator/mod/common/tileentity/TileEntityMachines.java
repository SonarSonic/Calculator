package sonar.calculator.mod.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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
import sonar.core.utils.IGuiTile;
import sonar.core.utils.helpers.RecipeHelper;

public class TileEntityMachines {

	/** single process machines */
	public static class ReassemblyChamber extends TileEntityAbstractProcess {
		@Override
		public RecipeHelper recipeHelper() {
			return ReassemblyChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ReassemblyChamber(player.inventory, this);
		}

	}

	public static class RestorationChamber extends TileEntityAbstractProcess {
		@Override
		public RecipeHelper recipeHelper() {
			return RestorationChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.RestorationChamber(player.inventory, this);
		}
	}

	public static class ProcessingChamber extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return ProcessingChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ProcessingChamber(player.inventory, this);
		}
	}

	public static class ReinforcedFurnace extends TileEntityAbstractProcess {
		@Override
		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			return new ItemStack[] { FurnaceRecipes.instance().getSmeltingResult(stacks[0]) };
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerSmeltingBlock(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiSmeltingBlock.ReinforcedFurnace(player.inventory, this);
		}
	}

	/** dual process machines */
	public static class AlgorithmSeperator extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return AlgorithmSeparatorRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.AlgorithmSeperator(player.inventory, this);
		}
	}

	public static class ExtractionChamber extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return ExtractionChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}

		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			if (simulate) {
				return recipeHelper().getOutput(stacks);
			} else {
				ItemStack[] outputs = recipeHelper().getOutput(stacks);
				outputs[1] = rand.nextInt(8 + 1) == 8 ? new ItemStack(outputs[1].getItem(), 1, rand.nextInt(13 + 1)) : null;
				return outputs;
			}
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.ExtractionChamber(player.inventory, this);
		}

	}

	public static class PrecisionChamber extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}

		public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
			if (simulate) {
				return recipeHelper().getOutput(stacks);
			} else {
				ItemStack[] outputs = recipeHelper().getOutput(stacks);
				if (recipeHelper().containsStack(new ItemStack(Blocks.cobblestone, 1), stacks, false) != -1 || recipeHelper().containsStack(new ItemStack(Blocks.dirt, 1), stacks, false) != -1) {
					outputs[1] =  new ItemStack(outputs[1].getItem(), 1, rand.nextInt(13 + 1));
				}
				return outputs;
			}
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.PrecisionChamber(player.inventory, this);
		}
	}

	public static class StoneSeperator extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return StoneSeparatorRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDualOutputSmelting(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDualOutputSmelting.StoneSeperator(player.inventory, this);
		}

	}
}
