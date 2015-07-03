package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.utils.GuiSonar;
import sonar.calculator.mod.client.gui.utils.GuiSonar.CircuitButton;
import sonar.calculator.mod.client.gui.utils.GuiSonar.PauseButton;
import sonar.calculator.mod.common.containers.ContainerCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.core.utils.helpers.FontHelper;

public class GuiCalculatorLocator extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation(
			"Calculator:textures/gui/guicalculatorlocator.png");

	public TileEntityCalculatorLocator entity;

	public GuiCalculatorLocator(InventoryPlayer inventoryPlayer,
			TileEntityCalculatorLocator entity) {
		super(new ContainerCalculatorLocator(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {

		if (this.entity.multiblockstring()) {
			FontHelper.text(FontHelper.translate("locator.multiblock") + ": " + FontHelper.translate("locator.true"), 25, 21, 0);

			if (this.entity.stability ==0 || this.entity.size==0) {
				FontHelper.text((FontHelper.translate("circuit.stability") + ": "+ 0 + "%"), 25, 43, 2);
			} else {
				FontHelper.text(FontHelper.translate("circuit.stability") + ": "+ String.valueOf((this.entity.stability  *100 / (((2*entity.size)+1)*((2*entity.size)+1)-1)))  + "%", 25, 43, 0);
			}
		} else {
			FontHelper.text(FontHelper.translate("locator.multiblock") + ": " + FontHelper.translate("locator.false"), 25, 21, 2);
			FontHelper.text(FontHelper.translate("circuit.stability") + ": " + FontHelper.translate("locator.unknown"), 25, 43, 2);

		}
		if (this.entity.active == 1) {
			FontHelper.text(FontHelper.translate("locator.active")+": " + FontHelper.formatOutput(this.entity.currentGenerated()), 25, 10, 0);
		} else {
			FontHelper.text(FontHelper.translate("locator.active")+": " + FontHelper.translate("locator.false"), 25, 10, 2);
		}

		if (this.entity.ownerUsername() != "None") {
			FontHelper.text(FontHelper.translate("locator.owner") + ": " + this.entity.ownerUsername(), 25, 32, 0);
		} else {
			FontHelper.text(FontHelper.translate("locator.owner") + ": " + FontHelper.translate("locator.none"), 25, 32, 2);
		}
		
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
	    super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,	int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,this.ySize);

		int k = this.entity.storage.getEnergyStored() * 78 / 25000000;
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k,10);
	}

}
