package sonar.calculator.mod.common.tileentity.generators;

import java.util.List;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorPlug;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.ISyncTile;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCalculatorPlug extends TileEntityInventory implements ISyncTile {

	public SyncTagType.INT stable = new SyncTagType.INT("Stable");

	public TileEntityCalculatorPlug() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		int flag = stable.getObject();
		if (testStable()) {
			fill(0);
		}
		if (flag != this.stable.getObject()) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.markDirty();
		}

	}

	public boolean testStable() {
		if (this.slots[0] == null) {
			stable.setObject(0);
			return false;
		}
		if (this.slots[0].getItem() instanceof IStability) {
			return true;
		}
		return false;
	}

	public void fill(int slot) {
		IStability item = (IStability) slots[slot].getItem();
		boolean stability = item.getStability(slots[slot]);
		if (stability) {
			if (this.stable.getObject() != 2) {
				this.stable.setObject(2);
			}
		}

		else if (!stability && slots[slot].getItem() instanceof IStability) {
			stable.setObject(1);
		} else {
			stable.setObject(0);
		}
	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Lists.newArrayList(stable));
	}
	public byte getS() {
		if (stable.getObject() == 2) {
			return 1;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(GuiCalculatorPlug.getString(stable.getObject()));
		return currenttip;
	}

}
