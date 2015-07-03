package sonar.calculator.mod.network;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;

public class ChunkHandler implements LoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		for(int i =0; i<tickets.size(); i++){
			if(tickets.get(i)!=null){
				Ticket ticket = tickets.get(i);
				if(ticket.getModData().getInteger("ID")==1){					
					if(!readFluxController(ticket,world)){
						//ForgeChunkManager.forceChunk(ticket, getChunk(ticket));
						ForgeChunkManager.releaseTicket(ticket);
					}					
				}
			}
		}
	}

	public ChunkCoordIntPair getChunk(Ticket ticket){
		NBTTagCompound data = ticket.getModData();
		return new ChunkCoordIntPair(data.getInteger("X") >> 4, data.getInteger("Z") >> 4);
	
	}
	public static boolean readFluxController(Ticket ticket, World world){
		NBTTagCompound tag = ticket.getModData();
		TileEntity target = world.getTileEntity(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
		if(target!=null && target instanceof TileEntityFluxController){
			return true;
		}
		return false;
	}
	public static void saveFluxController(Ticket ticket, TileEntityFluxController controller){
		ticket.getModData().setInteger("ID", 1);
		ticket.getModData().setInteger("X", controller.xCoord);
		ticket.getModData().setInteger("Y", controller.yCoord);
		ticket.getModData().setInteger("Z", controller.zCoord);
	}

}
