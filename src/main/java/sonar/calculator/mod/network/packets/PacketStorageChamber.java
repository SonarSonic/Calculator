package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketStorageChamber implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int id,value;

	public PacketStorageChamber() {
	}

	public PacketStorageChamber(int xCoord, int yCoord, int zCoord, int id, int value) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.id=id;
		this.value=value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();	
		
		TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if(tile !=null && tile instanceof TileEntityStorageChamber){
			TileEntityStorageChamber chamber = (TileEntityStorageChamber) tile;
			chamber.stored[buf.readInt()]=buf.readInt();			
		}		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(id);
		buf.writeInt(value);
	}

	public static class Handler implements IMessageHandler<PacketStorageChamber, IMessage> {

		@Override
		public IMessage onMessage(PacketStorageChamber message, MessageContext ctx) {			
			return null;
		}
	}
}
