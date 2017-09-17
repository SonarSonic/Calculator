package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.containers.ContainerModuleSelector;
import sonar.calculator.mod.common.item.calculators.modules.EmptyModule;
import sonar.calculator.mod.network.packets.PacketModuleSelection;
import sonar.core.helpers.FontHelper;

import java.io.IOException;
import java.util.ArrayList;

public class GuiModuleSelector extends GuiContainer {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/module_selection.png");

	public ItemStack calc;
	public EntityPlayer player;
	public float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	public int scrollerLeft, scrollerStart, scrollerEnd, scrollerWidth;
    public ArrayList<IModule> modules = new ArrayList<>();
	public IModule current = EmptyModule.EMPTY;
	public int currentSlot = -1;

	public GuiModuleSelector(EntityPlayer player, ItemStack calc) {
		super(new ContainerModuleSelector(player, calc));
		this.calc = calc;
		this.player = player;
		Item item = calc.getItem();
		if (item instanceof IFlawlessCalculator) {
			modules = ((IFlawlessCalculator) item).getModules(calc);
			current = ((IFlawlessCalculator) item).getCurrentModule(calc);
			currentSlot = ((IFlawlessCalculator) item).getCurrentSlot(calc);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		drawTexturedModalRect(scrollerLeft, scrollerStart + (int) ((float) (scrollerEnd - scrollerStart - 17) * this.currentScroll), 136, 0, 8, 15);

		int pos = getDataPosition();
		int offsetTop = 29;
		if (getViewableSize() == 7) {
			offsetTop = offsetTop + 1;
		}
		for (int i = 0; i < getViewableSize(); i++) {
			drawSelectionBackground(offsetTop, i, pos);
		}
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.mc.player.openContainer = this.inventorySlots;
		this.xSize = 136;
		this.ySize = 166;

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		scrollerLeft = this.guiLeft + 136-12;
		scrollerStart = this.guiTop + 30;
		scrollerEnd = scrollerStart + 128;
		scrollerWidth = 10;
		int offsetTop = 29;
		if (getViewableSize() == 7) {
			offsetTop = offsetTop + 2;
		}
	}

	public int getDataPosition() {
		if (modules == null) {
			return -1;
		}
		if (current == EmptyModule.EMPTY) {
			return -1;
		}
		int start = (int) (modules.size() * this.currentScroll);
		int finish = Math.min(start + getViewableSize(), modules.size());
		for (int i = start; i < finish; i++) {
			IModule module = modules.get(i);
			if (module != null && module != EmptyModule.EMPTY) {
				if (module.getName().equals(current.getName()) && i == currentSlot)
					return i - start;
			}
		}
		return -1;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("item.FlawlessCalculator.name"), xSize, 10, 0);
		if (modules != null) {
			int start = (int) (modules.size() * this.currentScroll);
			int finish = Math.min(start + this.getViewableSize(), modules.size());
			int pos = this.getDataPosition();
			for (int i = start; i < finish; i++) {
				IModule module = modules.get(i);
				if (module != null && module != EmptyModule.EMPTY) {
					Item item = Calculator.moduleItems.getPrimaryObject(module.getName());
					if (item != null) {
                        itemRender.renderItemIntoGUI(new ItemStack(item, 1), 5, 31 + (i - start) * 18);
					}
                    FontHelper.text(module.getClientName(), 28, 35 + (i - start) * 18, -1);
				}else{
                    FontHelper.text("-empty-", 28, 35 + (i - start) * 18, -1);
				}
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int x = mouseX-guiLeft;
		int y = mouseY-guiTop;
		if (x > 3 && x < 124) {
			int start = (int) (modules.size() * this.currentScroll);
			int finish = Math.min(start + this.getViewableSize(), modules.size());
			for (int i = start; i < finish; i++) {
				if (i < modules.size()) {
                    if (y > 29 + (i - start) * 18 && y < 29 + (i - start) * 18 + 18) {
						IModule module = modules.get(i);
						if (module != null && module != EmptyModule.EMPTY) {
							this.current = module;
							this.currentSlot = i;
							Calculator.network.sendToServer(new PacketModuleSelection(current, currentSlot));
						}
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
			int j = modules.size() + 1;

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
		this.drawDefaultBackground();
		super.drawScreen(x, y, var);
		this.renderHoveredToolTip(x, y);
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
        drawTexturedModalRect(this.guiLeft + 4, this.guiTop + offsetTop + getSelectionHeight() * i, 0, i == pos ? 166 + getSelectionHeight() : 166, 154 + 72, getSelectionHeight());
	}

	public int getViewableSize() {
		return 7;
	}

	public int getSelectionHeight() {
		return 18;
	}

	private boolean needsScrollBars() {
        return modules.size() > getViewableSize();
	}
}
