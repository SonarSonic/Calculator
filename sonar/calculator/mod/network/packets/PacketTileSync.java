package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncType;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTileSync implements IMessage {

	public int xCoord, yCoord, zCoord, id;
	public Object sync;
	public int type;

	public PacketTileSync() {
	}

	public PacketTileSync(int xCoord, int yCoord, int zCoord, int id, Object data) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.id = id;
		this.sync = data;
		this.type = getType(data);
	}

	public int getType(Object obj){
		if(obj!=null){
			if(obj instanceof Integer){
				return 0;
			}
			else if(obj instanceof NBTTagCompound){
				return 1;
			}
			else if(obj instanceof String){
				return 2;
			}
			else if(obj instanceof Boolean){
				return 3;
			}
			else if(obj instanceof ItemStack){
				return 4;
			}
			else if(obj instanceof Byte){
				return 5;
			}
			else if(obj instanceof Float){
				return 6;
			}
		}
		return 0;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.id =buf.readInt();
		this.type =buf.readInt();

		switch(type){
		case 1: this.sync = ByteBufUtils.readTag(buf);break;
		case 2: this.sync = ByteBufUtils.readUTF8String(buf);break;
		case 3: this.sync = buf.readBoolean();break;
		case 4: this.sync = ByteBufUtils.readItemStack(buf);break;
		case 5: this.sync = buf.readByte();break;
		case 6: this.sync = buf.readFloat();break;
		default: this.sync = buf.readInt();break;
		}
		if (Minecraft.getMinecraft().thePlayer.worldObj != null) {
			TileEntity tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if(tile !=null && tile instanceof ISyncTile){
				
				ISyncTile sync = (ISyncTile) tile;
				sync.onSync(this.sync, id);
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(id);
		buf.writeInt(type);		
		switch(type){
		case 1: ByteBufUtils.writeTag(buf, (NBTTagCompound) sync);break;
		case 2: ByteBufUtils.writeUTF8String(buf, (String) sync);break;
		case 3: buf.writeBoolean((Boolean) sync);break;
		case 4: ByteBufUtils.writeItemStack(buf, (ItemStack)sync);break;
		case 5: buf.writeByte((Byte) sync);break;
		case 6: buf.writeFloat((Float) sync);break;
		default: buf.writeInt((Integer) sync);break;
		}
	}

	public static class Handler implements IMessageHandler<PacketTileSync, IMessage> {

		@Override
		public IMessage onMessage(PacketTileSync message, MessageContext ctx) {			
			return null;
		}
	}
}
