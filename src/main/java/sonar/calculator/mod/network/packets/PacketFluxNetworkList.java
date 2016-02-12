package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.network.PacketTileEntity;
import sonar.core.network.PacketTileEntityHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFluxNetworkList extends PacketTileEntity {

	public List<FluxNetwork> networks;

	public PacketFluxNetworkList() {
	}

	public PacketFluxNetworkList(int x, int y, int z, List<FluxNetwork> networks) {
		super(x, y, z);
		this.networks = networks;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		networks = new ArrayList();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			networks.add(i, FluxNetwork.readFromBuf(buf));
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(networks.size());
		for (int i = 0; i < networks.size(); i++) {
			FluxNetwork.writeToBuf(buf, networks.get(i));
		}
	}

	public static class Handler extends PacketTileEntityHandler<PacketFluxNetworkList> {

		@Override
		public IMessage processMessage(PacketFluxNetworkList message, TileEntity target) {
			if (target != null && target instanceof TileEntityFlux) {
				TileEntityFlux flux = (TileEntityFlux) target;
				flux.networks = message.networks;
			} else if (target != null && target instanceof TileEntityFluxController) {
				TileEntityFluxController flux = (TileEntityFluxController) target;
				flux.networks = message.networks;
			}
			return null;
		}
	}
}
