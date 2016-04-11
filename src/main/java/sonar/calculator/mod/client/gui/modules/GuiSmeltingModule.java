package sonar.calculator.mod.client.gui.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerSmeltingModule;
import sonar.core.helpers.FontHelper;

public class GuiSmeltingModule extends GuiContainer {
	public static ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/reinforcedFurnace.png");
	private WIPSmeltingModule module;

	public GuiSmeltingModule(EntityPlayer player, InventoryPlayer inv, WIPSmeltingModule.SmeltingInventory inventory, ItemStack item) {
		super(new ContainerSmeltingModule(player, inv, inventory, item));
		this.module = (WIPSmeltingModule) item.getItem();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		FontHelper.textCentre(FontHelper.translate("item.SmeltingModule.name"), this.xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(this.module.syncEnergy), this.xSize, 64, 2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = this.module.syncEnergy * 78 / 50000;
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);

		int l = this.module.syncCook * 23 / module.speed;
		drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 24, 176, 10, l, 16);

	}
}