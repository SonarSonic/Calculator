package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.utils.ISonarSides;
import sonar.core.utils.ISyncTile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCalculatorScreen implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int type, energy;

	public PacketCalculatorScreen() {
	}

	public PacketCalculatorScreen(int xCoord, int yCoord, int zCoord, int type, int energy) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.type = type;
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();

		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null && tile instanceof TileEntityCalculatorScreen) {
			TileEntityCalculatorScreen screen = (TileEntityCalculatorScreen) tile;
			if (buf.readInt() == 0) {
				screen.latestMax = buf.readInt();
			} else {
				screen.latestEnergy = buf.readInt();
			}
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(type);
		buf.writeInt(energy);
	}

	public static class Handler implements IMessageHandler<PacketCalculatorScreen, IMessage> {

		@Override
		public IMessage onMessage(PacketCalculatorScreen message, MessageContext ctx) {
			return null;
		}
	}
}
