package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.network.PacketTileEntity;
import sonar.core.network.PacketTileEntityHandler;
import sonar.logistics.network.packets.PacketProviders;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCalculatorScreen extends PacketTileEntity {

	public int type, energy;

	public PacketCalculatorScreen() {
	}

	public PacketCalculatorScreen(int x, int y, int z, int type, int energy) {
		super(x, y, z);
		this.type = type;
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.type = buf.readInt();
		this.energy = buf.readInt();
		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);

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
