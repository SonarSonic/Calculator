package sonar.calculator.mod.network.packets;

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

import java.util.ArrayList;
import java.util.List;

public class PacketTeleportLinks  extends PacketCoords {

	public List<TeleportLink> links;

    public PacketTeleportLinks() {
    }

	public PacketTeleportLinks(BlockPos pos, List<TeleportLink> links) {
		super(pos);
		this.links = links;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
        links = new ArrayList<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			links.add(i, TeleportLink.readFromBuf(buf));			
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(links.size());
        for (TeleportLink link : links) {
            TeleportLink.writeToBuf(buf, link);
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
