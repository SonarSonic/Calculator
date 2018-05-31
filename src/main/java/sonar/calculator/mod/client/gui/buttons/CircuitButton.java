package sonar.calculator.mod.client.gui.buttons;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.core.SonarCore;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.client.gui.SonarButtons;
import sonar.core.helpers.FontHelper;
import sonar.core.network.PacketByteBuf;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.upgrades.UpgradeInventory;

import java.util.ArrayList;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class CircuitButton extends SonarButtons.ImageButton {
	public int id;
	public UpgradeInventory upgrades;
	public GuiSonarTile gui;

	public CircuitButton(GuiSonarTile gui, UpgradeInventory upgrades, int id, int x, int y) {
		super(id, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 0, 0, 16, 16);
		this.gui = gui;
		this.upgrades = upgrades;
		this.id = id;
	}

	@Override
	public void drawButtonForegroundLayer(int x, int y) {
		ArrayList<String> list = new ArrayList<>();
		list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + FontHelper.translate("buttons.circuits"));
		for (Map.Entry<String, Integer> entry : upgrades.getInstalledUpgrades().entrySet()) {
			int max = upgrades.maxUpgrades.get(entry.getKey());
			list.add(entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().toLowerCase().substring(1) + ": " + entry.getValue() + '/' + max);
		}
		gui.drawSonarCreativeTabHoveringText(list, x, y);
	}

	@Override
	public void onClicked() {
		SonarCore.network.sendToServer(new PacketByteBuf((IByteBufTile) gui.entity, gui.entity.getCoords().getBlockPos(), id));
	}
}