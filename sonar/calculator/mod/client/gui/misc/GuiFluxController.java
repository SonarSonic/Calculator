package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons.SonarButton;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons.CircuitButton;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.containers.ContainerFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.network.packets.PacketMachineButton;
import sonar.core.utils.helpers.FontHelper;

public class GuiFluxController extends GuiButtons{
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxController.png");

	public TileEntityFluxController entity;

	private GuiTextField id;
	private GuiButton recieveMode, sendMode;
	
	public GuiFluxController(InventoryPlayer inventoryPlayer, TileEntityFluxController entity) {
		super(new ContainerFluxController(inventoryPlayer, entity), entity.xCoord, entity.yCoord, entity.zCoord);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 199;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		id.drawTextBox();
		FontHelper.text(StatCollector.translateToLocal(entity.blockType.getLocalizedName()), 6, 8, 0);
		FontHelper.text("ID:", 89, 8, 0);
		FontHelper.text("R: " + getRecieveString(entity.recieveMode, false),10, 27, 0);
		FontHelper.text("S: " + getSendString(entity.sendMode, false), 10, 37, 0);
		FontHelper.text("D: " + ((entity.allowDimensions==1)? "True" : "False"), 10, 47, 0);
		FontHelper.text("P: " + ((entity.playerProtect==1)? "True" : "False"), 10, 57, 0);		
		FontHelper.text("Master: " + entity.playerName, 10, 70, 0);
		FontHelper.text("Shared users: ", 10, 82, 0);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		id = new GuiTextField(this.fontRendererObj, 105, 6, 50, 12);
		id.setMaxStringLength(7);
		id.setText(String.valueOf(entity.freq));
		this.buttonList.add(new ConfirmButton(guiLeft + 157, guiTop + 6));
		this.buttonList.add(new ModeButton(3, this.guiLeft + 124, this.guiTop + 24, 20, 20, "R"));
		this.buttonList.add(new ModeButton(4, this.guiLeft + 148, this.guiTop + 24, 20, 20, "S"));
		this.buttonList.add(new ModeButton(5, this.guiLeft + 124, this.guiTop + 48, 20, 20, "D"));
		this.buttonList.add(new ModeButton(6, this.guiLeft + 148, this.guiTop + 48, 20, 20, "P"));

	}
	public String getRecieveString(int send, boolean full){
		switch(send){
		case 1: return full ? "Even Distribution - Spreads total energy evenly between receivers" : "Even Distribution";
		case 2: return full ? "Surge - Allows fast transfer speeds with good distribution" :"Surge";
		case 3: return full ? "Hyper-Surge - Allows for very fast transfer with poor distribution" : "Hyper-Surge";
		case 4: return full ? "God-Mode - Allows for very fast transfer with poor distribution" : "God-Mode";
		}
		return full ? "Default - Adds all energy in a sequencial order" : "Default";
	}
	
	public String getSendString(int send, boolean full){
		switch(send){
		case 1: return full ? "Largest First - Starts with the sender with the highest rf/t" : "Largest First";
		case 2: return full ? "Smallest First - Starts with the sender with the lowest rf/t" : "Smallest First";
		}
		return full ? "Default - Adds all energy in a sequencial order" : "Default";
	}	
	
	@SideOnly(Side.CLIENT)
	public class ModeButton extends CalculatorButtons.SonarButton {
		
		public ModeButton(int id, int x, int y, int textureX, int textureY, String display) {
			super(id, x, y, textureX, textureY, display);
		}
		
		public void func_146111_b(int x, int y) {
			if(this.id==3){
			drawCreativeTabHoveringText("Receive Mode", x-20, y-10);
			}
			if(this.id==4){
			drawCreativeTabHoveringText("Send Mode", x-20, y-10);
			}
			if(this.id==5){
			drawCreativeTabHoveringText("Dimensional Transfer", x-20, y-10);
			}
			if(this.id==6){
			drawCreativeTabHoveringText("Protection", x-20, y-10);
			}
		}
		
		@Override
		public void onClicked() {
			Calculator.network.sendToServer(new PacketMachineButton(this.id, entity.xCoord, entity.yCoord, entity.zCoord));
			buttonList.clear();
			initGui();
			updateScreen();
		}
	}
	@SideOnly(Side.CLIENT)
	public class ConfirmButton extends CalculatorButtons.ImageButton {

		public ConfirmButton(int x, int y) {
			super(1, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 68, 0, 16, 16);
		}

		public void func_146111_b(int x, int y) {
		//	drawCreativeTabHoveringText(StatCollector.translateToLocal("buttons.circuits"), x, y);
		}

		@Override
		public void onClicked() {
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		id.mouseClicked(i - guiLeft, j - guiTop, k);
	}

	protected void actionPerformed(GuiButton button) {
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof CalculatorButtons.SonarButton) {
				if(button instanceof ModeButton){
					ModeButton modeButton = (ModeButton) button;	
					if(modeButton.id==3){
						if (entity.recieveMode < 4)
							entity.recieveMode++;
						else
							entity.recieveMode = 0;
					} else if(modeButton.id==4){
						if (entity.sendMode < 2)
							entity.sendMode++;
						else
							entity.sendMode = 0;
					} else if(modeButton.id==5){
						if(entity.allowDimensions==0){
							entity.allowDimensions=1;
						}else{
							entity.allowDimensions=0;
						}
					} else if(modeButton.id==6){
						if(entity.playerProtect==0){
							entity.playerProtect=1;
						}else{
							entity.playerProtect=0;
						}
					}
				}
				SonarButton sButton = (SonarButton) button;
				sButton.onClicked();
			}
		}
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof ConfirmButton) {
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(0, entity.xCoord, entity.yCoord, entity.zCoord,0));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(Integer.valueOf(text), entity.xCoord, entity.yCoord, entity.zCoord,0));
				}
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {

		if (id.isFocused()) {
			if (c == 13 || c == 27) {
				id.setFocused(false);
			} else {
				typeKey(id,c, i);
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					entity.freq = 0;
				} else {
					entity.freq = Integer.valueOf(text);
				}
			}
		} else {
			super.keyTyped(c, i);
		}

	}
	public boolean typeKey(GuiTextField box, char c, int i) {
		switch (c) {
		case '\001':
			return box.textboxKeyTyped(c, i);
		case '\003':
			return box.textboxKeyTyped(c, i);
		case '\026':
			return false;
		case '\030':
			return box.textboxKeyTyped(c, i);
		}
		switch (i) {
		case 14:
			return box.textboxKeyTyped(c, i);
		case 199:
			return box.textboxKeyTyped(c, i);
		case 203:
			return box.textboxKeyTyped(c, i);
		case 205:
			return box.textboxKeyTyped(c, i);
		case 207:
			return box.textboxKeyTyped(c, i);
		case 211:
			return box.textboxKeyTyped(c, i);
		}
		if (Character.isDigit(c)) {
			return box.textboxKeyTyped(c, i);
		}
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	}

}
