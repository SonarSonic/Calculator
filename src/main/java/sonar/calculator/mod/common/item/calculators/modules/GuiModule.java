package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.modules.IModuleInventory;
import sonar.calculator.mod.client.gui.calculators.*;
import sonar.calculator.mod.client.gui.modules.GuiStorageModule;
import sonar.calculator.mod.common.containers.*;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.item.InventoryItem;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.IGuiItem;

public abstract class GuiModule extends ModuleBase implements IModuleInventory, IGuiItem {

	public static GuiModule calculator = new CalculatorModule();
	public static GuiModule scientific = new ScientificModule();
	public static GuiModule flawless = new FlawlessModule();
	public static GuiModule dynamic = new DynamicModule();
	public static GuiModule crafting = new CraftingModule();
	public static GuiModule storage = new StorageModule();
	
	public String name, clientName;
	public int size;
	public boolean requiresEnergy;

	public GuiModule(String name, String clientName, int size, boolean requiresEnergy) {
		this.name = name;
		this.clientName = clientName;
		this.size = size;
		this.requiresEnergy = requiresEnergy;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getClientName() {
		return FontHelper.translate(clientName);
	}

	@Override
	public InventoryItem getInventory(ItemStack stack, String tagName, boolean useStackTag) {
		return new InventoryItem(stack, size, tagName, useStackTag);
	}

	@Override
	public boolean isSharedInventory(ItemStack stack) {
		return false;
	}

	@Override
	public void onModuleActivated(ItemStack stack, NBTTagCompound tag, World world, EntityPlayer player) {
		if (!requiresEnergy || isEnergyAvailable(stack, player, world, 1)/* || this == storage*/) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, ID, world, -1000, -1000, -1000);
			}
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}

	public static class CalculatorModule extends GuiModule {

		public CalculatorModule() {
			super("Calculator", "item.Calculator.name", 3, true);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}
	}

	public static class ScientificModule extends GuiModule {

		public ScientificModule() {
			super("Scientific", "item.ScientificCalculator.name", 3, true);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerScientificCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiScientificCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}
	}

	public static class FlawlessModule extends GuiModule {

		public FlawlessModule() {
			super("Flawless", "flawless.mode1", 5, false);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerFlawlessCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiFlawlessCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}
	}

	public static class DynamicModule extends GuiModule {

		public DynamicModule() {
			super("Dynamic", "flawless.mode2", 10, false);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerDynamicModule(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiDynamicModule(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}
	}

	public static class CraftingModule extends GuiModule {

		public CraftingModule() {
			super("Crafting", "flawless.mode3", 10, true);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerCraftingCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiCraftingCalculator(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}
	}

	public static class StorageModule extends GuiModule {

		public StorageModule() {
			super("Storage", "Storage Module", 54, false);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player, ItemStack stack) {
			return new ContainerStorageModule(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public Object getGuiScreen(EntityPlayer player, ItemStack stack) {
			return new GuiStorageModule(player, ((IItemInventory) stack.getItem()).getInventory(stack));
		}

		@Override
		public boolean isSharedInventory(ItemStack stack) {
			return true;
		}
	}
}
