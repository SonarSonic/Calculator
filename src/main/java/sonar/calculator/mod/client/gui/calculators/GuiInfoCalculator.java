package sonar.calculator.mod.client.gui.calculators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.calculator.mod.guide.IItemInfo;
import sonar.calculator.mod.guide.IItemInfo.Category;
import sonar.calculator.mod.guide.InfoRegistry;
import sonar.core.helpers.FontHelper;

public class GuiInfoCalculator extends GuiContainer {

	public static final ResourceLocation list = new ResourceLocation("Calculator:textures/gui/info_calculator.png");
	public static final ResourceLocation info = new ResourceLocation("Calculator:textures/gui/info_calculator_item.png");

	public EntityPlayer player;
	private GuiTextField search;
	public float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;
	public Category currentCategory = Category.All;
	public ArrayList<IItemInfo> infoList = new ArrayList<IItemInfo>();
	public HashMap<Integer, ArrayList<String[]>> itemInfo = new HashMap<Integer, ArrayList<String[]>>();
	public int currentPos = -1;
	public int lastPos = -1;
	public int pageNum = 0;
	public GuiState currentState = GuiState.LIST;
	public String bulletPoint = "\u2022";
	public final int maxPerPage = 12;

	public enum GuiState {
		LIST, INFO;

		public ResourceLocation getBackground() {
			switch (this) {
			case LIST:
				return list;
			case INFO:
				return info;
			}
			return null;
		}
	}

	public GuiInfoCalculator(EntityPlayer player) {
		super(new ContainerInfoCalculator(player));
		this.player = player;
		resetInfoList();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(currentState.getBackground());
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if (currentState == GuiState.LIST) {
			drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 119, 166, 8, 15);
			int offsetTop = 29;
			if (getViewableSize() == 7) {
				offsetTop = offsetTop + 1;
			}
			for (int i = 0; i < getViewableSize(); i++) {
				drawSelectionBackground(offsetTop, i, -1);
			}

			int pos = 0;
			prepareItemRender();
			for (Category cat : IItemInfo.Category.values()) {
				if (cat != Category.All) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(currentState.getBackground());
					drawTexturedModalRect(guiLeft + 136, guiTop + 2 + (pos * 18), cat == currentCategory ? 136 + 18 : 136, 2, 18, 18);
					renderItem(cat.stack, guiLeft + 137, guiTop + 4 + (pos * 18));
					pos++;
				}
			}
		} else {
			IItemInfo info = infoList.get(this.currentPos);
			int pos = 0;
			prepareItemRender();
			for (ItemStack stack : info.getRelatedItems()) {
				if (stack != null) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(list);
					drawTexturedModalRect(guiLeft + 136, guiTop + 2 + (pos * 18), 136, 2, 18, 18);
					renderItem(stack, guiLeft + 137, guiTop + 4 + (pos * 18));
					pos++;
				}
			}
		}
	}

	public void prepareItemRender() {
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableGUIStandardItemLighting();
	}

	public void renderItem(ItemStack item, int x, int y) {
		if (item == null) {
			return;
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		this.itemRender.renderItemAndEffectIntoGUI(item, x, y);
		this.itemRender.renderItemOverlayIntoGUI(fontRendererObj, item, x, y, "");
		GlStateManager.popMatrix();
	}

	public void resetPosition() {
		lastPos = -1;
		currentPos = -1;
		pageNum = 0;
		currentScroll = 0.0F;
	}

	public void resetInfo() {
		buttonList.clear();
		if (currentPos != -1 && currentPos < infoList.size()) {
			changeItemInfo(infoList.get(currentPos));
		}
		initGui();
	}

	public String getSearch() {
		if (search == null || search.getText() == null) {
			return "";
		} else {
			return search.getText();
		}
	}

	public void resetInfoList() {
		ArrayList<IItemInfo> newInfo = InfoRegistry.getInfo(currentCategory);
		String search = getSearch();
		if (!search.isEmpty() && search != null) {
			newInfo = new ArrayList();
			for (IItemInfo item : InfoRegistry.getInfo(currentCategory)) {
				if (item != null) {
					if (item.getItem().getDisplayName().toLowerCase().contains(search.toLowerCase())) {
						newInfo.add(item);
					}
				}
			}
		}
		/* Collections.sort(newInfo, new Comparator<IItemInfo>() { public int compare(IItemInfo str1, IItemInfo str2) { int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getItem().getDisplayName(), str2.getItem().getDisplayName()); if (res == 0) { res = str1.getItem().getDisplayName().compareTo(str2.getItem().getDisplayName()); } return res; } }); */
		infoList = newInfo;
		this.currentScroll = 0.0F;
	}

	public void changeItemInfo(IItemInfo info) {
		HashMap<Integer, ArrayList<String[]>> itemInfo = new HashMap<Integer, ArrayList<String[]>>();
		String[] strings = FontHelper.translate(info.getItemInfo()).split("-");
		int pageNum = 0;
		int pageSize = 0;
		for (int i = 0; i < strings.length; i++) {
			String name = bulletPoint + " " + strings[i];
			List<String> lineInfo = fontRendererObj.listFormattedStringToWidth(name, ((int) ((xSize) * (1 / 0.8))) - 6);
			if (pageSize + lineInfo.size() < maxPerPage) {
				pageSize += lineInfo.size();
			} else {
				pageSize = lineInfo.size();
				pageNum++;
			}
			itemInfo.putIfAbsent(pageNum, new ArrayList());
			itemInfo.get(pageNum).add((String[]) lineInfo.toArray());
		}
		this.itemInfo = itemInfo;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		this.mc.thePlayer.openContainer = this.inventorySlots;
		this.xSize = 136;
		this.ySize = 166;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		if (currentState == GuiState.LIST) {
			scrollerLeft = this.guiLeft + 136 - 12;
			scrollerStart = this.guiTop + 30;
			scrollerEnd = scrollerStart + 128;
			scrollerWidth = 10;
			int offsetTop = 29;
			if (getViewableSize() == 7) {
				offsetTop = offsetTop + 2;
			}
			if (search == null) {
				search = new GuiTextField(0, this.fontRendererObj, 5, 16, 125, 12);
				search.setMaxStringLength(16);
			}

			int pos = 0;
			for (Category cat : IItemInfo.Category.values()) {
				if (cat != Category.All) {
					buttonList.add(new GuiButton(pos + 1, guiLeft + 136, guiTop + 2 + (pos * 18), 18, 18, "" + pos) {
						public void drawButton(Minecraft mc, int mouseX, int mouseY) {
							if (this.visible) {
								this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
								int i = this.getHoverState(this.hovered);
								if (this.isMouseOver()) {
									drawCreativeTabHoveringText("" + Category.values()[this.id], mouseX, mouseY);
								}
							}
						}

						public void drawButtonForegroundLayer(int x, int y) {
							drawCreativeTabHoveringText("" + Category.values()[this.id], x, y);
						}
					});
					pos++;
				}
			}

			// search.setText();
		} else {
			IItemInfo info = infoList.get(this.currentPos);
			if (info != null) {
				int pos = 0;
				while (pos != 10) {
					buttonList.add(new GuiButton(10 + pos, guiLeft + 136, guiTop + 2 + (pos * 18), 18, 18, "") {
						public void drawButton(Minecraft mc, int mouseX, int mouseY) {
							if (this.visible) {
								this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
								int i = this.getHoverState(this.hovered);
								if (this.isMouseOver()) {
									IItemInfo info = infoList.get(currentPos);
									if (info != null && info.getRelatedItems() != null) {
										if (this.id - 10 < info.getRelatedItems().length) {
											drawCreativeTabHoveringText("" + info.getRelatedItems()[id - 10].getDisplayName(), mouseX, mouseY);
										}
									}
								}
								RenderHelper.enableGUIStandardItemLighting();
							}
						}
					});
					pos++;

				}
			}
			buttonList.add(new GuiButton(0, guiLeft + 6, guiTop + 140, 20, 20, "<<"));
			buttonList.add(new GuiButton(1, guiLeft + 110, guiTop + 140, 20, 20, ">>"));
			buttonList.add(new GuiButton(2, guiLeft + 26, guiTop + 140, 20, 20, "<"));
			buttonList.add(new GuiButton(3, guiLeft + 90, guiTop + 140, 20, 20, ">"));
		}
	}

	public void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button == null) {
			return;
		}
		if (currentState == GuiState.LIST) {
			this.currentCategory = Category.values()[button.id];
			this.resetInfoList();
		} else if (currentState == GuiState.INFO) {
			if (button.id >= 10) {
				IItemInfo info = infoList.get(this.currentPos);
				if (info == null || !((button.id - 10) < info.getRelatedItems().length))
					return;
				ItemStack stack = info.getRelatedItems()[button.id - 10];
				if (stack == null)
					return;

				int pos = 0;
				boolean found = false;
				for (IItemInfo itemInfo : InfoRegistry.getInfo(Category.All)) {
					if (!found && ItemStack.areItemsEqual(itemInfo.getItem(), stack)) {
						this.currentPos = pos;
						pageNum = 0;
						currentCategory = Category.All;
						resetInfoList();
						changeItemInfo(itemInfo);
						found = true;
					}
					if (itemInfo == info) {
						lastPos = pos;
					}
					pos++;
				}
			} else {
				buttonPressed(button.id);
			}
		}
	}

	public void buttonPressed(int id) {
		switch (id) {
		case 0:
			if (currentPos != 0) {
				currentPos -= 1;
			} else {
				currentPos = infoList.size() - 1;
			}
			lastPos = -1;
			pageNum = 0;
			this.changeItemInfo(infoList.get(currentPos));
			break;
		case 1:
			if (currentPos + 1 < infoList.size()) {
				currentPos += 1;
			} else {
				this.currentPos = 0;
			}
			lastPos = -1;
			pageNum = 0;
			this.changeItemInfo(infoList.get(currentPos));
			break;
		case 2:
			if (pageNum != 0) {
				pageNum--;
			} else {
				buttonPressed(0);
			}
			break;
		case 3:
			if (pageNum + 1 < itemInfo.size()) {
				pageNum++;
			} else {
				buttonPressed(1);
			}
			break;
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		if (currentState == GuiState.LIST) {
			FontHelper.textCentre(FontHelper.translate("item.InfoCalculator.name"), xSize, 6, 0);
			prepareItemRender();
			if (infoList != null) {
				int start = (int) (infoList.size() * this.currentScroll);
				int finish = Math.min(start + this.getViewableSize(), infoList.size());
				for (int i = start; i < finish; i++) {
					IItemInfo info = infoList.get(i);
					ItemStack item = info.getItem();
					if (item != null) {
						this.renderItem(item, 5, 31 + ((i - start) * 18));
						GlStateManager.pushMatrix();
						GlStateManager.scale(0.8, 0.8, 0.8);
						String name = item.getDisplayName();
						if (fontRendererObj.getStringWidth(name) > 120) {
							name = fontRendererObj.trimStringToWidth(name, 120 - 6) + "...";
						}
						FontHelper.text(name, 33, (int) (45 + ((i - start) * (18.0)) * (1.0 / 0.8)), -1);
						GlStateManager.popMatrix();
					} else {
						FontHelper.text("Opps: error", 28, 35 + ((i - start) * 18), -1);
					}

				}
			}
			search.drawTextBox();
		} else {
			prepareItemRender();
			IItemInfo info = infoList.get(currentPos);
			ItemStack item = info.getItem();
			if (item != null) {
				this.renderItem(item, 5, 5);
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.8, 0.8, 0.8);
				FontHelper.text(EnumChatFormatting.UNDERLINE + item.getDisplayName(), 35, 12, 1);
				GlStateManager.popMatrix();
			}
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.scale(0.8, 0.8, 0.8);
			int size = 28;

			for (String[] ss : itemInfo.get(pageNum)) {
				int pos = 0;
				for (String s : ss) {
					int X = 6;
					FontHelper.text(s, X, size, 0);
					if (pos == ss.length - 1) {
						size += 16;
					} else {
						size += 11;
					}
					pos++;
				}
			}
			GlStateManager.popMatrix();
			FontHelper.textCentre(currentPos + 1 + " / " + (infoList.size()), xSize, 152, 0);
			FontHelper.textCentre(pageNum + 1 + " / " + (itemInfo.size()), xSize, 142, 0);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (currentState == GuiState.LIST) {
			int x = mouseX - guiLeft;
			int y = mouseY - guiTop;
			search.mouseClicked(x, y, mouseButton);
			if (x > 3 && x < 124) {
				int start = (int) (infoList.size() * this.currentScroll);
				int finish = Math.min(start + this.getViewableSize(), infoList.size());
				for (int i = start; i < finish; i++) {
					if (i < infoList.size()) {
						if (y > (29 + (i - start) * 18) && y < (29 + (i - start) * 18) + 18) {
							IItemInfo info = infoList.get(i);
							if (info != null) {
								resetPosition();
								currentPos = i;
								changeItemInfo(infoList.get(currentPos));
								currentState = GuiState.INFO;
								resetInfo();
							} else {
								currentPos = -1;
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) throws IOException {
		if (currentState == GuiState.LIST && search.isFocused()) {
			if (c == 13 || c == 27) {
				search.setFocused(false);
			} else {
				search.textboxKeyTyped(c, i);
				resetInfoList();
			}

		} else {
			if ((i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
				if (currentState == GuiState.INFO && lastPos != -1) {
					int last = lastPos;
					resetPosition();
					this.currentPos = last;
					currentCategory = Category.All;
					resetInfo();
					return;
				} else if ((currentState == GuiState.INFO || currentCategory != Category.All)) {
					resetPosition();
					if (currentState == GuiState.LIST)
						currentCategory = Category.All;
					currentState = GuiState.LIST;
					resetInfoList();
					resetInfo();
					return;
				}
			}
			super.keyTyped(c, i);
		}
	}

	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		if (currentState == GuiState.LIST) {
			float lastScroll = currentScroll;
			int i = Mouse.getEventDWheel();

			if (i != 0 && this.needsScrollBars()) {
				int j = infoList.size() + 1;

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
	}

	public void drawScreen(int x, int y, float var) {
		super.drawScreen(x, y, var);
		if (currentState == GuiState.LIST) {
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
				this.currentScroll = ((float) (y - scrollerStart) - 7.5F) / ((float) (scrollerEnd - scrollerStart) - 1.0F);

				if (this.currentScroll < 0.0F) {
					this.currentScroll = 0.0F;
				}
				if (this.currentScroll > 1.0F) {
					this.currentScroll = 1.0F;
				}

			}
		}
	}

	public void drawSelectionBackground(int offsetTop, int i, int pos) {
		drawTexturedModalRect(this.guiLeft + 4, this.guiTop + offsetTop + (getSelectionHeight() * i), 0, i == pos ? 166 + getSelectionHeight() : 166, 119, getSelectionHeight());
	}

	public int getViewableSize() {
		return 7;
	}

	public int getSelectionHeight() {
		return 18;
	}

	private boolean needsScrollBars() {
		if (infoList.size() <= getViewableSize())
			return false;

		return true;

	}

}
