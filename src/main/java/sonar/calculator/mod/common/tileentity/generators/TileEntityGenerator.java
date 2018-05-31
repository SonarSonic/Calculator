package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.machines.ICalculatorGenerator;
import sonar.calculator.mod.client.gui.generators.GuiExtractor;
import sonar.calculator.mod.common.containers.ContainerGenerator;
import sonar.calculator.mod.common.recipes.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.StarchExtractorRecipes;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.filters.SlotHelper;
import sonar.core.network.sync.SyncTagType;

import java.util.List;

public abstract class TileEntityGenerator extends TileEntityEnergyInventory implements IFlexibleGui, ICalculatorGenerator {

	public SyncTagType.INT itemLevel = new SyncTagType.INT(0);
	public SyncTagType.INT burnTime = new SyncTagType.INT(1);
	public SyncTagType.INT maxBurnTime = new SyncTagType.INT(2);
	public int GENERATOR_CAPACITY;
	public int GENERATOR_REQUIRED;
	public int ENERGY_PER_TICK;

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	public TileEntityGenerator() {
		super.inv.setSize(2);
		super.inv.getInsertFilters().put((SLOT, STACK, FACE) -> FACE == EnumFacing.UP ? SonarHelper.intContains(slotsSides, SLOT) : null, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put((SLOT, STACK, FACE) -> FACE != EnumFacing.UP ? SonarHelper.intContains(slotsTop, SLOT) : null, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(0, s -> TileEntityFurnace.isItemFuel(s)), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(1, s -> getItemValue(s) > 0), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE) -> FACE == EnumFacing.DOWN && inv.getStackInSlot(SLOT).getItem() == Items.BUCKET, EnumFilterType.EXTERNAL);
		super.energyMode = EnergyMode.SEND;
		syncList.addParts(itemLevel, burnTime, maxBurnTime);
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
			if (!(this.storage.getEnergyStored() == this.storage.getMaxEnergyStored()) && this.itemLevel.getObject() >= GENERATOR_REQUIRED) {
				int itemBurnTime = TileEntityFurnace.getItemBurnTime(stack);
				if (itemBurnTime != 0) {
					this.maxBurnTime.setObject(itemBurnTime);
					burnTime.increaseBy(1);
					ItemStack burnStack = slots().get(0);

					if (!burnStack.isEmpty()) {
						if(burnStack.getCount() == 1){
							ItemStack container = burnStack.getItem().getContainerItem(burnStack);
							inv.setStackInSlot(0, container);
						}else {
							burnStack.shrink(1);
						}
					}
				}
			}
		}
		if (burnTime.getObject() > 0 && !(burnTime.getObject() >= maxBurnTime.getObject())) {
			this.storage.receiveEnergy(ENERGY_PER_TICK, false);
			burnTime.increaseBy(1);
		}
		if (maxBurnTime.getObject() != 0 && burnTime.getObject() >= maxBurnTime.getObject()) {
			this.storage.receiveEnergy(ENERGY_PER_TICK, false);
			burnTime.setObject(0);
			this.removeItem(GENERATOR_REQUIRED);
		}
	}

	public void processItemLevel() {
		ItemStack stack = slots().get(1);
		if (stack.isEmpty() || !(getItemValue(stack) > 0)) {
			return;
		}
		if (!(itemLevel.getObject() + getItemValue(stack) > GENERATOR_CAPACITY)) {
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
	public int getItemLevel() {
		return itemLevel.getObject();
	}

	@Override
	public int getMaxItemLevel() {
		return GENERATOR_CAPACITY;
	}

	public static class StarchExtractor extends TileEntityGenerator {
		public StarchExtractor() {
			super();
			super.ENERGY_PER_TICK = CalculatorConfig.STARCH_EXTRACTOR_PER_TICK;
			super.GENERATOR_CAPACITY = CalculatorConfig.STARCH_EXTRACTOR_GENERATOR_CAPACITY;
			super.GENERATOR_REQUIRED = CalculatorConfig.STARCH_EXTRACTOR_GENERATOR_REQUIRED;
			super.storage.setCapacity(CalculatorConfig.STARCH_EXTRACTOR_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.STARCH_EXTRACTOR_TRANSFER_RATE);
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.starch") + ": " + this.itemLevel.getObject() * 100 / GENERATOR_CAPACITY + '%');
			return currenttip;
		}

		@Override
		public int getItemValue(ItemStack stack) {
			return StarchExtractorRecipes.instance().getValue(null, stack);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiExtractor.Starch(player.inventory, this);
		}
	}

	public static class RedstoneExtractor extends TileEntityGenerator {
		public RedstoneExtractor() {
			super();
			super.ENERGY_PER_TICK = CalculatorConfig.REDSTONE_EXTRACTOR_PER_TICK;
			super.GENERATOR_CAPACITY = CalculatorConfig.REDSTONE_EXTRACTOR_GENERATOR_CAPACITY;
			super.GENERATOR_REQUIRED = CalculatorConfig.REDSTONE_EXTRACTOR_GENERATOR_REQUIRED;
			super.storage.setCapacity(CalculatorConfig.REDSTONE_EXTRACTOR_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.REDSTONE_EXTRACTOR_TRANSFER_RATE);
		}

        @Override
		public int getItemValue(ItemStack stack) {
			return RedstoneExtractorRecipes.instance().getValue(null, stack);
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.redstone") + ": " + this.itemLevel.getObject() * 100 / GENERATOR_CAPACITY + '%');
			return currenttip;
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiExtractor.Redstone(player.inventory, this);
		}
	}

	public static class GlowstoneExtractor extends TileEntityGenerator {
		public GlowstoneExtractor() {
			super();
			super.ENERGY_PER_TICK = CalculatorConfig.GLOWSTONE_EXTRACTOR_PER_TICK;
			super.GENERATOR_CAPACITY = CalculatorConfig.GLOWSTONE_EXTRACTOR_GENERATOR_CAPACITY;
			super.GENERATOR_REQUIRED = CalculatorConfig.GLOWSTONE_EXTRACTOR_GENERATOR_REQUIRED;
			super.storage.setCapacity(CalculatorConfig.GLOWSTONE_EXTRACTOR_STORAGE);
			super.storage.setMaxTransfer(CalculatorConfig.GLOWSTONE_EXTRACTOR_TRANSFER_RATE);
		}

        @Override
		public int getItemValue(ItemStack stack) {
			return GlowstoneExtractorRecipes.instance().getValue(null, stack);
		}

        @Override
		@SideOnly(Side.CLIENT)
		public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
            currenttip.add(FontHelper.translate("generator.glowstone") + ": " + this.itemLevel.getObject() * 100 / GENERATOR_CAPACITY + '%');
			return currenttip;
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiExtractor.Glowstone(player.inventory, this);
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerGenerator(player.inventory, this);
	}
}
