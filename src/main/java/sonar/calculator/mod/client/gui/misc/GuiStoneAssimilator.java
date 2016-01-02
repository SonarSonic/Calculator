package sonar.calculator.mod.client.gui.misc;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerAssimilator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.inventory.GuiSonar;
import sonar.core.utils.helpers.FontHelper;

public class GuiStoneAssimilator extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");

	public TileEntityAssimilator.Stone entity;

	public GuiStoneAssimilator(InventoryPlayer inventoryPlayer, TileEntityAssimilator entity) {
		super(new ContainerAssimilator(inventoryPlayer, entity), entity);

		this.entity = (TileEntityAssimilator.Stone)entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
		FontHelper.textCentre("Hunger Points: " + entity.hungerPoints, xSize, 60, 0);		
		FontHelper.textCentre("Health Points: " + entity.healthPoints, xSize, 70, 0);			
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

}
