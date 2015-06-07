package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.ISyncTile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTileSync implements IMessage {

	public int xCoord, yCoord, zCoord, id, sync;

	public PacketTileSync() {
	}

	public PacketTileSync(int xCoord, int yCoord, int zCoord, int id, int sync) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.id = id;
		this.sync = sync;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.id =buf.readInt();
		this.sync = buf.readInt();
		
		if (Minecraft.getMinecraft().thePlayer.worldObj != null) {
			TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if(tile !=null && tile instanceof ISyncTile){
				ISyncTile sync = (ISyncTile) tile;
				sync.onSync(this.sync, id);
			}			
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(id);
		buf.writeInt(sync);
	}

	public static class Handler implements IMessageHandler<PacketTileSync, IMessage> {

		@Override
		public IMessage onMessage(PacketTileSync message, MessageContext ctx) {			
			return null;
		}
	}
}
