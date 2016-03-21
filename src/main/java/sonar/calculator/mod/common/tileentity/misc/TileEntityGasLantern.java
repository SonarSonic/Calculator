package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.ISyncTile;
import sonar.core.utils.helpers.FontHelper;

import com.google.common.collect.Lists;

public class TileEntityGasLantern extends TileEntityInventory implements ISyncTile {

	public SyncTagType.INT burnTime = new SyncTagType.INT("burnTime");
	public SyncTagType.INT maxBurnTime = new SyncTagType.INT("maxBurnTime");
	
	public TileEntityGasLantern() {
		super.inv = new SonarTileInventory(this, 1);
	}

	@Override
	public void update() {
		if (this.worldObj.isRemote) {
			return;
		}
		boolean flag1 = burnTime.getObject() > 0;
		boolean flag2 = false;
		if (burnTime.getObject() > 0) {
			burnTime.increaseBy(1);
		}
		if (!this.worldObj.isRemote) {
			if (maxBurnTime.getObject() == 0) {
				if (this.slots()[0] != null) {
					if (TileEntityFurnace.isItemFuel(slots()[0])) {
						burn();
					}
				}
			}
			if (maxBurnTime.getObject() != 0 && burnTime.getObject() == 0) {
				burnTime.increaseBy(1);
				flag2 = true;
			}

			if (burnTime == maxBurnTime) {
				maxBurnTime.setObject(0);
				burnTime.setObject(0);
				flag2 = true;
			}
		}

		if (flag1 != this.burnTime.getObject() > 0) {
			flag1 = true;

			GasLantern.setState(this.isBurning(), worldObj, pos);
			this.worldObj.markBlockForUpdate(pos);

		}

		if (flag2) {
			this.markDirty();
		}

	}

	private void burn() {
		this.maxBurnTime.setObject(TileEntityFurnace.getItemBurnTime(this.slots()[0]) * 10);
		this.slots()[0].stackSize--;

		if (this.slots()[0].stackSize <= 0) {
			this.slots()[0] = null;
		}

	}

	public boolean isBurning() {
		if (this.maxBurnTime.getObject() == 0) {
			return false;
		}
		return true;
	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Lists.newArrayList(burnTime, maxBurnTime));
	}
	
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if (burnTime.getObject() > 0 && maxBurnTime.getObject() != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + burnTime.getObject() * 100 / maxBurnTime.getObject();
			currenttip.add(burn);
		} else {
			String burn = FontHelper.translate("co2.burning");
			currenttip.add(burn);
		}
		return currenttip;
	}
}
