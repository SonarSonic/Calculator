package sonar.calculator.mod.client.gui.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerStorageModule;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiStorageModule extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");

	public GuiStorageModule(EntityPlayer player, InventoryItem inventory) { 
		super(new ContainerStorageModule(player, inventory));
        this.ySize = 222;
    }
	
    @Override
	protected void drawGuiContainerForegroundLayer(int x,int y) {
		  FontHelper.textCentre(FontHelper.translate("item.StorageModule.name"), xSize, 6, 0);
	}

    @Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}