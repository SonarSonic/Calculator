package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.client.gui.GuiSonar;
import sonar.core.client.gui.SonarButtons;
import sonar.core.client.gui.SonarButtons.SonarButton;
import sonar.core.helpers.FontHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GuiSmeltingBlock extends GuiSonar {

	public TileEntityAbstractProcess entity;

	public GuiSmeltingBlock(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
		super(new ContainerSmeltingBlock(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getBackground() {
		return new ResourceLocation("Calculator:textures/gui/restorationchamber.png");
	}

    @Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new CircuitButton(entity.getUpgradeInventory(), 0, guiLeft + 150 - 14, guiTop + 23));
		this.buttonList.add(new PauseButton(this, entity, 1, guiLeft + 8 + 14, guiTop + 23, entity.isPaused()));
	}

	public void initGui(boolean pause) {
		super.initGui();
		this.buttonList.add(new CircuitButton(entity.getUpgradeInventory(), 0, guiLeft + 150 - 14, guiTop + 23));
		this.buttonList.add(new PauseButton(this, entity, 1, guiLeft + 8 + 14, guiTop + 23, pause));
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		FontHelper.textCentre(this.entity.getName(), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
        if (x > guiLeft + 130 && x < guiLeft + 144 && y > guiTop + 60 && y < guiTop + 74) {
            ArrayList<String> list = new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.##");
			list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + "Machine Stats");
			list.add("Usage: " + df.format(entity.getEnergyUsage()) + " rf/t");
			list.add("Speed: " + entity.getProcessTime() + " ticks");
            this.drawSpecialToolTip(list, x, y, fontRenderer);
		}
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);

		if (this.entity.currentSpeed != 0 && this.entity.cookTime.getObject() != 0) {
			int l = this.entity.cookTime.getObject() * 23 / this.entity.currentSpeed;
			drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 24, 176, 10, l, 16);
		}
	}

    @Override
	protected void actionPerformed(GuiButton button) {
		if (entity.getWorld().isRemote) {
			if (button != null && button instanceof SonarButtons.SonarButton) {
				SonarButton sButton = (SonarButton) button;
				sButton.onClicked();
			}
		}
	}

	public static class RestorationChamber extends GuiSmeltingBlock {
		public RestorationChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}
	}

	public static class ReassemblyChamber extends GuiSmeltingBlock {
		public ReassemblyChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}
	}

	public static class ProcessingChamber extends GuiSmeltingBlock {
		public ProcessingChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}
	}

	public static class ReinforcedFurnace extends GuiSmeltingBlock {
		public ReinforcedFurnace(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
			super(inventoryPlayer, entity);
		}

		@Override
		public ResourceLocation getBackground() {
			return new ResourceLocation("Calculator:textures/gui/reinforcedFurnace.png");
		}
	}
}