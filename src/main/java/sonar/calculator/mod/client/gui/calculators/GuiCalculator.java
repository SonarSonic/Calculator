package sonar.calculator.mod.client.gui.calculators;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.calculator.mod.common.item.calculators.CalculatorItem.CalculatorInventory;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCalculator extends GuiContainer {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/calculator.png");

	public GuiCalculator(EntityPlayer player, InventoryPlayer inv, CalculatorInventory calculatorInventory, Map<Integer, Integer> map) {
		super(new ContainerCalculator(player, inv, calculatorInventory, map));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		FontHelper.textCentre(FontHelper.translate("item.Calculator.name"), xSize, 8, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);

		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}
