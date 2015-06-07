package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons.SonarButton;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.utils.helpers.FontHelper;

public class GuiSmeltingBlock
  extends GuiButtons
{
  public static ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/restorationchamber.png");
  
  public TileEntityAbstractProcess entity;
  
  public void initGui()
  {
      super.initGui();
      this.buttonList.add(new CircuitButton(guiLeft+150-14, guiTop+23));
      this.buttonList.add(new PauseButton(guiLeft+8+14, guiTop+23, entity.isPaused()));
  }
  public void initGui(boolean pause)
  {
      super.initGui();
      this.buttonList.add(new CircuitButton(guiLeft+150-14, guiTop+23));
      this.buttonList.add(new PauseButton(guiLeft+8+14, guiTop+23, pause));
  } 
  protected void actionPerformed(GuiButton button) {
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof CalculatorButtons.SonarButton) {
				SonarButton sButton = (SonarButton) button;
				sButton.onClicked();
			}
		}
	}
  public static class RestorationChamber
  	extends GuiSmeltingBlock
  	{
	  public RestorationChamber(InventoryPlayer inventoryPlayer,TileEntityAbstractProcess entity) {
		super(inventoryPlayer, entity);
	  	}
  	}

  public static class ReassemblyChamber
  	extends GuiSmeltingBlock
  	{
	  public ReassemblyChamber(InventoryPlayer inventoryPlayer,TileEntityAbstractProcess entity) {
		super(inventoryPlayer, entity);
	  	}
  	}
  public static class ProcessingChamber
  	extends GuiSmeltingBlock
  	{
	  public ProcessingChamber(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
		super(inventoryPlayer, entity);
	  	} 
  	}
  public static class ReinforcedFurnace
	extends GuiSmeltingBlock
	{
	  public ReinforcedFurnace(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity) {
		super(inventoryPlayer, entity);
		this.bground=new ResourceLocation("Calculator:textures/gui/reinforcedFurnace.png");
	  	} 
	}


  public GuiSmeltingBlock(InventoryPlayer inventoryPlayer, TileEntityAbstractProcess entity)
  {
    super(new ContainerSmeltingBlock(inventoryPlayer, entity), entity.xCoord, entity.yCoord, entity.zCoord);
    

    this.entity = entity;
    

    this.xSize = 176;
    this.ySize = 166;
  }
  

  @Override
public void drawGuiContainerForegroundLayer(int x, int y)
  {
    String name = this.entity.hasCustomInventoryName() ? this.entity.getInventoryName() : I18n.format(this.entity.getInventoryName(), new Object[0]);
	FontHelper.textCentre(name, xSize, 6, 0);
	
	String power= null;
    switch(CalculatorConfig.energyStorageType){
	case 1: power = this.entity.storage.getEnergyStored() + " RF";	break;
	case 2: power = (this.entity.storage.getEnergyStored()/4) + " EU";	break;
	}
    FontHelper.textCentre(power, xSize, 64, 2);
	super.drawGuiContainerForegroundLayer(x, y);
  }
  

  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    

    Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    

    int k = this.entity.storage.getEnergyStored() * 78 / CalculatorConfig.cubeEnergy;
    int j = 78 - k;
    drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
    

    if(this.entity.currentSpeed!=0&&this.entity.cookTime!=0){
    int l = this.entity.cookTime * 23 / this.entity.currentSpeed;
    drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 24, 176, 10, l, 16);
    }
  }


}

