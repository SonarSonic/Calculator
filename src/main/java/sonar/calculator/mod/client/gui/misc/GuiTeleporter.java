package sonar.calculator.mod.client.gui.misc;

import java.awt.Color;
import java.util.List;

import net.java.games.input.Keyboard;
import net.java.games.input.Mouse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.api.machines.TeleportLink;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.core.SonarCore;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.ContainerEmpty;
import sonar.core.inventory.GuiSonar;
import sonar.core.network.PacketByteBufServer;
import sonar.core.network.PacketTextField;

public class GuiTeleporter extends GuiSonar {
	public static ResourceLocation network = new ResourceLocation("Calculator:textures/gui/teleporter.png");

	public TileEntityTeleporter entity;
	private GuiTextField name;
	private GuiTextField password;
	private GuiTextField destinationPassword;
	private float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	private int currentID;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;

	public GuiTeleporter(InventoryPlayer player, TileEntityTeleporter entity) {
		super(new ContainerEmpty(player, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		name.drawTextBox();
		password.drawTextBox();
		destinationPassword.drawTextBox();
		List<TeleportLink> links = entity.links;
		if (links != null) {
			int start = (int) (links.size() * this.currentScroll);
			int finish = Math.min(start + 4, links.size());
			for (int i = start; i < finish; i++) {
				if (links.get(i) != null) {
					if (links.get(i).networkID == entity.linkID) {
						FontHelper.text(links.get(i).networkName, 90, 7 + (12 * i) - (12 * start), Color.GREEN.getRGB());
					} else {
						FontHelper.text(links.get(i).networkName, 90, 7 + (12 * i) - (12 * start), 2);
					}
				}
			}
		}
		FontHelper.text("Teleporter", 16, 6, 0);
		GL11.glPushMatrix();
		GL11.glScaled(0.75, 0.75, 0.75);
		FontHelper.textOffsetCentre("X: " +entity.xCoord + " Y: " +entity.yCoord + " Z: " +entity.zCoord, 57, 26, 0);
		FontHelper.textOffsetCentre("Dimension: " + entity.dimension(), 57, 35, 0);
		GL11.glPopMatrix();
		FontHelper.text("Password: ", 18, 56, 0);
		FontHelper.text("Password: ", 97, 56, Color.GREEN.getRGB());
	}

	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		name = new GuiTextField(this.fontRendererObj, 8, 40, 70, 12);
		name.setMaxStringLength(10);
		name.setText(String.valueOf(entity.name));

		password = new GuiTextField(this.fontRendererObj, 8, 66, 70, 12);
		password.setMaxStringLength(10);
		password.setText(String.valueOf(entity.password));

		destinationPassword = new GuiTextField(this.fontRendererObj, 87, 66, 70, 12);
		destinationPassword.setMaxStringLength(10);
		destinationPassword.setText(String.valueOf(entity.linkPassword));

		this.currentID = entity.linkID;
		scrollerLeft = this.guiLeft + 163;
		scrollerStart = this.guiTop + 32 - 26;
		scrollerEnd = scrollerStart + 48;
		scrollerWidth = 10;

		this.buttonList.add(new NetworkButton(10, guiLeft + 86, guiTop + 19 + 12 - 26));
		this.buttonList.add(new NetworkButton(11, guiLeft + 86, guiTop + 19 + 24 - 26));
		this.buttonList.add(new NetworkButton(12, guiLeft + 86, guiTop + 19 + 36 - 26));
		this.buttonList.add(new NetworkButton(13, guiLeft + 86, guiTop + 19 + 48 - 26));
	}

	public String passwordConversion(String string) {
		char[] dots = new char[string.length()];
		for (int i = 0; i < string.length(); i++) {
			dots[i] = "*".charAt(0);
		}
		return dots.toString();
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		name.mouseClicked(i - guiLeft, j - guiTop, k);
		password.mouseClicked(i - guiLeft, j - guiTop, k);
		destinationPassword.mouseClicked(i - guiLeft, j - guiTop, k);

	}

	@Override
	public ResourceLocation getBackground() {
		return new ResourceLocation("Calculator:textures/gui/teleporter.png");
	}

	public void handleMouseInput() {
		super.handleMouseInput();
		float lastScroll = currentScroll;
		int i = Mouse.getEventDWheel();

		if (i != 0 && this.needsScrollBars()) {
			int j = entity.links.size() + 1;

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

	@Override
	protected void keyTyped(char c, int i) {
		if (name.isFocused()) {
			if (c == 13 || c == 27) {
				name.setFocused(false);
			} else {
				name.textboxKeyTyped(c, i);
				final String text = name.getText();
				if (text.isEmpty() || text == "" || text == null) {
					SonarCore.network.sendToServer(new PacketTextField("Unnamed", entity.xCoord, entity.yCoord, entity.zCoord, 1));
				} else {
					SonarCore.network.sendToServer(new PacketTextField(text, entity.xCoord, entity.yCoord, entity.zCoord, 1));
				}
				if (text.isEmpty() || text == "" || text == null)
					this.entity.name = "Unnamed";
				else
					this.entity.name = text;

			}

		} else if (password.isFocused()) {
			if (c == 13 || c == 27) {
				password.setFocused(false);
			} else {
				password.textboxKeyTyped(c, i);
				final String text = password.getText();
				if (text.isEmpty() || text == "" || text == null) {
					SonarCore.network.sendToServer(new PacketTextField("", entity.xCoord, entity.yCoord, entity.zCoord, 2));
				} else {
					SonarCore.network.sendToServer(new PacketTextField(text, entity.xCoord, entity.yCoord, entity.zCoord, 2));
				}
				if (text.isEmpty() || text == "" || text == null)
					this.entity.password = "";
				else
					this.entity.password = text;

			}

		} else if (destinationPassword.isFocused()) {
			if (c == 13 || c == 27) {
				destinationPassword.setFocused(false);
			} else {
				destinationPassword.textboxKeyTyped(c, i);
				final String text = destinationPassword.getText();
				if (text.isEmpty() || text == "" || text == null) {
					SonarCore.network.sendToServer(new PacketTextField("", entity.xCoord, entity.yCoord, entity.zCoord, 3));
				} else {
					SonarCore.network.sendToServer(new PacketTextField(text, entity.xCoord, entity.yCoord, entity.zCoord, 3));
				}
				if (text.isEmpty() || text == "" || text == null)
					this.entity.linkPassword = "";
				else
					this.entity.linkPassword = text;

			}

		} else {
			super.keyTyped(c, i);

		}

	}

	private boolean needsScrollBars() {
		if (entity.links == null)
			return false;
		if (entity.links.size() <= 4)
			return false;

		return true;

	}

	protected void actionPerformed(GuiButton button) {
		if (button.id >= 10) {
			if (entity.links != null) {
				int start = (int) (entity.links.size() * this.currentScroll);
				int network = start + button.id - 10;
				if (network < entity.links.size()) {
					entity.linkID=entity.links.get(network).networkID;
					SonarCore.network.sendToServer(new PacketByteBufServer(entity, entity.xCoord, entity.yCoord, entity.zCoord, 0));
				}
			}
		}
	}

	public int getLinkPosition() {
		if (entity.linkID == 0) {
			return -1;
		}
		List<TeleportLink> links = entity.links;
		if (links == null) {
			return -1;
		}
		int start = (int) (links.size() * this.currentScroll);
		int finish = Math.min(start + 4, links.size());
		for (int i = start; i < finish; i++) {
			if (links.get(i) != null) {
				if (entity.linkID == links.get(i).networkID) {
					return i - start;
				}
			}
		}

		return -1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int pos = this.getLinkPosition();
		for (int i = 0; i < 4; i++) {
			drawTexturedModalRect(this.guiLeft + 86, this.guiTop + 19 + 12 - 26 + (12 * i), 0, i == pos ? 178 : 166, 154, 12);
		}
		this.drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 176, 0, 10, 15);
		if (entity.passwordMatch)
			this.drawTexturedModalRect(this.guiLeft + 162, this.guiTop + 65, 176, 15, 9, 14);
	}

	@SideOnly(Side.CLIENT)
	public class NetworkButton extends SonarButtons.ImageButton {

		public NetworkButton(int id, int x, int y) {
			super(id, x, y, network, 0, 190, 72, 11);
		}
	}

}
