package sonar.calculator.mod.client.gui.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerStorageModule;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.item.modules.StorageModule;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStorageModule extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");

	public GuiStorageModule(EntityPlayer player, InventoryPlayer inv, StorageModule.StorageInventory inventory) { 
		super(new ContainerStorageModule(player, inv, inventory));
        this.ySize = 222;
    }
	
	protected void drawGuiContainerForegroundLayer(int x,int y) {
		  FontHelper.textCentre(StatCollector.translateToLocal("item.StorageModule.name"), xSize, 6, 0);
	}

	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}