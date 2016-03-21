package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.network.PacketCoords;
import sonar.core.network.PacketTileEntityHandler;

public class PacketStorageChamber extends PacketCoords {

	public int id,value;

	public PacketStorageChamber() {}

	public PacketStorageChamber(BlockPos pos, int id, int value) {
		super(pos);
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
				((TileEntityStorageChamber.StorageChamberInventory)chamber.inv).stored[message.id]=message.value;			
			}	
			return null;
		}
	}
}
