package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.network.packets.PacketTileSync;

public abstract class ContainerSync extends Container {

	public ISyncTile sync;
	public TileEntity tile;
	public int[] syncData = new int[SyncType.MAX];

	public ContainerSync(ISyncTile sync, TileEntity tile) {
		this.sync = sync;
		this.tile = tile;

	}

	public ContainerSync(TileEntity tile) {
		if (tile instanceof ISyncTile) {
			sync = (ISyncTile) tile;
		}
		this.tile = tile;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (sync != null) {
			for (int i = 0; i < syncData.length; i++) {
				if (sync.getSyncData(i).used) {
					if (crafters != null) {						
						for (Object o : crafters){
							if (o != null && o instanceof EntityPlayerMP){
								Calculator.network.sendTo(new PacketTileSync(tile.xCoord, tile.yCoord, tile.zCoord,i, sync.getSyncData(i).data), (EntityPlayerMP) o);
							}
						}
						syncData[i] = sync.getSyncData(i).data;

					}
				}
			}
		}
	}
}
