package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.inventory.GuiSonar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDynamicCalculator extends GuiSonar {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/dynamiccalculator.png");

	public GuiDynamicCalculator(EntityPlayer player, TileEntityCalculator.Dynamic dynamic) {
		super(new ContainerDynamicCalculator(player, dynamic), dynamic);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}
