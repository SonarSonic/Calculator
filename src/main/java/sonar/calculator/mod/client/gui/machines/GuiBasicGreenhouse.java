package sonar.calculator.mod.client.gui.machines;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.core.SonarCore;
import sonar.core.client.gui.GuiSonar;
import sonar.core.client.gui.SonarButtons;
import sonar.core.client.gui.SonarButtons.SonarButton;
import sonar.core.helpers.FontHelper;

public class GuiBasicGreenhouse extends GuiSonar {
	DecimalFormat dec = new DecimalFormat("##.##");
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/basicgreenhouse.png");

	public TileEntityBasicGreenhouse entity;

	public GuiBasicGreenhouse(InventoryPlayer inventoryPlayer, TileEntityBasicGreenhouse entity) {
		super(new ContainerBasicGreenhouse(inventoryPlayer, entity), entity);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 192;
	}

	public void initGui() {
		super.initGui();
		buttonList.add(new GreenhouseButton(0, guiLeft + 18, guiTop + 62, 14, 14, FontHelper.translate("greenhouse.build")));
		buttonList.add(new GreenhouseButton(1, guiLeft + 36, guiTop + 62, 14, 14, FontHelper.translate("greenhouse.rebuild")));
		buttonList.add(new GreenhouseButton(2, guiLeft + 54, guiTop + 62, 14, 14, FontHelper.translate("greenhouse.demolish")));
		this.buttonList.add(new PauseButton(this, entity, 3, guiLeft + 6, guiTop + 6, entity.isPaused()));
	}

	@SideOnly(Side.CLIENT)
	public class GreenhouseButton extends GuiButton {
		public String name;

		public GreenhouseButton(int id, int x, int y, int texX, int texY, String name) {
			super(id, x, y, texX, texY, name);
			this.name = name;
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		}
	}

	protected void actionPerformed(GuiButton button) {
		if (button != null && button instanceof SonarButtons.SonarButton) {
			SonarButton sButton = (SonarButton) button;
			sButton.onClicked();
		} else {
			SonarCore.sendPacketToServer(entity, button.id);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		if (entity.wasBuilt.getObject()) {
			double car = (double) this.entity.carbon.getObject() * 100 / this.entity.maxLevel;
			String carbon = dec.format(car) + "%";
			FontHelper.textOffsetCentre(carbon, 115, 79, 2);
			double oxy = (double) this.entity.getOxygen() * 100 / this.entity.maxLevel;
			String oxygen = dec.format(oxy) + "%";
			FontHelper.textOffsetCentre(oxygen, 151, 79, 2);
		}

		for (GuiButton b : buttonList) {
			if (b instanceof GreenhouseButton) {
				GreenhouseButton button = (GreenhouseButton) b;
				if (x >= button.xPosition && y >= button.yPosition && x < button.xPosition + button.width && y < button.yPosition + button.height) {
					drawCreativeTabHoveringText(FontHelper.translate(button.name), x - guiLeft, y - guiTop);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);

		int e = this.entity.storage.getEnergyStored() * 46 / this.entity.storage.getMaxEnergyStored();
		this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 46 + 11 - e, 176, 46 - e, 14, 46);

		if (entity.wasBuilt.getObject()) {
			int c = this.entity.carbon.getObject() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 11 + 66 - c, 190, 66 - c, 28, 66);

			int o = this.entity.getOxygen() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 137, this.guiTop + 11 + 66 - o, 218, 66 - o, 28, 66);
		}
		if (entity.slots()[0] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.LOG), this.guiLeft + 19 + 7, this.guiTop + 28 - 6);
		}
		if (entity.slots()[1] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.OAK_STAIRS), this.guiLeft + 37 + 7, this.guiTop + 28 - 6);
		}
		if (entity.slots()[2] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.GLASS), this.guiLeft + 19 + 7, this.guiTop + 46 - 6);
		}
		if (entity.slots()[3] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.PLANKS), this.guiLeft + 37 + 7, this.guiTop + 46 - 6);
		}
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
