package sonar.calculator.mod.client.gui.misc;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.recipes.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.FabricationSonarRecipe;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.core.SonarCore;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.network.PacketByteBuf;
import sonar.core.network.utils.ByteBufWritable;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeHelperV2;

import java.io.IOException;
import java.util.List;

public class GuiFabricationChamber extends GuiSonarTile {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fabrication_chamber.png");

	public TileEntityFabricationChamber chamber;
	public float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;
	public int currentSlot = -1;
	public final List<FabricationSonarRecipe> recipes = FabricationChamberRecipes.instance().getRecipes();

	public GuiFabricationChamber(InventoryPlayer player, TileEntityFabricationChamber chamber) {
		super(new ContainerFabricationChamber(player, chamber), chamber);
		this.chamber = chamber;
		this.ySize = 200;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 176, 0, 8, 15);
		int pos = getDataPosition();
		int offsetTop = 6;
		for (int i = 0; i < getViewableSize(); i++) {
			drawSelectionBackground(offsetTop, i, pos);
		}

		if (chamber.currentFabricateTime.getObject() != 0) {
			int l = chamber.currentFabricateTime.getObject() * 23 / chamber.fabricateTime;
			drawTexturedModalRect(this.guiLeft + 84 + 10, this.guiTop + 89, 176, 16, l, 16);
		}
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		scrollerLeft = this.guiLeft + 91;
		scrollerStart = this.guiTop + 6;
		scrollerEnd = scrollerStart + 74;
		scrollerWidth = 10;
		this.buttonList.add(new GuiButton(0, guiLeft + 26, guiTop + 87, 60, 20, "Fabricate"));
	}

	public int getDataPosition() {
		if (chamber.selected == null) {
			return -1;
		}
		int start = (int) (recipes.size() * this.currentScroll);
		int finish = Math.min(start + getViewableSize(), recipes.size());
		for (int i = start; i < finish; i++) {
			ItemStack stack = RecipeHelperV2.getItemStackFromList(FabricationChamberRecipes.instance().getRecipes().get(i).outputs(), 0);
			if (ItemStackHelper.equalStacksRegular(chamber.selected, stack)) {
				return i - start;
			}
		}
		return -1;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textOffsetCentre("Required", 135, 8, -1);
		int start = (int) (recipes.size() * this.currentScroll);
		int finish = Math.min(start + this.getViewableSize(), recipes.size());
		int pos = this.getDataPosition();
		for (int i = start; i < finish; i++) {
			ItemStack stack = RecipeHelperV2.getItemStackFromList(FabricationChamberRecipes.instance().getRecipes().get(i).outputs(), 0);
			if (!stack.isEmpty()) {
                itemRender.renderItemIntoGUI(stack, 7, 7 + (i - start) * 18);
				GL11.glPushMatrix();
				GL11.glScaled(0.7, 0.7, 0.7);
				String string = stack.getDisplayName();
				if (string.length() > 15) {
					string = string.substring(0, Math.min(15, string.length())) + "...";
				}
                FontHelper.text(' ' + string, 32, 17 + (i - start) * 26, -1);
				GL11.glPopMatrix();
			}
		}
		if (chamber.selected != null) {
			ISonarRecipe recipe = FabricationChamberRecipes.instance().getRecipeFromOutputs(null, new Object[] { chamber.selected });
			if (recipe != null) {
				GL11.glPushMatrix();
				GL11.glScaled(0.8, 0.8, 0.8);
				int left = 124;
				int top = 24;
				int cPos = 0;
				for (ISonarRecipeObject circuit : recipe.inputs()) {
                    int cLeft = left + (cPos - cPos / 5 * 5) * 18;
                    int cTop = top + cPos / 5 * 18;
					ItemStack stack = (ItemStack) circuit.getValue();
					itemRender.renderItemIntoGUI(stack, cLeft, cTop);
                    itemRender.renderItemOverlayIntoGUI(fontRenderer, stack, cLeft, cTop, null);
					cPos++;
				}
				GL11.glPopMatrix();
			} else {
				FontHelper.textOffsetCentre("INVALID RECIPE", 135, 18, 0);
			}
		} else {
			FontHelper.textOffsetCentre("NO RECIPE", 135, 22, -1);
		}
	}

    @Override
	protected void actionPerformed(GuiButton button) {
		if (button != null && button.id == 0) {
			SonarCore.network.sendToServer(new PacketByteBuf(chamber, chamber.getPos(), 1));
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int x = mouseX - guiLeft;
		int y = mouseY - guiTop;
		if (x > 3 && x < 90) {
			int start = (int) (recipes.size() * this.currentScroll);
			int finish = Math.min(start + this.getViewableSize(), recipes.size());
			for (int i = start; i < finish; i++) {
                if (y > 4 + (i - start) * 18 && y < 4 + (i - start) * 18 + 18) {
					ItemStack stack = RecipeHelperV2.getItemStackFromList(FabricationChamberRecipes.instance().getRecipes().get(i).outputs(), 0);
					if (!stack.isEmpty()) {
						SonarCore.network.sendToServer(new PacketByteBuf(chamber, chamber.getPos(), 0, new ByteBufWritable(true) {
							@Override
							public void writeToBuf(ByteBuf buf) {
								ByteBufUtils.writeItemStack(buf, stack);
							}
						}));
					}
				}
			}
		}
	}

    @Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		float lastScroll = currentScroll;
		int i = Mouse.getEventDWheel();

		if (i != 0 && this.needsScrollBars()) {
			int j = recipes.size() + 1;

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

    @Override
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
			this.currentScroll = ((float) (y - scrollerStart) - 7.5F) / ((float) (scrollerEnd - scrollerStart) - 1.0F);

			if (this.currentScroll < 0.0F) {
				this.currentScroll = 0.0F;
			}
			if (this.currentScroll > 1.0F) {
				this.currentScroll = 1.0F;
			}
		}
	}

	public void drawSelectionBackground(int offsetTop, int i, int pos) {
        drawTexturedModalRect(this.guiLeft + 6, this.guiTop + offsetTop + getSelectionHeight() * i, 0, i == pos ? 220 + getSelectionHeight() : 220, 88, getSelectionHeight());
	}

	public int getViewableSize() {
		return 4;
	}

	public int getSelectionHeight() {
		return 18;
	}

	private boolean needsScrollBars() {
        return recipes.size() > getViewableSize();
	}
}
