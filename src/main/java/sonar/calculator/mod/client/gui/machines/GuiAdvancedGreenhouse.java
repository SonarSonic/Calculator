package sonar.calculator.mod.client.gui.machines;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.core.SonarCore;
import sonar.core.client.gui.SonarButtons.ImageButton;
import sonar.core.helpers.FontHelper;

public class GuiAdvancedGreenhouse extends GuiContainer {
	DecimalFormat dec = new DecimalFormat("##.##");
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/advancedgreenhouse.png");

	public TileEntityAdvancedGreenhouse entity;

	public GuiAdvancedGreenhouse(InventoryPlayer inventoryPlayer, TileEntityAdvancedGreenhouse entity) {
		super(new ContainerAdvancedGreenhouse(inventoryPlayer, entity));
		this.entity = entity;
		this.ySize = 192;
	}

	public void initGui() {
		super.initGui();
		buttonList.add(new GreenhouseButton(0, guiLeft + 18, guiTop + 68, 14, 14, FontHelper.translate("greenhouse.build")));
		buttonList.add(new GreenhouseButton(1, guiLeft + 36, guiTop + 68, 14, 14, FontHelper.translate("greenhouse.rebuild")));
		buttonList.add(new GreenhouseButton(2, guiLeft + 54, guiTop + 68, 14, 14, FontHelper.translate("greenhouse.demolish")));
	}

	@SideOnly(Side.CLIENT)
	public class GreenhouseButton extends GuiButton {
		public String name;

		public GreenhouseButton(int id, int x, int y, int texX, int texY, String name) {
			super(id, x, y, texX, texY, name);
			//this.visible = false;
			this.name = name;
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			//if (this.isMouseOver())
				//drawCreativeTabHoveringText(FontHelper.translate(name), mouseX, mouseY);
			//super.drawButton(mc, mouseX, mouseY);
		}
	}

	protected void actionPerformed(GuiButton button) {
		SonarCore.sendPacketToServer(entity, button.id);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		if (entity.state.getObject() != State.BUILDING) {
			double car = (double) this.entity.carbon.getObject() * 100 / this.entity.maxLevel;
			String carbon = dec.format(car) + "%";
			FontHelper.textOffsetCentre(carbon, 115, 79, 2);
			double oxy = (double) this.entity.getOxygen() * 100 / this.entity.maxLevel;
			String oxygen = dec.format(oxy) + "%";
			FontHelper.textOffsetCentre(oxygen, 151, 79, 2);
		}
		for(GuiButton b : buttonList){
			GreenhouseButton button=(GreenhouseButton) b;
			if(x >= button.xPosition && y >= button.yPosition && x < button.xPosition + button.width && y < button.yPosition + button.height){
				drawCreativeTabHoveringText(FontHelper.translate(button.name), x-guiLeft, y-guiTop);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int e = this.entity.storage.getEnergyStored() * 46 / this.entity.storage.getMaxEnergyStored();
		this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 46 + 11 - e, 176, 46 - e, 14, 46);

		if (entity.wasBuilt.getObject()) {
			int c = this.entity.carbon.getObject() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 11 + 66 - c, 190, 66 - c, 28, 66);

			int o = this.entity.getOxygen() * 66 / this.entity.maxLevel;
			this.drawTexturedModalRect(this.guiLeft + 137, this.guiTop + 11 + 66 - o, 218, 66 - o, 28, 66);
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		if (entity.slots()[1] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.OAK_STAIRS, 64), this.guiLeft + 12 - 4 + 9, this.guiTop + 33 - 4);
		}
		if (entity.slots()[2] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.OAK_STAIRS, 64), this.guiLeft + 30 - 4 + 9, this.guiTop + 33 - 4);
		}
		if (entity.slots()[3] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.OAK_STAIRS, 52), this.guiLeft + 48 - 4 + 9, this.guiTop + 33 - 4);
		}
		if (entity.slots()[0] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.LOG, 30), this.guiLeft + 30 - 4 + 9, this.guiTop + 15 - 4);
		}
		if (entity.slots()[4] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.GLASS, 64), this.guiLeft + 12 - 4 + 9, this.guiTop + 51 - 4);
		}
		if (entity.slots()[5] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.GLASS, 26), this.guiLeft + 30 - 4 + 9, this.guiTop + 51 - 4);
		}
		if (entity.slots()[6] == null) {
			itemRender.renderItemIntoGUI(new ItemStack(Blocks.PLANKS, 40), this.guiLeft + 48 - 4 + 9, this.guiTop + 51 - 4);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
