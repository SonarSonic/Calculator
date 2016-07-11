package sonar.calculator.mod.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.recipes.machines.HealthProcessorRecipes;
import sonar.core.energy.DischargeValues;
import sonar.core.helpers.FontHelper;
import sonar.core.integration.jei.JEIRecipe;

public class Recipes {

	public static class Processing extends JEIRecipe<Processing> {

		@Override
		public Processing getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Processing().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Restoration extends JEIRecipe<Restoration> {

		@Override
		public Restoration getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Restoration().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Reassembly extends JEIRecipe<Reassembly> {

		@Override
		public Reassembly getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Reassembly().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Algorithm extends JEIRecipe<Algorithm> {

		@Override
		public Algorithm getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Algorithm().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Stone extends JEIRecipe<Stone> {

		@Override
		public Stone getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Stone().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Extraction extends JEIRecipe<Extraction> {

		@Override
		public Extraction getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Extraction().setRecipe(recipeID, inputs, outputs);
		}

		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			minecraft.fontRendererObj.drawString("12.5%", 123, 39, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Precision extends JEIRecipe<Precision> {

		@Override
		public Precision getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Precision().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Calculator extends JEIRecipe<Calculator> {

		@Override
		public Calculator getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Calculator().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Scientific extends JEIRecipe<Scientific> {

		@Override
		public Scientific getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Scientific().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Atomic extends JEIRecipe<Atomic> {

		@Override
		public Atomic getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Atomic().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Flawless extends JEIRecipe<Flawless> {

		@Override
		public Flawless getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Flawless().setRecipe(recipeID, inputs, outputs);
		}
	}

	public static class Health extends JEIRecipe<Health> {

		@Override
		public Health getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Health().setRecipe(recipeID, inputs, outputs);
		}

		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			FontHelper.textCentre(" " + HealthProcessorRecipes.instance().getOutput((ItemStack) this.inputs.get(0))  + " Health Points", (int) (recipeWidth*(1.0/0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}
	
	public static class Discharge extends JEIRecipe<Discharge> {

		@Override
		public Discharge getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Discharge().setRecipe(recipeID, inputs, outputs);
		}

		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {
			GL11.glPushMatrix();
			GL11.glScaled(0.7, 0.7, 0.7);
			FontHelper.textCentre(" " + DischargeValues.getValueOf((ItemStack) this.inputs.get(0)) + " Discharge", (int) (recipeWidth*(1.0/0.7)), 40, 0);
			GL11.glPopMatrix();
		}
	}

	public static class Conductor extends JEIRecipe<Conductor> {

		@Override
		public Conductor getInstance(String recipeID, Object[] inputs, Object[] outputs) {
			return new Conductor().setRecipe(recipeID, inputs, outputs);
		}
	}
}
