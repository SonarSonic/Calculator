package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.TeleportLink;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleportLinks implements IMessage {

	public int xCoord, yCoord, zCoord;
	public List<TeleportLink> links;

	public PacketTeleportLinks() {
	}

	public PacketTeleportLinks(int xCoord, int yCoord, int zCoord, List<TeleportLink> links) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.links = links;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		links = new ArrayList();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			links.add(i, TeleportLink.readFromBuf(buf));
			
		}

		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null && tile instanceof TileEntityTeleporter) {
			TileEntityTeleporter teleporter = (TileEntityTeleporter) tile;
			teleporter.links = links;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(links.size());
		for (int i = 0; i < links.size(); i++) {
			TeleportLink.writeToBuf(buf, links.get(i));
		}
	}

	public static class Handler implements IMessageHandler<PacketTeleportLinks, IMessage> {

		@Override
		public IMessage onMessage(PacketTeleportLinks message, MessageContext ctx) {
			return null;
		}
	}
}
