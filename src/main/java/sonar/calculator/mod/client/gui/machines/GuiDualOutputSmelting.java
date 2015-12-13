package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons.SonarButton;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.inventory.GuiSonar;
import sonar.core.utils.helpers.FontHelper;

public class GuiDualOutputSmelting extends GuiSonar {
	
	public TileEntityAbstractProcess entity;

	public GuiDualOutputSmelting(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
		super(new ContainerDualOutputSmelting(inventoryPlayer, entity), entity);

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public ResourceLocation getBackground() {
		return new ResourceLocation("Calculator:textures/gui/stoneseperator.png");
	}

	public void initGui() {
		super.initGui();
		this.buttonList.add(new CircuitButton(0,guiLeft + 149, guiTop + 23));
		this.buttonList.add(new PauseButton(1,guiLeft + 8, guiTop + 23, entity.isPaused()));
	}

	public void initGui(boolean pause) {
		super.initGui();
		this.buttonList.add(new CircuitButton(0,guiLeft + 149, guiTop + 23));
		this.buttonList.add(new PauseButton(1,guiLeft + 8, guiTop + 23, pause));
	}

	protected void actionPerformed(GuiButton button) {
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof CalculatorButtons.SonarButton) {
				SonarButton sButton = (SonarButton) button;
				sButton.onClicked();
			}
			if (button.id == 1) {
				entity.paused = !entity.paused;
			}
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(this.entity.getInventoryName(), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);

		if (this.entity.currentSpeed!= 0  && this.entity.cookTime != 0) {
			int l = this.entity.cookTime * 23 / this.entity.currentSpeed;
			drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 24, 176, 10, l, 16);
		}
	}

	public static class AlgorithmSeperator extends GuiDualOutputSmelting {
		public AlgorithmSeperator(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}
	}

	public static class StoneSeperator extends GuiDualOutputSmelting {

		public StoneSeperator(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}
	}

	public static class ExtractionChamber extends GuiDualOutputSmelting {

		public ExtractionChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return new ResourceLocation("Calculator:textures/gui/extractionchamber.png");
		}
	}

	public static class PrecisionChamber extends GuiDualOutputSmelting {
		public PrecisionChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return new ResourceLocation("Calculator:textures/gui/extractionchamber.png");
		}
	}

}
