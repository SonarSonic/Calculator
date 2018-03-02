package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.machines.ProcessType;
import sonar.calculator.mod.api.nutrition.IHealthProcessor;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.common.containers.ContainerHealthProcessor;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.SonarCompat;

public class TileEntityHealthProcessor extends TileEntitySidedInventory implements IGuiTile, IHealthProcessor {

	public SyncTagType.INT storedpoints = new SyncTagType.INT(0);
	public final int speed = 4;

	public TileEntityHealthProcessor() {
		super.input = new int[] { 0 };
		super.output = new int[] { 1 };
		super.inv = new SonarInventory(this, 2);
		syncList.addParts(storedpoints, inv);
	}

	@Override
	public void update() {
		super.update();
		if (!this.getWorld().isRemote)
			loot(slots().get(0));

		charge(slots().get(1));
		this.markDirty();
	}

	public void charge(ItemStack stack) {
		if (!(SonarCompat.isEmpty(stack)) && this.storedpoints.getObject() != 0) {
			if (stack.getItem() instanceof IHealthStore) {
				IHealthStore module = (IHealthStore) stack.getItem();
				int health = module.getHealthPoints(stack);
				int max = module.getMaxHealthPoints(stack);
				if (!(health >= max) || max == -1) {
					if (storedpoints.getObject() >= speed) {
						if (max == -1 || max >= health + speed) {
							module.transferHealth(speed, stack, ProcessType.ADD);
							storedpoints.increaseBy(-speed);
						} else if (max != -1) {
							module.transferHealth(max - health, stack, ProcessType.ADD);
							storedpoints.increaseBy(-(max - health));
						}
					} else if (storedpoints.getObject() <= speed) {
						if (max == -1 | max >= health + speed) {
							module.transferHealth(speed, stack, ProcessType.ADD);
							storedpoints.setObject(0);
						} else if (max != -1) {
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
        if (!SonarCompat.isEmpty(stack)) {
			int value = HealthProcessorRecipes.instance().getValue(null, stack);
			if (value > 0) {
                storedpoints.increaseBy(value);
                SonarCompat.shrinkAndSet(slots(), 0, 1);
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
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing slots) {
		if (slot == 1) {
			if (this.storedpoints.getObject() == 0) {
				return true;
			}
			if (!(this.storedpoints.getObject() == 0)) {
				return false;
			}
		}
		return true;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		currenttip.add(FontHelper.translate("points.health") + ": " + storedpoints);
		return currenttip;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerHealthProcessor(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiHealthProcessor(player.inventory, this);
	}
}
