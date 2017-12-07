package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerCraftingCalculator;
import sonar.core.client.gui.GuiSonar;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiCraftingCalculator extends GuiSonar {

	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/craftingcalculator.png");

	public GuiCraftingCalculator(EntityPlayer player, InventoryItem craftingInv) {
		super(new ContainerCraftingCalculator(player, craftingInv));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("item.CraftingCalculator.name"), xSize, 5, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
