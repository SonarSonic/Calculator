package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerDynamicModule;
import sonar.core.client.gui.GuiSonar;
import sonar.core.common.item.InventoryItem;

@SideOnly(Side.CLIENT)
public class GuiDynamicModule extends GuiSonar {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/dynamiccalculator.png");

	public GuiDynamicModule(EntityPlayer player, InventoryItem calc) {
		super(new ContainerDynamicModule(player, calc));
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}