package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.gui.utils.GuiSonar;
import sonar.calculator.mod.common.containers.ContainerAlgorithmAssimilator;
import sonar.calculator.mod.common.containers.ContainerAssimilator;
import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.utils.helpers.FontHelper;

public class GuiAlgorithmAssimilator extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/assimilator.png");

	public TileEntityAssimilator.Algorithm entity;

	public GuiAlgorithmAssimilator(InventoryPlayer inventoryPlayer, TileEntityAssimilator entity) {
		super(new ContainerAlgorithmAssimilator(inventoryPlayer, entity), entity);

		this.entity = (TileEntityAssimilator.Algorithm)entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 8, 0);	
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

}
