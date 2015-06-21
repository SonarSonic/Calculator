package sonar.calculator.mod.api;

import io.netty.buffer.ByteBuf;

/** used with Tile Entities which requiring syncing with the client */
public interface ISyncTile {

	/**client only method for syncing data
	 * @param data new data value from server
	 * @param id SyncType number*/
	public void onSync(Object data, int id);

	/** gets the SyncData for the given SyncType data values
	 * @param id SyncType number*/
	public SyncData getSyncData(int id);

}
