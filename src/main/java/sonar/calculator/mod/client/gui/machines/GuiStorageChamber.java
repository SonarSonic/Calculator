package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiStorageChamber extends GuiSonarTile {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/storage_chamber.png");

	public TileEntityStorageChamber entity;

	public GuiStorageChamber(EntityPlayer player, TileEntityStorageChamber entity) {
		super(new ContainerStorageChamber(player, entity), entity);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 183;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		String string = FontHelper.translate("circuit.type") + ": ";
		switch (entity.circuitType) {
		case Analysed:
			string = string + FontHelper.translate("circuit.analysed");
			break;
		case Stable:
			string = string + FontHelper.translate("circuit.stable");
			break;
		case Damaged:
			string = string + FontHelper.translate("circuit.damaged");
			break;
		case Dirty:
			string = string + FontHelper.translate("circuit.dirty");
			break;
		case None:
			string = string + FontHelper.translate("locator.none");
			break;
		}
		FontHelper.textCentre(string, xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
