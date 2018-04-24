package sonar.calculator.mod.client.gui.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiHungerProcessor extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/hungerprocessor.png");

	public TileEntityHungerProcessor entity;

	public GuiHungerProcessor(InventoryPlayer inventoryPlayer, TileEntityHungerProcessor entity) {
		super(new ContainerHungerProcessor(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		String points = FontHelper.translate("points.hunger") + ": " + this.entity.storedpoints;
		FontHelper.textCentre(points, xSize, 60, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
