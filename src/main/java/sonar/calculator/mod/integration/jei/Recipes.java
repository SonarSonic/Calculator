package sonar.calculator.mod.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.core.handlers.energy.DischargeValues;
import sonar.core.helpers.FontHelper;
import sonar.core.integration.jei.JEIRecipe;
import sonar.core.integration.jei.JEIRecipeV2;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.RecipeHelperV2;

public class Recipes {

	public static class Processing extends JEIRecipeV2<Processing> {

		public Processing(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Restoration extends JEIRecipeV2<Restoration> {

		public Restoration(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Reassembly extends JEIRecipeV2<Reassembly> {

		public Reassembly(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Algorithm extends JEIRecipeV2<Algorithm> {

		public Algorithm(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Stone extends JEIRecipeV2<Stone> {

		public Stone(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Extraction extends JEIRecipeV2<Extraction> {

		public Extraction(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}

        @Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			ItemStack stack = this.outputs.get(1);
			if (stack.getItem() == sonar.calculator.mod.Calculator.circuitBoard
					|| stack.getItem() == sonar.calculator.mod.Calculator.circuitDamaged
					|| stack.getItem() == sonar.calculator.mod.Calculator.circuitDirty) {
				GL11.glPushMatrix();
				GL11.glScaled(0.7, 0.7, 0.7);
                minecraft.fontRenderer.drawString("12.5%", 123, 39, 0);
				GL11.glPopMatrix();
			}
		}
	}

	public static class Precision extends JEIRecipeV2<Precision> {

		public Precision(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Calculator extends JEIRecipeV2<Calculator> {

		public Calculator(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Scientific extends JEIRecipeV2<Scientific> {

		public Scientific(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Atomic extends JEIRecipeV2<Atomic> {

		public Atomic(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Flawless extends JEIRecipeV2<Flawless> {

		public Flawless(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Health extends JEIRecipeV2<Health> {

		public Health(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}

        @Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);

			FontHelper.textCentre(
					" " + HealthProcessorRecipes.instance().getValue(null, this.inputs.get(0).iterator().next())
							+ " Health Points",
					(int) (recipeWidth * (1.0 / 0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Analysing extends JEIRecipeV2<Analysing> {

		public Analysing(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}

        @Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			int outputPos = recipe.inputs().get(0).getStackSize();
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			double chance = 0;
			switch (outputPos) {
			case 1:
				chance = 6;
				break;
			case 2:
				chance = 0.2;
				break;
			case 3:
				chance = 0.1;
				break;
			case 4:
				chance = 0.02;
				break;
			case 5:
				chance = 0.01;
				break;
			}
			FontHelper.textCentre(FontHelper.translate("info.extractChance") + " = " + chance + " %",
					(int) (recipeWidth * (1.0 / 0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Discharge extends JEIRecipe<Discharge> {

		@Override
		public Discharge getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Discharge().setRecipe(recipeID, inputs, outputs);
		}

        @Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			FontHelper.textCentre(" " + DischargeValues.getValueOf((ItemStack) this.inputs.get(0)) + " Discharge",
					(int) (recipeWidth * (1.0 / 0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Conductor extends JEIRecipeV2<Conductor> {

		public Conductor(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Fabrication extends JEIRecipeV2<Fabrication> {

		public Fabrication(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Harvest extends JEIRecipeV2<Harvest> {

		public Harvest(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}
}
