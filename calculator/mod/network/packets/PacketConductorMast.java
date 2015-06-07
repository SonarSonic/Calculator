package sonar.calculator.mod.network.packets;

import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketConductorMast implements IMessage {

	public int usage;
	public int xCoord,yCoord,zCoord;
	
	public PacketConductorMast(){}
	
	public PacketConductorMast(int usage, int xCoord, int yCoord, int zCoord){
		this.usage=usage;
		this.xCoord=xCoord;
		this.yCoord=yCoord;
		this.zCoord=zCoord;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord=buf.readInt();
		this.yCoord=buf.readInt();
		this.zCoord=buf.readInt();
		this.usage=buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(usage);
	}
	public static class Handler implements IMessageHandler<PacketConductorMast, IMessage> {

		@Override
		public IMessage onMessage(PacketConductorMast message, MessageContext ctx) {	
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te =world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if(te!=null && te instanceof TileEntityConductorMast){			
				TileEntityConductorMast mast = (TileEntityConductorMast) te;
				mast.onPacket(message.usage);
			}
			
			return null;
		}

	}
}
