package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.ISyncTile;
import sonar.core.utils.ISonarSides;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSonarSides implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int side,value;

	public PacketSonarSides() {
	}

	public PacketSonarSides(int xCoord, int yCoord, int zCoord, int side, int value) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.side=side;
		this.value=value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		
		
		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if(tile !=null && tile instanceof ISonarSides){
			ISonarSides sides = (ISonarSides) tile;
			sides.setSide(buf.readInt(), buf.readInt());
			
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(side);
		buf.writeInt(value);
	}

	public static class Handler implements IMessageHandler<PacketSonarSides, IMessage> {

		@Override
		public IMessage onMessage(PacketSonarSides message, MessageContext ctx) {			
			return null;
		}
	}
}
