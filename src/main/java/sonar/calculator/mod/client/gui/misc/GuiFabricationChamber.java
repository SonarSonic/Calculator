package sonar.calculator.mod.client.gui.misc;

import gnu.trove.map.hash.THashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerModuleSelector;
import sonar.calculator.mod.common.item.calculators.modules.EmptyModule;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes.CircuitStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.calculator.mod.network.packets.PacketModuleSelection;
import sonar.core.SonarCore;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.helpers.RenderHelper;
import sonar.core.inventory.GuiSonar;
import sonar.core.inventory.SonarButtons;
import sonar.core.inventory.SonarButtons.SonarButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiFabricationChamber extends GuiContainer {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fabrication_chamber.png");

	public TileEntityFabricationChamber chamber;
	public float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;
	public int currentSlot = -1;
	public LinkedHashMap<ItemStack, CircuitStack[]> recipes = FabricationChamberRecipes.instance.recipes;
	public LinkedHashMap<CircuitStack[], ItemStack> recipes_reversed = FabricationChamberRecipes.instance.recipes_reverse;

	public GuiFabricationChamber(InventoryPlayer player, TileEntityFabricationChamber chamber) {
		super(new ContainerFabricationChamber(player, chamber));
		this.chamber = chamber;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 176, 27, 8, 15);
		int pos = getDataPosition();
		int offsetTop = 6;
		for (int i = 0; i < getViewableSize(); i++) {
			drawSelectionBackground(offsetTop, i, pos);
		}
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.mc.thePlayer.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		scrollerLeft = this.guiLeft + 91;
		scrollerStart = this.guiTop + 6;
		scrollerEnd = scrollerStart + 74;
		scrollerWidth = 10;
	}

	public int getDataPosition() {
		if (chamber.selected == null) {
			return -1;
		}
		int start = (int) (recipes.size() * this.currentScroll);
		int finish = Math.min(start + getViewableSize(), recipes.size());
		for (int i = start; i < finish; i++) {
			ItemStack stack = (new ArrayList<ItemStack>(recipes_reversed.values())).get(i);
			if (stack != null && ItemStackHelper.equalStacksRegular(chamber.selected, stack)) {
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
			ItemStack stack = (new ArrayList<ItemStack>(recipes_reversed.values())).get(i);
			if (stack != null) {
				itemRender.renderItemIntoGUI(stack, 7, 7 + ((i - start) * 18));
				GL11.glPushMatrix();
				GL11.glScaled(0.7, 0.7, 0.7);
				String string = stack.getDisplayName();
				if (string.length() > 15) {
					string = string.substring(0, Math.min(15, string.length())) + "...";
				}
				FontHelper.text(" " + string, 32, 17 + ((i - start) * 26), -1);
				GL11.glPopMatrix();
			}
		}
		if (chamber.selected != null) {
			CircuitStack[] requirements = FabricationChamberRecipes.instance.getRequirements(chamber.selected);
			if (requirements != null) {
				GL11.glPushMatrix();
				GL11.glScaled(0.8, 0.8, 0.8);
				int left = 124;
				int top = 24;
				int cPos = 0;
				for (CircuitStack circuit : requirements) {
					int cLeft = left + ((cPos - ((cPos / 5) * 5)) * 18);
					int cTop = top + (cPos / 5) * 18;
					ItemStack stack = new ItemStack(Calculator.circuitBoard, (int) circuit.required, circuit.meta);

					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Stable", circuit.stable ? 1 : 0);
					stack.setTagCompound(tag);
					itemRender.renderItemIntoGUI(stack, cLeft, cTop);
					itemRender.renderItemOverlayIntoGUI(fontRendererObj, stack, cLeft, cTop, "" + circuit.required);
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
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int x = mouseX - guiLeft;
		int y = mouseY - guiTop;
		if (x > 3 && x < 90) {
			int start = (int) (recipes.size() * this.currentScroll);
			int finish = Math.min(start + this.getViewableSize(), recipes.size());
			for (int i = start; i < finish; i++) {
				if (y > (4 + (i - start) * 18) && y < (4 + (i - start) * 18) + 18) {
					chamber.selected = (new ArrayList<ItemStack>(recipes_reversed.values())).get(i);
				}

			}
		}
	}

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
		drawTexturedModalRect(this.guiLeft + 5, this.guiTop + offsetTop + (getSelectionHeight() * i), 0, i == pos ? 166 + getSelectionHeight() : 166, 88, getSelectionHeight());
	}

	public int getViewableSize() {
		return 4;
	}

	public int getSelectionHeight() {
		return 18;
	}

	private boolean needsScrollBars() {
		if (recipes.size() <= getViewableSize())
			return false;

		return true;

	}

}
