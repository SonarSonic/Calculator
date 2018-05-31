package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.machines.ProcessType;
import sonar.calculator.mod.api.nutrition.IHealthProcessor;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.common.containers.ContainerHealthProcessor;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.filters.SlotHelper;
import sonar.core.network.sync.SyncTagType;

import java.util.List;

public class TileEntityHealthProcessor extends TileEntitySidedInventory implements IFlexibleGui, IHealthProcessor {

	public SyncTagType.INT storedpoints = new SyncTagType.INT(0);
	public final int speed = 4;

	public TileEntityHealthProcessor() {
		super.sides.input = new int[] { 0 };
		super.sides.output = new int[] { 1 };
		super.inv.setSize(2);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(0, s -> isLoot(s) || s.getItem() instanceof IHealthStore), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.filterSlot(1, s -> s.getItem() instanceof IHealthStore), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE) -> SLOT != 1 || storedpoints.getObject() == 0, EnumFilterType.EXTERNAL);
		syncList.addParts(storedpoints);
	}

	@Override
	public void update() {
		super.update();
		if (!this.world.isRemote) {
			loot(slots().get(0));
			charge(slots().get(1));
			markDirty();
		}
	}

	public void charge(ItemStack stack) {
		if (!(stack == null) && this.storedpoints.getObject() != 0) {
			if (stack.getItem() instanceof IHealthStore) {
				IHealthStore module = (IHealthStore) stack.getItem();
				int health = module.getHealthPoints(stack);
				int max = module.getMaxHealthPoints(stack);
				if (!(health >= max) || max == -1) {
					if (storedpoints.getObject() >= speed) {
						if (max == -1 || max >= health + speed) {
							module.transferHealth(speed, stack, ProcessType.ADD);
							storedpoints.increaseBy(-speed);
						} else {
							module.transferHealth(max - health, stack, ProcessType.ADD);
							storedpoints.increaseBy(-(max - health));
						}
					} else if (storedpoints.getObject() <= speed) {
						if (max == -1 | max >= health + speed) {
							module.transferHealth(speed, stack, ProcessType.ADD);
							storedpoints.setObject(0);
						} else {
							module.transferHealth(max - health, stack, ProcessType.ADD);
							storedpoints.increaseBy(-(max - health));
						}
					}
				}
			}
		}
	}

	public int getHealthPoints() {
		return storedpoints.getObject();
	}

	private void loot(ItemStack stack) {
        if (!stack.isEmpty()) {
			int value = HealthProcessorRecipes.instance().getValue(null, stack);
			if (value > 0) {
                storedpoints.increaseBy(value);
				this.slots().get(0).shrink(1);
			}
			if (stack.getItem() instanceof IHealthStore) {
				IHealthStore module = (IHealthStore) stack.getItem();
				int health = module.getHealthPoints(stack);
				if (health != 0) {
					if (health >= speed) {
						module.transferHealth(speed, stack, ProcessType.REMOVE);
						storedpoints.increaseBy(speed);
					}
					if (health <= speed) {
						module.transferHealth(health, stack, ProcessType.REMOVE);
						storedpoints.increaseBy(health);
					}
				}
			}
		}
	}

	private boolean isLoot(ItemStack stack) {
        return HealthProcessorRecipes.instance().getValue(null, stack) > 0;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		currenttip.add(FontHelper.translate("points.health") + ": " + storedpoints);
		return currenttip;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerHealthProcessor(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiHealthProcessor(player.inventory, this);
	}
}