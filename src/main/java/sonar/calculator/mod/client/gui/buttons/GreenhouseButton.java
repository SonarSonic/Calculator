package sonar.calculator.mod.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class GreenhouseButton extends GuiButton {
	public String name;

	public GreenhouseButton(int id, int x, int y, int texX, int texY, String name) {
		super(id, x, y, texX, texY, name);
		this.name = name;
	}

	@Override
	public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {}
}