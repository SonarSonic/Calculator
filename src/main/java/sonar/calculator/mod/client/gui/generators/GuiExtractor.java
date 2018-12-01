package sonar.calculator.mod.client.gui.generators;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public abstract class GuiExtractor extends GuiSonarTile {

	public TileEntityGenerator entity;

	public GuiExtractor(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
		super(new ContainerGenerator(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyLevel()), this.xSize, 64, 2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int x, int y) {
		super.drawGuiContainerBackgroundLayer(var1, x, y);

		int k = (int)(this.entity.storage.getEnergyLevel() * 160 / this.entity.storage.getFullCapacity());
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 63, 0, 186, k, 10);
		int k2 = this.entity.itemLevel.getObject() * 138 / 5000;
		drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 17, 0, 166, k2, 10);

		if (this.entity.maxBurnTime.getObject() > 0) {
			int k3 = this.entity.burnTime.getObject() * 138 / this.entity.maxBurnTime.getObject();
			drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 41, 0, 176, k3, 10);
		}
	}

	public static class Starch extends GuiExtractor {

		public static final ResourceLocation starch = new ResourceLocation("Calculator:textures/gui/starchgenerator.png");

		public Starch(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return starch;
		}
	}

	public static class Redstone extends GuiExtractor {

		public static final ResourceLocation redstone = new ResourceLocation("Calculator:textures/gui/redstonegenerator.png");

		public Redstone(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return redstone;
		}
	}

	public static class Glowstone extends GuiExtractor {

		public static final ResourceLocation glowstone = new ResourceLocation("Calculator:textures/gui/glowstonegenerator.png");

		public Glowstone(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return glowstone;
		}
	}
}
