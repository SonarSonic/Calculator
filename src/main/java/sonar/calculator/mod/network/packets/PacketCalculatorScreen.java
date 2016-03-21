package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.network.PacketCoords;
import sonar.core.network.PacketTileEntityHandler;

public class PacketCalculatorScreen extends PacketCoords {

	public int type, energy;

	public PacketCalculatorScreen() {
	}

	public PacketCalculatorScreen(BlockPos pos, int type, int energy) {
		super(pos);
		this.type = type;
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.type = buf.readInt();
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(type);
		buf.writeInt(energy);
	}

	public static class Handler extends PacketTileEntityHandler<PacketCalculatorScreen> {

		@Override
		public IMessage processMessage(PacketCalculatorScreen message, TileEntity target) {
			if (target instanceof TileEntityCalculatorScreen) {
				TileEntityCalculatorScreen screen = (TileEntityCalculatorScreen) target;
				if (message.type == 0) {
					screen.latestMax = message.energy;
				} else {
					screen.latestEnergy = message.energy;
				}
			}
			return null;
		}
	}
}
