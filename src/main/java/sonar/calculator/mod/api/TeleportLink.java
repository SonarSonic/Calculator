package sonar.calculator.mod.api;

import io.netty.buffer.ByteBuf;
import sonar.calculator.mod.utils.FluxNetwork;
import cpw.mods.fml.common.network.ByteBufUtils;

public class TeleportLink {
	public String networkName;
	public int networkID, dimension;
	
	public TeleportLink(int networkID, String networkName, int dimension) {
		this.networkID = networkID;
		this.networkName = networkName;
		this.dimension = dimension;
	}
	public static void writeToBuf(ByteBuf buf, TeleportLink network){
		buf.writeInt(network.networkID);
		buf.writeInt(network.dimension);
		ByteBufUtils.writeUTF8String(buf, network.networkName);
	}
	public static TeleportLink readFromBuf(ByteBuf buf){
		int bufNetworkID = buf.readInt();	
		int bufType = buf.readInt();	
		String bufNetwork = ByteBufUtils.readUTF8String(buf);
		
		return new TeleportLink(bufNetworkID, bufNetwork, bufType);
	}
	
}
