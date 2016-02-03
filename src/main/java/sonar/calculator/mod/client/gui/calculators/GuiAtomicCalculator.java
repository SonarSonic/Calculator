package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerAtomicCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.inventory.GuiSonar;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAtomicCalculator extends GuiSonar {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/atomiccalculator.png");

	public GuiAtomicCalculator(EntityPlayer player, TileEntityCalculator.Atomic atomic) {
		super(new ContainerAtomicCalculator(player, atomic), atomic);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		FontHelper.textCentre(FontHelper.translate("tile.atomiccalculatorBlock.name"), xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
