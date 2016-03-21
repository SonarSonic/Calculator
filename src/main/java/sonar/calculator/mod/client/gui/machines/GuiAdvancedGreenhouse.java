package sonar.calculator.mod.client.gui.machines;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.core.utils.helpers.FontHelper;

public class GuiAdvancedGreenhouse extends GuiContainer {
	DecimalFormat dec = new DecimalFormat("##.##");
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/advancedgreenhouse.png");

	public TileEntityAdvancedGreenhouse entity;

	public GuiAdvancedGreenhouse(InventoryPlayer inventoryPlayer, TileEntityAdvancedGreenhouse entity) {
		super(new ContainerAdvancedGreenhouse(inventoryPlayer, entity));
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (entity.wasBuilt()) {
			double car = (double) this.entity.carbonLevels * 100 / this.entity.maxLevel;
			String carbon = dec.format(car) + "%";
			FontHelper.textOffsetCentre(carbon, 115, 79, 2);
			double oxy = (double) this.entity.getOxygen() * 100 / this.entity.maxLevel;
			String oxygen = dec.format(oxy) + "%";
			FontHelper.textOffsetCentre(oxygen, 151, 79, 2);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int e = this.entity.storage.getEnergyStored() * 46 / this.entity.storage.getMaxEnergyStored();
		this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 46 + 11 - e, 176, 46 - e, 14, 46);

		if (entity.wasBuilt()) {
			int c = this.entity.carbonLevels * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 11 + 66 - c, 190, 66 - c, 28, 66);

			int o = this.entity.getOxygen() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 137, this.guiTop + 11 + 66 - o, 218, 66 - o, 28, 66);
		}
		if (entity.slots()[1] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.oak_stairs, 64), this.guiLeft + 12 - 4, this.guiTop + 33 + 3);
		}
		if (entity.slots()[2] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.oak_stairs, 64), this.guiLeft + 30 - 4, this.guiTop + 33 + 3);
		}
		if (entity.slots()[3] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.oak_stairs, 52), this.guiLeft + 48 - 4, this.guiTop + 33 + 3);
		}
		if (entity.slots()[0] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.log, 30), this.guiLeft + 30 - 4, this.guiTop + 15 + 3);
		}
		if (entity.slots()[4] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.glass, 64), this.guiLeft + 12 - 4, this.guiTop + 51 + 3);
		}
		if (entity.slots()[5] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.glass, 26), this.guiLeft + 30 - 4, this.guiTop + 51 + 3);
		}
		if (entity.slots()[6] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.planks, 40), this.guiLeft + 48 - 4, this.guiTop + 51 + 3);
		}
	}
}
