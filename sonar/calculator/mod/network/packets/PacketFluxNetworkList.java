package sonar.calculator.mod.network.packets;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.FluxNetwork;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.core.utils.ISonarSides;
import sonar.core.utils.ISyncTile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFluxNetworkList implements IMessage {

	public int xCoord, yCoord, zCoord;
	public List<FluxNetwork> networks;

	public PacketFluxNetworkList() {
	}

	public PacketFluxNetworkList(int xCoord, int yCoord, int zCoord, List<FluxNetwork> networks) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.networks = networks;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		networks = new ArrayList();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			networks.add(i, FluxNetwork.readFromBuf(buf));			
		}

		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null && tile instanceof TileEntityFlux) {
			TileEntityFlux flux = (TileEntityFlux) tile;
			flux.networks = networks;
		} else if (tile != null && tile instanceof TileEntityFluxController) {
			TileEntityFluxController flux = (TileEntityFluxController) tile;
			flux.networks = networks;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(networks.size());		
		for (int i = 0; i < networks.size(); i++) {
			FluxNetwork.writeToBuf(buf, networks.get(i));
		}
	}

	public static class Handler implements IMessageHandler<PacketFluxNetworkList, IMessage> {

		@Override
		public IMessage onMessage(PacketFluxNetworkList message, MessageContext ctx) {
			return null;
		}
	}
}
