package sonar.calculator.mod.client.gui.calculators;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerPortableDynamic;
import sonar.core.common.item.InventoryItem;

@SideOnly(Side.CLIENT)
public class GuiPortableDynamic extends GuiContainer {
	private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/dynamiccalculator.png");

	public GuiPortableDynamic(EntityPlayer player, InventoryPlayer inv, InventoryItem calc, Map<Integer, Integer> map) {
		super(new ContainerPortableDynamic(player, inv, calc, map));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
