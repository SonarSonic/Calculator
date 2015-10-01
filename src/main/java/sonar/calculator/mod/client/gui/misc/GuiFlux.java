package sonar.calculator.mod.client.gui.misc;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.GuiSonar;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.utils.helpers.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class GuiFlux extends GuiSonar {

	public static final ResourceLocation networkTex = new ResourceLocation("Calculator:textures/gui/networkSelect.png");

	private float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	private int changed;
	public int state;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;
	public EntityPlayer player;
	public TileEntity tile;
	private GuiTextField id;
	private String currentName;

	public static int changeName = 0;

	public GuiFlux(Container container, TileEntity tile, EntityPlayer player) {
		super(container, tile);
		this.player = player;
		this.tile = tile;

	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.mc.thePlayer.openContainer = this.inventorySlots;
		if (!network()) {
			this.xSize = xSize();
			this.ySize = ySize();
		} else {
			this.xSize = 176;
			this.ySize = 166;
		}
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		scrollerLeft = this.guiLeft + 164;
		scrollerStart = this.guiTop + 8;
		scrollerEnd = scrollerStart + 49;
		scrollerWidth = 10;

		if (!network()) {
			this.buttonList.add(new GuiButton(0, guiLeft + 114, guiTop + 3, 55, 20, "Network"));
		} else {
			id = new GuiTextField(this.fontRendererObj, 8, 62, 92, 12);
			id.setMaxStringLength(14);
			id.setText(this.getNetworkName());
			this.currentName = this.getNetworkName();
			this.buttonList.add(new FluxButton(2, guiLeft + 114, guiTop + 62));
			this.buttonList.add(new FluxButton(3, guiLeft + 114 + 14, guiTop + 62));
			this.buttonList.add(new FluxButton(4, guiLeft + 114 + 28, guiTop + 62));
			this.buttonList.add(new NetworkButton(10, guiLeft + 7, guiTop + 8));
			this.buttonList.add(new NetworkButton(11, guiLeft + 7, guiTop + 8 + 12));
			this.buttonList.add(new NetworkButton(12, guiLeft + 7, guiTop + 8 + 24));
			this.buttonList.add(new NetworkButton(13, guiLeft + 7, guiTop + 8 + 36));
		}
	}

	public int getNetworkPosition() {
		if (currentName == null) {
			return -1;
		}
		List<FluxNetwork> networks = this.getNetworks();
		if (networks == null) {
			return -1;
		}
		int start = (int) (networks.size() * this.currentScroll);
		int finish = Math.min(start + 4, networks.size());
		for (int i = start; i < finish; i++) {
			if (networks.get(i) != null) {
				if (currentName.equals(networks.get(i).networkName)&&networks.get(i).networkID==getNetworkID()) {
					return i - start;
				}
			}
		}

		return -1;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		if (!network()) {
			return;
		}
		id.drawTextBox();
		List<FluxNetwork> networks = this.getNetworks();
		if (networks != null) {
			int start = (int) (this.getNetworks().size() * this.currentScroll);
			int finish = Math.min(start + 4, networks.size());
			for (int i = start; i < finish; i++) {
				if (networks.get(i) != null) {
					if (networks.get(i).networkName.equals(currentName)&&networks.get(i).networkID==getNetworkID()) {
						FontHelper.text(networks.get(i).networkName, 10, 10 + (12 * i) - (12 * start), Color.GREEN.getRGB());
						FontHelper.text(getNetworkType(networks.get(i).type), 105, 10 + (12 * i) - (12 * start), Color.GREEN.getRGB());

					} else {
						FontHelper.text(networks.get(i).networkName, 10, 10 + (12 * i) - (12 * start), 2);
						FontHelper.text(getNetworkType(networks.get(i).type), 105, 10 + (12 * i) - (12 * start), 2);
					}
				}
			}
		}

	}

	public static String getNetworkType(int networkType) {
		switch (networkType) {
		case 0:
			return FontHelper.translate("network.restricted");
		case 1:
			return FontHelper.translate("network.private");
		case 2:
			return FontHelper.translate("network.public");
		}
		return "Unknown";
	}

	public void handleMouseInput() {
		super.handleMouseInput();
		if (!network()) {
			return;
		}
		float lastScroll = currentScroll;
		int i = Mouse.getEventDWheel();

		if (i != 0 && this.needsScrollBars()) {
			int j = this.getNetworks().size() + 1;

			if (i > 0) {
				i = 1;
			}

			if (i < 0) {
				i = -1;
			}

			this.currentScroll = (float) ((double) this.currentScroll - (double) i / (double) j);

			if (this.currentScroll < 0.0F) {
				this.currentScroll = 0.0F;
			}

			if (this.currentScroll > 1.0F) {
				this.currentScroll = 1.0F;
			}
		}

	}

	public void drawScreen(int x, int y, float var) {
		super.drawScreen(x, y, var);
		if (!network()) {
			return;
		}
		float lastScroll = currentScroll;
		boolean flag = Mouse.isButtonDown(0);

		if (!this.wasClicking && flag && x >= scrollerLeft && y >= scrollerStart && x < scrollerLeft + scrollerWidth && y < scrollerEnd) {
			this.isScrolling = this.needsScrollBars();
		}

		if (!flag) {
			this.isScrolling = false;
		}

		this.wasClicking = flag;

		if (this.isScrolling) {
			this.currentScroll = ((float) (y - scrollerStart) - 7.5F) / ((float) (scrollerEnd - scrollerStart) - 15.0F);

			if (this.currentScroll < 0.0F) {
				this.currentScroll = 0.0F;
			}

			if (this.currentScroll > 1.0F) {
				this.currentScroll = 1.0F;
			}

		}

	}

	protected void actionPerformed(GuiButton button) {
		if (changed == 1) {
			changed = 0;
			return;
		}
		if (button == null) {
			return;
		}
		if (!network() && button.id == 0) {
			this.currentScroll = 0;
			state = 1;
			this.switchContainer(this.mc.thePlayer.openContainer);
			this.inventorySlots = this.mc.thePlayer.openContainer;
			if (!(tile instanceof TileEntityFluxController)) {
				this.changed = 1;
			}
			this.reset();
			return;
		}
		if (network()) {
			switch (button.id) {
			case 2:
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					changeNetworkName("NETWORK", 0);

				} else {
					changeNetworkName(text, 0);
				}
				break;
			case 3:
				final String rename = id.getText();
				if (!(rename.isEmpty() || rename == "" || rename == null)) {
					changeNetworkName(rename, 4);
				}
				break;
			case 4:
				state = 0;
				this.switchContainer(this.mc.thePlayer.openContainer);
				this.inventorySlots = this.mc.thePlayer.openContainer;
				break;
			}

			if (button.id >= 10) {
				if (this.getNetworks() != null) {
					int start = (int) (this.getNetworks().size() * this.currentScroll);
					int network = start + button.id - 10;
					if (network < this.getNetworks().size()) {
						changeNetworkName(this.getNetworks().get(network).networkName, 0);
					}
				}
			}
		}
		Calculator.network.sendToServer(new PacketFluxPoint(this.mc.thePlayer.getGameProfile().getName(), tile.xCoord, tile.yCoord, tile.zCoord, 3));
		this.reset();
	}

	public void changeNetworkName(String string, int type) {
		Calculator.network.sendToServer(new PacketFluxPoint(string, tile.xCoord, tile.yCoord, tile.zCoord, type));
		this.setNetworkName(string);
		this.currentName = string;
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (!network()) {
			return;
		} else {
			id.mouseClicked(i - guiLeft, j - guiTop, k);
			if (id.isFocused() && id.getText().equals("NETWORK"))
				id.setText("");
		}

	}

	/* typing method */
	@Override
	protected void keyTyped(char c, int i) {
		if (network() && id.isFocused()) {
			if (c == 13 || c == 27) {
				id.setFocused(false);
			} else {
				id.textboxKeyTyped(c, i);
				final String text = id.getText();

				if (text.isEmpty() || text == "" || text == null)
					this.setNetworkName("NETWORK");
				else
					this.setNetworkName(text);

			}
		} else {
			super.keyTyped(c, i);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (!network()) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(this.getBackground());
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(networkTex);
		}

		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if (network()) {
			int pos = this.getNetworkPosition();
			for (int i = 0; i < 4; i++) {
				drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 8 + (12 * i), 0, i == pos ? 178 : 166, 154, 12);
			}
		}
		if (network()) {
			this.drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 176, 0, 10, 15);
		}
	}

	public boolean network() {
		return state == 1;
	}

	private boolean needsScrollBars() {
		if (this.getNetworks() == null)
			return false;
		if (this.getNetworks().size() <= 4)
			return false;

		return true;

	}

	@SideOnly(Side.CLIENT)
	public class FluxButton extends CalculatorButtons.ImageButton {
		int id;

		public FluxButton(int id, int x, int y) {
			super(id, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), id == 0 ? 104 : id == 2 ? 68 : id == 3 ? 80 : 92, 0, 11, 11);
			this.id = id;
		}

		public void func_146111_b(int x, int y) {
			switch (id) {
			case 2:
				drawCreativeTabHoveringText(FontHelper.translate("network.add"), x, y);
				break;
			case 3:
				drawCreativeTabHoveringText(FontHelper.translate("network.rename"), x, y);
				break;
			case 4:
				drawCreativeTabHoveringText(FontHelper.translate("network.back"), x, y);
				break;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public class NetworkButton extends CalculatorButtons.ImageButton {

		public NetworkButton(int id, int x, int y) {
			super(id, x, y, networkTex, 0, 190, 154, 11);
		}
	}

	public abstract void switchContainer(Container container);

	public abstract List<FluxNetwork> getNetworks();

	public abstract void setNetworkName(String string);

	public abstract String getNetworkName();
	
	public abstract int getNetworkID();

	public abstract int xSize();

	public abstract int ySize();
}
