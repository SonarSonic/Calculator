package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.helpers.FontHelper;

public class GuiConductorMast extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/conductorMast.png");

	public TileEntityConductorMast entity;

	public GuiConductorMast(InventoryPlayer inventoryPlayer, TileEntityConductorMast entity) {
		super(new ContainerConductorMast(inventoryPlayer, entity));
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);

		String wait = FontHelper.translate("conductor.wait") + ": ";
		if (!(this.entity.lightningSpeed.getObject() < 600) && this.entity.lightingTicks.getObject() < 500) {
			switch (this.entity.random.getObject()) {
			case 0:
				String power = wait + FontHelper.translate("conductor.msg");
				this.fontRendererObj.drawString(power, this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2, 46, 4210752);
				break;
			case 1:
				String power1 = wait + FontHelper.translate("conductor.msg1");
				this.fontRendererObj.drawString(power1, this.xSize / 2 - this.fontRendererObj.getStringWidth(power1) / 2, 46, 4210752);
				break;
			case 2:
				String power2 = wait + FontHelper.translate("conductor.msg2");
				this.fontRendererObj.drawString(power2, this.xSize / 2 - this.fontRendererObj.getStringWidth(power2) / 2, 46, 4210752);
				break;
			case 3:
				String power3 = wait + FontHelper.translate("conductor.msg3");
				this.fontRendererObj.drawString(power3, this.xSize / 2 - this.fontRendererObj.getStringWidth(power3) / 2, 46, 4210752);
				break;
			case 4:
				String power4 = wait + FontHelper.translate("conductor.msg4");
				this.fontRendererObj.drawString(power4, this.xSize / 2 - this.fontRendererObj.getStringWidth(power4) / 2, 46, 4210752);
				break;
			case 5:
				String power5 = wait + FontHelper.translate("conductor.msg5");
				this.fontRendererObj.drawString(power5, this.xSize / 2 - this.fontRendererObj.getStringWidth(power5) / 2, 46, 4210752);
				break;
			case 6:
				String power6 = wait + FontHelper.translate("conductor.msg6");
				this.fontRendererObj.drawString(power6, this.xSize / 2 - this.fontRendererObj.getStringWidth(power6) / 2, 46, 4210752);
				break;
			case 7:
				String power7 = wait + FontHelper.translate("conductor.msg7");
				this.fontRendererObj.drawString(power7, this.xSize / 2 - this.fontRendererObj.getStringWidth(power7) / 2, 46, 4210752);
				break;
			case 8:
				String power8 = wait + FontHelper.translate("conductor.msg8");
				this.fontRendererObj.drawString(power8, this.xSize / 2 - this.fontRendererObj.getStringWidth(power8) / 2, 46, 4210752);
				break;
			}

		} else {
			String power = wait + (this.entity.lightningSpeed.getObject() / 20 - this.entity.lightingTicks.getObject() / 20) + " " + FontHelper.translate("lightning.seconds");
			this.fontRendererObj.drawString(power, this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2, 46, 4210752);
		}

		FontHelper.textOffsetCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), 90, 66, 2);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int c = this.entity.cookTime.getObject() * 18 / this.entity.furnaceSpeed;
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 26, 176, 0, c, 9);

		int l = this.entity.storage.getEnergyStored() * 145 / this.entity.storage.getMaxEnergyStored();
		drawTexturedModalRect(this.guiLeft + 22, this.guiTop + 59, 0, 166, l, 20);

	}
}
