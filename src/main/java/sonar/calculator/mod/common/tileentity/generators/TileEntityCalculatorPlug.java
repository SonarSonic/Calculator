package sonar.calculator.mod.common.tileentity.generators;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorPlug;
import sonar.calculator.mod.common.block.generators.CalculatorPlug;
import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

import java.util.List;

public class TileEntityCalculatorPlug extends TileEntityInventory implements IGuiTile, IByteBufTile {

	public SyncTagType.INT stable = new SyncTagType.INT(0);

	public TileEntityCalculatorPlug() {
		super.inv = new SonarInventory(this, 1);
		syncList.addParts(stable, inv);
	}

	@Override
	public void update() {
		super.update();
		if (this.isClient()) {
			return;
		}
		int flag = stable.getObject();
		if (testStable()) {
			fill(0);
		}
		if (flag != this.stable.getObject()) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(CalculatorPlug.ACTIVE, stable.getObject() == 2), 2);
		}
	}

    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public boolean testStable() {
		ItemStack testItem = slots().get(0);
		if (testItem.isEmpty()) {
			stable.setObject(0);
			return false;
		}
        return testItem.getItem() instanceof IStability;
	}

	public void fill(int slot) {
		ItemStack circuit = slots().get(slot);
		IStability item = (IStability) circuit.getItem();
		boolean stability = item.getStability(circuit);
		if (stability) {
			if (this.stable.getObject() != 2) {
				this.stable.setObject(2);
			}
		} else if (!stability && circuit.getItem() instanceof IStability) {
			stable.setObject(1);
		} else {
			stable.setObject(0);
		}
	}

	public byte getS() {
		if (stable.getObject() == 2) {
			return 1;
		}
		return 0;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		currenttip.add(FontHelper.translate("circuit.stable") + ": " + (!state.getValue(CalculatorPlug.ACTIVE) ? FontHelper.translate("locator.false") : FontHelper.translate("locator.true")));
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

	@Override
	public void writePacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			stable.writeToBuf(buf);
			break;
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			stable.readFromBuf(buf);
			markBlockForUpdate();
			break;
		}
	}
}
