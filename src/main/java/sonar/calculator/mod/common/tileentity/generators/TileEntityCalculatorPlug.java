package sonar.calculator.mod.common.tileentity.generators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorPlug;
import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.utils.IGuiTile;

import com.google.common.collect.Lists;

public class TileEntityCalculatorPlug extends TileEntityInventory implements IGuiTile  {

	public SyncTagType.INT stable = new SyncTagType.INT(0);

	public TileEntityCalculatorPlug() {
		super.inv = new SonarInventory(this, 1);
	}

	@Override
	public void update() {
		super.update();
		int flag = stable.getObject();
		if (testStable()) {
			fill(0);
		}
		if (flag != this.stable.getObject()) {
			this.worldObj.markBlockForUpdate(pos);
			this.markDirty();
		}

	}

	public boolean testStable() {
		if (this.slots()[0] == null) {
			stable.setObject(0);
			return false;
		}
		if (this.slots()[0].getItem() instanceof IStability) {
			return true;
		}
		return false;
	}

	public void fill(int slot) {
		IStability item = (IStability) slots()[slot].getItem();
		boolean stability = item.getStability(slots()[slot]);
		if (stability) {
			if (this.stable.getObject() != 2) {
				this.stable.setObject(2);
			}
		}
		else if (!stability && slots()[slot].getItem() instanceof IStability) {
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

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerCalculatorPlug(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiCalculatorPlug(player.inventory, this);
	}

}
