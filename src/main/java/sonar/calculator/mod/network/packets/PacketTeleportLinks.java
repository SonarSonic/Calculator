package sonar.calculator.mod.network.packets;

import java.util.List;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sonar.calculator.mod.api.machines.TeleportLink;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.core.network.PacketCoords;
import sonar.core.network.PacketTileEntityHandler;

public class PacketTeleportLinks  extends PacketCoords {

	public List<TeleportLink> links;

	public PacketTeleportLinks() {}

	public PacketTeleportLinks(BlockPos pos, List<TeleportLink> links) {
		super(pos);
		this.links = links;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		links = Lists.newArrayList();
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
		public IMessage processMessage(EntityPlayer player, MessageContext ctx, PacketTeleportLinks message, TileEntity target) {
			if (target instanceof TileEntityTeleporter) {
				TileEntityTeleporter teleporter = (TileEntityTeleporter) target;
				teleporter.links = message.links;
			}
			return null;
		}
	}
}
