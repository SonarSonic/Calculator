package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.network.PacketTileEntity;
import sonar.core.network.PacketTileEntityHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketStorageChamber extends PacketTileEntity {

	public int id,value;

	public PacketStorageChamber() {}

	public PacketStorageChamber(int x, int y, int z, int id, int value) {
		super(x,y,z);
		this.id=id;
		this.value=value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.id = buf.readInt();
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(id);
		buf.writeInt(value);
	}

	public static class Handler extends PacketTileEntityHandler<PacketStorageChamber> {

		@Override
		public IMessage processMessage(PacketStorageChamber message, TileEntity target) {
			if(target !=null && target instanceof TileEntityStorageChamber){
				TileEntityStorageChamber chamber = (TileEntityStorageChamber) target;
				chamber.stored[message.id]=message.value;			
			}	
			return null;
		}
	}
}
