package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.core.utils.ISyncTile;
import sonar.core.utils.helpers.NBTHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestSync implements IMessage {

	public int xCoord, yCoord, zCoord;

	public PacketRequestSync() {
	}

	public PacketRequestSync(int xCoord, int yCoord, int zCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
	}

	public static class Handler implements IMessageHandler<PacketRequestSync, IMessage> {

		@Override
		public IMessage onMessage(PacketRequestSync message, MessageContext ctx) {
			if (Minecraft.getMinecraft().thePlayer.worldObj != null) {
				TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
				if (tile != null && tile instanceof ISyncTile) {
					NBTTagCompound tag = new NBTTagCompound();
					ISyncTile sync = (ISyncTile) tile;
					sync.writeData(tag, SyncType.SYNC);
					return new PacketTileSync(message.xCoord, message.yCoord, message.zCoord, tag);
				}
			}
			return null;
		}
	}
}