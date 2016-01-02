package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.utils.FluxRegistry;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFluxPoint implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int id;
	public String string;

	public PacketFluxPoint() {
	}

	public PacketFluxPoint(String string, int xCoord, int yCoord, int zCoord, int id) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.string = string;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.string = ByteBufUtils.readUTF8String(buf);
		this.id = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		ByteBufUtils.writeUTF8String(buf, string);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketFluxPoint, IMessage> {

		@Override
		public IMessage onMessage(PacketFluxPoint message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if (te == null) {
				return null;
			}

			if (te instanceof TileEntityFlux) {
				TileEntityFlux flux = (TileEntityFlux) te;
				TileEntityFluxPoint point = null;
				if (message.id == 1 || message.id == 2) {
					point = (TileEntityFluxPoint) te;
				}
				switch (message.id) {
				case 0:
					flux.setName(message.string);
					return null;
				case 1:
					point.priority = Integer.valueOf(message.string);
					return null;
				case 2:
					point.maxTransfer = Integer.valueOf(message.string);
					return null;
				case 4:
					flux.rename(message.string);
					return null;
				}
			}

			if (te instanceof TileEntityFluxController) {
				TileEntityFluxController flux = (TileEntityFluxController) te;
				switch (message.id) {
				case 0:
					flux.setName(message.string);
					return null;
				case 4:
					flux.rename(message.string);
				}
			}

			if (message.id == 3) {

				if (te instanceof TileEntityFluxController) {
					TileEntityFluxController flux = (TileEntityFluxController) te;
					return new PacketFluxNetworkList(message.xCoord, message.yCoord, message.zCoord, FluxRegistry.getAvailableNetworks(message.string, flux));
				} else {
					return new PacketFluxNetworkList(message.xCoord, message.yCoord, message.zCoord, FluxRegistry.getAvailableNetworks(message.string, null));
				}
			}
			return null;
		}

	}
}
