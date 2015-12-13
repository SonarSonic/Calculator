package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.inventory.GuiSonar;
import sonar.core.inventory.GuiSonar.PauseButton;
import sonar.core.utils.helpers.FontHelper;

public class GuiAtomicMultiplier extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/atomicMultiplier.png");

	public TileEntityAtomicMultiplier entity;

	public GuiAtomicMultiplier(InventoryPlayer inventoryPlayer, TileEntityAtomicMultiplier entity) {
		super(new ContainerAtomicMultiplier(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (entity.active == 1) {
			FontHelper.textCentre(FontHelper.translate("locator.active"), xSize, 3, 0);
		} else {
			FontHelper.textCentre(FontHelper.translate("locator.idle"), xSize, 3, 0);
		}

		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 66, 2);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int changedEnergy = this.entity.storage.getEnergyStored() / 100000;
		int newEnergy = changedEnergy * 126 / 15000;
		drawTexturedModalRect(this.guiLeft + 41, this.guiTop + 65, 0, 166, newEnergy, 10);

		int c = this.entity.cookTime * 18 / this.entity.furnaceSpeed;
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 20, 176, 0, c, 9);
	}

}
