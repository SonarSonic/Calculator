package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerAtomicCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiAtomicCalculator extends GuiSonarTile {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/atomiccalculator.png");

	public GuiAtomicCalculator(EntityPlayer player, TileEntityCalculator.Atomic atomic) {
		super(new ContainerAtomicCalculator(player, atomic), atomic);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		FontHelper.textCentre(FontHelper.translate("tile.AtomicCalculator.name"), xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
