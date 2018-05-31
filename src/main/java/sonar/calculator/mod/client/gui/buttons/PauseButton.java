package sonar.calculator.mod.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.core.api.machines.IPausable;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.client.gui.SonarButtons;
import sonar.core.helpers.FontHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class PauseButton extends SonarButtons.ImageButton {

	public Supplier<Boolean> isPaused;
	public int id;
	public IPausable machine;
	public GuiSonarTile gui;

	public PauseButton(GuiSonarTile gui, IPausable machine, int id, int x, int y, Supplier<Boolean> isPaused) {
		super(id, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 34, 0, 16, 16);
		this.gui = gui;
		this.isPaused = isPaused;
		this.id = id;
		this.machine = machine;
	}

	@Override
	public void drawButton(@Nonnull Minecraft mc, int x, int y, float partialTicks) {
		textureX = isPaused.get() ? 51 : 34;
		super.drawButton(mc, x, y, partialTicks);
	}

	@Override
	public void drawButtonForegroundLayer(int x, int y) {
		ArrayList<String> list = new ArrayList<>();
		list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + (isPaused.get() ? FontHelper.translate("buttons.resume") : FontHelper.translate("buttons.pause")));
		if (machine instanceof IProcessMachine) {
			list.add("Current: " + (int) ((double) ((IProcessMachine) machine).getCurrentProcessTime() / ((IProcessMachine) machine).getProcessTime() * 100) + " %");
		}
		gui.drawSonarCreativeTabHoveringText(list, x, y);
	}

}