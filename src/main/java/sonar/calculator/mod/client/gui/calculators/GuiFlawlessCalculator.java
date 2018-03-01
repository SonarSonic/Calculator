package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerFlawlessCalculator;
import sonar.core.client.gui.GuiSonar;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiFlawlessCalculator extends GuiSonar {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/flawlesscalculator.png");

	public GuiFlawlessCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		super(new ContainerFlawlessCalculator(player, inventoryItem));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("item.FlawlessCalculator.name"), xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
