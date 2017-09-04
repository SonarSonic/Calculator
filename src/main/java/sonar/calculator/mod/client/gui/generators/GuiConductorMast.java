package sonar.calculator.mod.client.gui.generators;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import sonar.calculator.mod.common.containers.ContainerConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GuiConductorMast extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/conductorMast.png");

	public TileEntityConductorMast entity;

	public GuiConductorMast(InventoryPlayer inventoryPlayer, TileEntityConductorMast entity) {
		super(new ContainerConductorMast(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);

		String wait = FontHelper.translate("conductor.wait") + ": ";
		if (!(this.entity.lightningSpeed.getObject() < 600) && this.entity.lightingTicks.getObject() < 500) {
			switch (this.entity.random.getObject()) {
			case 0:
				String power = wait + FontHelper.translate("conductor.msg");
                    this.fontRenderer.drawString(power, this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2, 46, 4210752);
				break;
			case 1:
				String power1 = wait + FontHelper.translate("conductor.msg1");
                    this.fontRenderer.drawString(power1, this.xSize / 2 - this.fontRenderer.getStringWidth(power1) / 2, 46, 4210752);
				break;
			case 2:
				String power2 = wait + FontHelper.translate("conductor.msg2");
                    this.fontRenderer.drawString(power2, this.xSize / 2 - this.fontRenderer.getStringWidth(power2) / 2, 46, 4210752);
				break;
			case 3:
				String power3 = wait + FontHelper.translate("conductor.msg3");
                    this.fontRenderer.drawString(power3, this.xSize / 2 - this.fontRenderer.getStringWidth(power3) / 2, 46, 4210752);
				break;
			case 4:
				String power4 = wait + FontHelper.translate("conductor.msg4");
                    this.fontRenderer.drawString(power4, this.xSize / 2 - this.fontRenderer.getStringWidth(power4) / 2, 46, 4210752);
				break;
			case 5:
				String power5 = wait + FontHelper.translate("conductor.msg5");
                    this.fontRenderer.drawString(power5, this.xSize / 2 - this.fontRenderer.getStringWidth(power5) / 2, 46, 4210752);
				break;
			case 6:
				String power6 = wait + FontHelper.translate("conductor.msg6");
                    this.fontRenderer.drawString(power6, this.xSize / 2 - this.fontRenderer.getStringWidth(power6) / 2, 46, 4210752);
				break;
			case 7:
				String power7 = wait + FontHelper.translate("conductor.msg7");
                    this.fontRenderer.drawString(power7, this.xSize / 2 - this.fontRenderer.getStringWidth(power7) / 2, 46, 4210752);
				break;
			case 8:
				String power8 = wait + FontHelper.translate("conductor.msg8");
                    this.fontRenderer.drawString(power8, this.xSize / 2 - this.fontRenderer.getStringWidth(power8) / 2, 46, 4210752);
				break;
			}
		} else {
            String power = wait + (this.entity.lightningSpeed.getObject() / 20 - this.entity.lightingTicks.getObject() / 20) + ' ' + FontHelper.translate("lightning.seconds");
            this.fontRenderer.drawString(power, this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2, 46, 4210752);
		}

		FontHelper.textOffsetCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), 90, 66, 2);
        if (x > guiLeft + 2 && x < guiLeft + 16 && y > guiTop + 62 && y < guiTop + 76) {
            ArrayList<String> list = new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.##");
			list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + "Last Strike");
			if (entity.rfPerStrike.getObject() == 0 && entity.rfPerTick.getObject() == 0) {
				list.add("Awaiting first strike");
            } else {
				list.add("Total: " + FontHelper.formatStorage(entity.rfPerStrike.getObject()) + "/strike");
                list.add("Approx: " + FontHelper.formatOutput(entity.rfPerTick.getObject().intValue()));
			}
            drawSpecialToolTip(list, x, y, fontRenderer);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int x, int y) {
		super.drawGuiContainerBackgroundLayer(var1, x, y);

        int c = this.entity.cookTime.getObject() * 18 / TileEntityConductorMast.furnaceSpeed;
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 26, 176, 0, c, 9);
		int changedEnergy = this.entity.storage.getEnergyStored() / 50000;
		int newEnergy = changedEnergy * 145 / 1000;
		drawTexturedModalRect(this.guiLeft + 22, this.guiTop + 59, 0, 166, newEnergy, 20);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
