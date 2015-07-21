package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.core.utils.ISyncTile;
import sonar.core.utils.helpers.NBTHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTileSync implements IMessage {

	public int xCoord, yCoord, zCoord;
	public NBTTagCompound tag;

	public PacketTileSync() {
	}

	public PacketTileSync(int xCoord, int yCoord, int zCoord, NBTTagCompound tag) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.tag = ByteBufUtils.readTag(buf);
		if (Minecraft.getMinecraft().thePlayer.worldObj != null) {
			TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if (tile != null && tile instanceof ISyncTile) {
				ISyncTile sync = (ISyncTile) tile;
				sync.readData(this.tag, NBTHelper.SyncType.SYNC);
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler implements IMessageHandler<PacketTileSync, IMessage> {

		@Override
		public IMessage onMessage(PacketTileSync message, MessageContext ctx) {
			return null;
		}
	}
}