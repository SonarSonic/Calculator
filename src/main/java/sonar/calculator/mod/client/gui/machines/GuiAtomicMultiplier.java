package sonar.calculator.mod.client.gui.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GuiAtomicMultiplier extends GuiSonarTile {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/atomicMultiplier.png");
	public TileEntityAtomicMultiplier entity;

	public GuiAtomicMultiplier(InventoryPlayer inventoryPlayer, TileEntityAtomicMultiplier entity) {
		super(new ContainerAtomicMultiplier(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		if (entity.active.getObject() == 1) {
			FontHelper.textCentre(FontHelper.translate("locator.active"), xSize, 3, 0);
		} else {
			FontHelper.textCentre(FontHelper.translate("locator.idle"), xSize, 3, 0);
		}
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 66, 2);
        if (x > guiLeft + 2 && x < guiLeft + 16 && y > guiTop + 62 && y < guiTop + 76) {
            ArrayList<String> list = new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.##");
			list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + "Machine Stats");
			list.add("Usage: " + df.format(entity.getEnergyUsage()) + " rf/t");
			list.add("Speed: " + entity.getProcessTime() + " ticks");
            drawSpecialToolTip(list, x, y, fontRenderer);
		}
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int energy_level = (int)((double)this.entity.storage.getEnergyStored() * 128D / (double)this.entity.storage.getMaxEnergyStored());
		drawTexturedModalRect(this.guiLeft + 41, this.guiTop + 65, 0, 166, energy_level, 10);

        int c = this.entity.cookTime.getObject() * 18 / CalculatorConfig.ATOMIC_MULTIPLIER_SPEED;
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 20, 176, 0, c, 9);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}