package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.core.client.gui.GuiSonar;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiCalculator extends GuiSonar {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/calculator.png");

	public GuiCalculator(EntityPlayer player, InventoryItem calculatorInventory) {
		super(new ContainerCalculator(player, calculatorInventory));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("item.Calculator.name"), xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
