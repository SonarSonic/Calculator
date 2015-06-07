package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons.CircuitButton;
import sonar.calculator.mod.client.gui.utils.GuiButtons.PauseButton;
import sonar.calculator.mod.common.containers.ContainerAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.utils.helpers.FontHelper;

public class GuiAnalysingChamber extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation(
			"Calculator:textures/gui/analysischamber.png");
	public TileEntityAnalysingChamber entity;

	public GuiAnalysingChamber(InventoryPlayer inventoryPlayer,
			TileEntityAnalysingChamber entity) {
		super(new ContainerAnalysingChamber(inventoryPlayer, entity));

		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		String power= null;
	    switch(CalculatorConfig.energyStorageType){
		case 1: power = this.entity.storage.getEnergyStored() + " RF";	break;
		case 2: power = (this.entity.storage.getEnergyStored()/4) + " EU";	break;
		}
	    FontHelper.textCentre(power, xSize, 64, 2);


		if (this.entity.stable == 1) {
		    FontHelper.textCentre(StatCollector.translateToLocal("circuit.stable"), xSize, 12, 0);
		}

	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

	    int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
	    int j = 78 - k;
	    drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
	}

}
