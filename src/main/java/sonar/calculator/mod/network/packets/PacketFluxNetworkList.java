package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.network.PacketCoords;
import sonar.core.network.PacketTileEntityHandler;

public class PacketFluxNetworkList extends PacketCoords {

	public List<FluxNetwork> networks;

	public PacketFluxNetworkList() {
	}

	public PacketFluxNetworkList(BlockPos pos, List<FluxNetwork> networks) {
		super(pos);
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
