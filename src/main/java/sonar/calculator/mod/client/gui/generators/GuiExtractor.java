package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerExtractor;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.core.helpers.FontHelper;

public abstract class GuiExtractor extends GuiContainer {

	public TileEntityGenerator entity;

	public GuiExtractor(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
		super(new ContainerExtractor(inventoryPlayer, entity));
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
	}

	public abstract ResourceLocation getResource();

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(getResource());
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = this.entity.storage.getEnergyStored() * 160 / 1000000;
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
		public ResourceLocation getResource() {
			return starch;
		}

	}

	public static class Redstone extends GuiExtractor {

		public static final ResourceLocation redstone = new ResourceLocation("Calculator:textures/gui/redstonegenerator.png");

		public Redstone(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getResource() {
			return redstone;
		}

	}

	public static class Glowstone extends GuiExtractor {

		public static final ResourceLocation glowstone = new ResourceLocation("Calculator:textures/gui/glowstonegenerator.png");

		public Glowstone(InventoryPlayer inventoryPlayer, TileEntityGenerator entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getResource() {
			return glowstone;
		}

	}
}
