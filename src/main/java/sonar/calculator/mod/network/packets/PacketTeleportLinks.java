package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.machines.TeleportLink;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.core.network.PacketCoords;
import sonar.core.network.PacketTileEntityHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleportLinks  extends PacketCoords {

	public List<TeleportLink> links;

	public PacketTeleportLinks() {}

	public PacketTeleportLinks(int x, int y, int z, List<TeleportLink> links) {
		super(x,y,z);
		this.links = links;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		links = new ArrayList();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			links.add(i, TeleportLink.readFromBuf(buf));			
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(links.size());
		for (int i = 0; i < links.size(); i++) {
			TeleportLink.writeToBuf(buf, links.get(i));
		}
	}

	public static class Handler extends PacketTileEntityHandler<PacketTeleportLinks> {

		@Override
		public IMessage processMessage(PacketTeleportLinks message, TileEntity target) {
			if (target instanceof TileEntityTeleporter) {
				TileEntityTeleporter teleporter = (TileEntityTeleporter) target;
				teleporter.links = message.links;
			}
			return null;
		}
	}
}
