package sonar.calculator.mod.utils;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;

public class FluxNetwork {
	
	public String networkName;
	public int networkID, type;
	
	public FluxNetwork(int networkID, String networkName, int type) {
		this.networkID = networkID;
		this.networkName = networkName;
		this.type = type;
	}
	public static void writeToBuf(ByteBuf buf, FluxNetwork network){
		buf.writeInt(network.networkID);
		buf.writeInt(network.type);
		ByteBufUtils.writeUTF8String(buf, network.networkName);
	}
	public static FluxNetwork readFromBuf(ByteBuf buf){
		int bufNetworkID = buf.readInt();	
		int bufType = buf.readInt();	
		String bufNetwork = ByteBufUtils.readUTF8String(buf);
		
		return new FluxNetwork(bufNetworkID, bufNetwork, bufType);
	}
}
