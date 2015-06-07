package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.utils.SlotBigStorage;
import sonar.core.utils.helpers.FontHelper;

public class GuiStorageChamber extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation(
			"Calculator:textures/gui/circuit_storage.png");

	public TileEntityStorageChamber entity;

	public GuiStorageChamber(InventoryPlayer inventoryPlayer,
			TileEntityStorageChamber entity) {
		super(new ContainerStorageChamber(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 202;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
	}

	public void renderItemStack(int stack, int x, int y) {
		if (entity.stored[stack] != 0) {
			itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc
					.getTextureManager(), new ItemStack(entity.getSavedStack()
					.getItem(), entity.stored[stack], stack), this.guiLeft + x, this.guiTop + y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);
		if (entity.getSavedStack() != null) {
			renderItemStack(0, 26 + 13, 6);
			renderItemStack(1, 62 + 13, 6);
			renderItemStack(2, 98 + 13, 6);
			renderItemStack(3, 134 + 13, 6);
			renderItemStack(4, 8 + 13, 32);
			renderItemStack(5, 44 + 13, 32);
			renderItemStack(6, 80 + 13, 32);
			renderItemStack(7, 116 + 13, 32);
			renderItemStack(8, 152 + 13, 32);
			renderItemStack(9, 8 + 13, 58);
			renderItemStack(10, 44 + 13, 58);
			renderItemStack(11, 80 + 13, 58);
			renderItemStack(12, 116 + 13, 58);
			renderItemStack(13, 152 + 13, 58);
		}
	}
}
