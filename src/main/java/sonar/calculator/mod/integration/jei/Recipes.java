package sonar.calculator.mod.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.core.handlers.energy.DischargeValues;
import sonar.core.helpers.FontHelper;
import sonar.core.integration.jei.JEISonarRecipe;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.RecipeHelperV2;

public class Recipes {

	public static class Processing extends JEISonarRecipe<Processing> {

		public Processing(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Restoration extends JEISonarRecipe<Restoration> {

		public Restoration(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Reassembly extends JEISonarRecipe<Reassembly> {

		public Reassembly(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Algorithm extends JEISonarRecipe<Algorithm> {

		public Algorithm(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Stone extends JEISonarRecipe<Stone> {

		public Stone(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Extraction extends JEISonarRecipe<Extraction> {

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

	public static class Precision extends JEISonarRecipe<Precision> {

		public Precision(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Calculator extends JEISonarRecipe<Calculator> {

		public Calculator(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Scientific extends JEISonarRecipe<Scientific> {

		public Scientific(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Atomic extends JEISonarRecipe<Atomic> {

		public Atomic(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Flawless extends JEISonarRecipe<Flawless> {

		public Flawless(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Health extends JEISonarRecipe<Health> {

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

	public static class Analysing extends JEISonarRecipe<Analysing> {

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
			FontHelper.textCentre(FontHelper.translate("info.extractChance") + " = " + chance + " %", (int) (recipeWidth * (1.0 / 0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Discharge extends JEISonarRecipe<Discharge> {

		public Discharge(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}

        @Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			FontHelper.textCentre(" " + DischargeValues.instance().getValue((ItemStack) this.inputs.get(0).get(0)) + " Discharge",	(int) (recipeWidth * (1.0 / 0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Conductor extends JEISonarRecipe<Conductor> {

		public Conductor(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Fabrication extends JEISonarRecipe<Fabrication> {

		public Fabrication(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}

	public static class Harvest extends JEISonarRecipe<Harvest> {

		public Harvest(RecipeHelperV2 helper, ISonarRecipe recipe) {
			super(helper, recipe);
		}
	}
}
