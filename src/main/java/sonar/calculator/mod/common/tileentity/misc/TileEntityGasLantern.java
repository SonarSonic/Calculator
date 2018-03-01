package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.client.gui.misc.GuiGasLantern;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.calculator.mod.common.containers.ContainerLantern;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.SonarCompat;

public class TileEntityGasLantern extends TileEntityInventory implements IGuiTile {

	public SyncTagType.INT burnTime = new SyncTagType.INT("burnTime");
	public SyncTagType.INT maxBurnTime = new SyncTagType.INT("maxBurnTime");

	public TileEntityGasLantern() {
		super.inv = new SonarInventory(this, 1);
		syncList.addParts(burnTime, maxBurnTime, inv);
	}

	@Override
	public void update() {
        super.update();
		if (this.getWorld().isRemote) {
			return;
		}
		boolean flag1 = burnTime.getObject() > 0;
		boolean flag2 = false;
		if (burnTime.getObject() > 0) {
			burnTime.increaseBy(1);
		}
		if (!this.getWorld().isRemote) {
			if (maxBurnTime.getObject() == 0) {
				ItemStack burnStack = slots().get(0);
				if (!SonarCompat.isEmpty(burnStack) && TileEntityFurnace.isItemFuel(burnStack)) {
					burn();
				}
			}
			if (maxBurnTime.getObject() != 0 && burnTime.getObject() == 0) {
				burnTime.increaseBy(1);
				flag2 = true;
			}

			if (burnTime.getObject() >= maxBurnTime.getObject()) {
				maxBurnTime.setObject(0);
				burnTime.setObject(0);
				flag2 = true;
			}
		}

		if (flag1 != this.burnTime.getObject() > 0) {
			flag1 = true;
			GasLantern.setState(this.isBurning(), getWorld(), pos);
			markBlockForUpdate();
		}

		if (flag2) {
			this.markDirty();
		}
	}

	private void burn() {
		ItemStack burnStack = slots().get(0);
		this.maxBurnTime.setObject(TileEntityFurnace.getItemBurnTime(burnStack) * 10);
		burnStack = SonarCompat.shrink(burnStack, 1);
	}

	public boolean isBurning() {
        return this.maxBurnTime.getObject() != 0;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		if (burnTime.getObject() > 0 && maxBurnTime.getObject() != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + burnTime.getObject() * 100 / maxBurnTime.getObject();
			currenttip.add(burn);
		} else {
			String burn = FontHelper.translate("co2.burning");
			currenttip.add(burn);
		}
		return currenttip;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerLantern(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiGasLantern(player.inventory, this);
	}
}
