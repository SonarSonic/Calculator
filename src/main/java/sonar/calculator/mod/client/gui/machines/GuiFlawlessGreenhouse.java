package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.SonarCore;
import sonar.core.client.gui.GuiSonar;
import sonar.core.client.gui.SonarButtons;
import sonar.core.client.gui.SonarButtons.SonarButton;
import sonar.core.helpers.FontHelper;

import java.text.DecimalFormat;

public class GuiFlawlessGreenhouse extends GuiSonar {
	DecimalFormat dec = new DecimalFormat("##.##");
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/flawlessgreenhouse.png");

	public TileEntityFlawlessGreenhouse entity;

	public GuiFlawlessGreenhouse(InventoryPlayer inventoryPlayer, TileEntityFlawlessGreenhouse entity) {
		super(new ContainerFlawlessGreenhouse(inventoryPlayer, entity), entity);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 192;
	}

    @Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new PauseButton(this, entity, 3, guiLeft + 6, guiTop + 6, entity.isPaused()));
	}

    @Override
	protected void actionPerformed(GuiButton button) {
		if (button != null && button instanceof SonarButtons.SonarButton) {
			SonarButton sButton = (SonarButton) button;
			sButton.onClicked();
		} else {
			SonarCore.sendPacketToServer(entity, button.id);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (entity.wasBuilt.getObject()) {
			double car = (double) this.entity.carbon.getObject() * 100 / this.entity.maxLevel;
            String carbon = dec.format(car) + '%';
			FontHelper.textOffsetCentre(carbon, 61, 79, 0);
			double oxy = (double) this.entity.getOxygen() * 100 / this.entity.maxLevel;
            String oxygen = dec.format(oxy) + '%';
			FontHelper.textOffsetCentre(oxygen, 97, 79, 0);
		}
		String size = FontHelper.translate("greenhouse.size") + ": " + entity.houseSize;
		FontHelper.textOffsetCentre(size, 144, 10, 0);

		String grownName = FontHelper.translate("greenhouse.grown") + ": ";
		FontHelper.textOffsetCentre(grownName, 144, 30, 0);
        FontHelper.textOffsetCentre(String.valueOf(entity.plantsGrown), 144, 40, 0);

		String harvestName = FontHelper.translate("greenhouse.harvested") + ": ";
		FontHelper.textOffsetCentre(harvestName, 144, 60, 0);
        FontHelper.textOffsetCentre(String.valueOf(entity.plantsHarvested), 144, 70, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int e = this.entity.storage.getEnergyStored() * 46 / this.entity.storage.getMaxEnergyStored();
		this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 46 + 11 - e, 176, 46 - e, 14, 46);

		if (entity.wasBuilt.getObject()) {
			int c = this.entity.carbon.getObject() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 47, this.guiTop + 11 + 66 - c, 190, 66 - c, 28, 66);

			int o = this.entity.getOxygen() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 11 + 66 - o, 218, 66 - o, 28, 66);
		}
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
