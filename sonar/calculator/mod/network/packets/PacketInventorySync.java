package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketInventorySync implements IMessage {

	public InventoryPlayer playerInv;
	
	public PacketInventorySync() {
	}

	public PacketInventorySync(InventoryPlayer playerInv) {
		this.playerInv=playerInv;
	}

	@Override
	public void fromBytes(ByteBuf buf) {	
		NBTTagCompound compound = ByteBufUtils.readTag(buf);	
		
		Minecraft.getMinecraft().thePlayer.inventory.readFromNBT(compound.getTagList("player", 10));
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag =new NBTTagCompound();
		NBTTagList playerTag = new NBTTagList(); 
		playerInv.writeToNBT(playerTag);
		tag.setTag("player", playerTag);
		ByteBufUtils.writeTag(buf, tag);
		
	}

	public static class Handler implements IMessageHandler<PacketInventorySync, IMessage> {

		@Override
		public IMessage onMessage(PacketInventorySync message, MessageContext ctx) {			
			return null;
		}
	}
}
