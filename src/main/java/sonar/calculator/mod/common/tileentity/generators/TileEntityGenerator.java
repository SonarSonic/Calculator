package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.machines.ICalculatorGenerator;
import sonar.calculator.mod.client.gui.generators.GuiExtractor;
import sonar.calculator.mod.common.containers.ContainerGenerator;
import sonar.calculator.mod.common.recipes.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.StarchExtractorRecipes;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.utils.IGuiTile;

import java.util.List;

public abstract class TileEntityGenerator extends TileEntityEnergyInventory implements ISidedInventory, IGuiTile, ICalculatorGenerator {

	public SyncTagType.INT itemLevel = new SyncTagType.INT(0);
	public SyncTagType.INT burnTime = new SyncTagType.INT(1);
	public SyncTagType.INT maxBurnTime = new SyncTagType.INT(2);
	public int levelMax = 5000;
	public int requiredLevel = 400;
	public int energyMultiplier;

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	public TileEntityGenerator() {
		super.storage.setCapacity(1000000).setMaxTransfer(2000);
		super.inv = new SonarInventory(this, 2);
		super.energyMode = EnergyMode.SEND;
		super.maxTransfer = 2000;
		syncList.addParts(itemLevel, burnTime, maxBurnTime, inv);
	}

	@Override
	public void update() {
		super.update();
		if (!this.world.isRemote) {
			processItemLevel();
			generateEnergy();
			this.addEnergy(EnumFacing.VALUES);
		}
		this.markDirty();
	}

	public void generateEnergy() {
		ItemStack stack = this.getStackInSlot(0);
        if (!stack.isEmpty() && burnTime.getObject() == 0 && TileEntityFurnace.isItemFuel(stack)) {
			if (!(this.storage.getEnergyStored() == this.storage.getMaxEnergyStored()) && this.itemLevel.getObject() >= requiredLevel) {
				int itemBurnTime = TileEntityFurnace.getItemBurnTime(stack);
				if (itemBurnTime != 0) {
					this.maxBurnTime.setObject(itemBurnTime);
					burnTime.increaseBy(1);
					ItemStack burnStack = slots().get(0);

					if (!burnStack.isEmpty()) {
						burnStack.shrink(1);
					}
				}
			}
		}
		if (burnTime.getObject() > 0 && !(burnTime.getObject() >= maxBurnTime.getObject())) {
			this.storage.receiveEnergy(energyMultiplier, false);
			burnTime.increaseBy(1);
		}
		if (maxBurnTime.getObject() != 0 && burnTime.getObject() >= maxBurnTime.getObject()) {
			this.storage.receiveEnergy(energyMultiplier, false);
			burnTime.setObject(0);
			this.removeItem(requiredLevel);
		}
	}

	public void processItemLevel() {
		ItemStack stack = slots().get(1);
		if (stack.isEmpty() || !(getItemValue(stack) > 0)) {
			return;
		}
		if (!(itemLevel.getObject() + getItemValue(stack) > levelMax)) {
			addItem(getItemValue(stack));
			stack.shrink(1);
		}
	}

	public abstract int getItemValue(ItemStack stack);

	public void addItem(int add) {
		itemLevel.increaseBy(add);
	}

	public void removeItem(int remove) {
		itemLevel.increaseBy(-remove);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (TileEntityFurnace.isItemFuel(stack)) {
				return true;
			}
		}
		if (slot == 1) {
			if (getItemValue(stack) > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsSides : side == EnumFacing.UP ? slotsTop : slotsSides;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
		return direction != EnumFacing.DOWN || slot != 1 || stack != null && stack.getItem() == Items.BUCKET;
	}

	@Override
	public int getItemLevel() {
		return itemLevel.getObject();
	}

	@Override
	public int getMaxItemLevel() {
		return levelMax;
	}

	public static class StarchExtractor extends TileEntityGenerator {
		public StarchExtractor() {
			super.energyMultiplier = CalculatorConfig.getInteger("Starch Extractor");
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.starch") + ": " + this.itemLevel.getObject() * 100 / 5000 + '%');
			return currenttip;
		}

		@Override
		public int getItemValue(ItemStack stack) {
			return StarchExtractorRecipes.instance().getValue(null, stack);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiExtractor.Starch(player.inventory, this);
		}
	}

	public static class RedstoneExtractor extends TileEntityGenerator {
		public RedstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.getInteger("Redstone Extractor");
		}

        @Override
		public int getItemValue(ItemStack stack) {
			return RedstoneExtractorRecipes.instance().getValue(null, stack);
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.redstone") + ": " + this.itemLevel.getObject() * 100 / 5000 + '%');
			return currenttip;
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiExtractor.Redstone(player.inventory, this);
		}
	}

	public static class GlowstoneExtractor extends TileEntityGenerator {
		public GlowstoneExtractor() {
			super.energyMultiplier = CalculatorConfig.getInteger("Glowstone Extractor");
		}

        @Override
		public int getItemValue(ItemStack stack) {
			return GlowstoneExtractorRecipes.instance().getValue(null, stack);
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.glowstone") + ": " + this.itemLevel.getObject() * 100 / 5000 + '%');
			return currenttip;
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiExtractor.Glowstone(player.inventory, this);
		}
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerGenerator(player.inventory, this);
	}
}
