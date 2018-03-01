package sonar.calculator.mod.client.gui.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerStorageModule;
import sonar.core.client.gui.GuiSonar;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiStorageModule extends GuiSonar {
	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");

	public GuiStorageModule(EntityPlayer player, InventoryItem inventory) { 
		super(new ContainerStorageModule(player, inventory));
        this.ySize = 222;
    }
	
    @Override
	protected void drawGuiContainerForegroundLayer(int x,int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		  FontHelper.textCentre(FontHelper.translate("item.StorageModule.name"), xSize, 6, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return texture;
	}
}