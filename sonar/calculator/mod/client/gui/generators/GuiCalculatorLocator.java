package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons.CircuitButton;
import sonar.calculator.mod.client.gui.utils.GuiButtons.PauseButton;
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
	public void drawGuiContainerForegroundLayer(int par1, int par2) {

		if (this.entity.multiblockstring()) {
			FontHelper.text(StatCollector.translateToLocal("locator.multiblock") + ": " + StatCollector.translateToLocal("locator.true"), 25, 21, 0);

			if (this.entity.stability ==0 || this.entity.size==0) {
				FontHelper.text((StatCollector.translateToLocal("circuit.stability") + ": "+ 0 + "%"), 25, 43, 2);
			} else {
				FontHelper.text(StatCollector.translateToLocal("circuit.stability") + ": "+ String.valueOf((this.entity.stability  *100 / (((2*entity.size)+1)*((2*entity.size)+1)-1)))  + "%", 25, 43, 0);
			}
		} else {
			FontHelper.text(StatCollector.translateToLocal("locator.multiblock") + ": " + StatCollector.translateToLocal("locator.false"), 25, 21, 2);
			FontHelper.text(StatCollector.translateToLocal("circuit.stability") + ": " + StatCollector.translateToLocal("locator.unknown"), 25, 43, 2);

		}
		if (this.entity.active == 1) {
			FontHelper.text(StatCollector.translateToLocal("locator.active")+": " + this.entity.currentGenerated() + " rf/t", 25, 10, 0);
		} else {
			FontHelper.text(StatCollector.translateToLocal("locator.active")+": " + StatCollector.translateToLocal("locator.false"), 25, 10, 2);
		}

		if (this.entity.ownerUsername() != "None") {
			FontHelper.text(StatCollector.translateToLocal("locator.owner") + ": " + this.entity.ownerUsername(), 25, 32, 0);
		} else {
			FontHelper.text(StatCollector.translateToLocal("locator.owner") + ": " + StatCollector.translateToLocal("locator.none"), 25, 32, 2);
		}

		String power = null;
		switch (CalculatorConfig.energyStorageType) {
		case 1:
			power = this.entity.storage.getEnergyStored() + " RF";
			break;
		case 2:
			power = (this.entity.storage.getEnergyStored() / 4) + " EU";
			break;
		}
		FontHelper.textCentre(power, this.xSize, 64, 2);
	    super.drawGuiContainerForegroundLayer(par1, par2);
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
