package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.item.modules.LocatorModule;
import sonar.calculator.mod.network.ChunkHandler;
import sonar.calculator.mod.utils.FluxControllerData;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.core.common.tileentity.TileEntityInventory;

public class TileEntityFluxController extends TileEntityInventory{

	public static String chunkData = "Calculator: Flux Controller";

	public int recieveMode, sendMode;
	public int allowDimensions, playerProtect;
	public int freq, x, y, z,dimension;
	public String playerName;

	public TileEntityFluxController() {
		super.slots = new ItemStack[9];
	}

	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			this.worldObj.perWorldStorage.setData(chunkData + this.freq, new FluxControllerData(this));
			FluxRegistry.addDimension(freq, this.dimension);
		}
	}

	public void removeFromFrequency() {
		if (!this.worldObj.isRemote) {
			this.worldObj.perWorldStorage.setData(chunkData + this.freq, new FluxControllerData((TileEntityFluxController) null, this.freq));
			FluxRegistry.removeDimension(freq,dimension);
		}
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("recieveMode", recieveMode);
		tag.setInteger("sendMode", sendMode);
		tag.setInteger("freq", freq);
		tag.setInteger("XX", x);
		tag.setInteger("YY", y);
		tag.setInteger("ZZ", z);
		tag.setInteger("DIMENSION", dimension);
		tag.setString("playerName", playerName);
		tag.setInteger("allowDimensions", allowDimensions);
		tag.setInteger("playerProtect", playerProtect);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.recieveMode = tag.getInteger("recieveMode");
		this.sendMode = tag.getInteger("sendMode");
		this.freq = tag.getInteger("freq");
		this.x = tag.getInteger("XX");
		this.y = tag.getInteger("YY");
		this.z = tag.getInteger("ZZ");
		this.dimension = tag.getInteger("DIMENSION");
		this.playerName = tag.getString("playerName");
		this.allowDimensions = tag.getInteger("allowDimensions");
		this.playerProtect = tag.getInteger("playerProtect");
	}

	public void onSync(int data, int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			this.freq = data;
			break;
		case SyncType.SPECIAL2:
			this.x = data;
			break;
		case SyncType.SPECIAL3:
			this.y = data;
			break;
		case SyncType.SPECIAL4:
			this.z = data;
			break;
		case SyncType.SPECIAL5:
			this.recieveMode = data;
			break;
		case SyncType.SPECIAL6:
			this.sendMode = data;
			break;
		case SyncType.SPECIAL7:
			this.allowDimensions = data;
			break;
		case SyncType.SPECIAL8:
			this.playerProtect = data;
			break;
		}
	}

	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, freq);
		case SyncType.SPECIAL2:
			return new SyncData(true, x);
		case SyncType.SPECIAL3:
			return new SyncData(true, y);
		case SyncType.SPECIAL4:
			return new SyncData(true, z);
		case SyncType.SPECIAL5:
			return new SyncData(true, recieveMode);
		case SyncType.SPECIAL6:
			return new SyncData(true, sendMode);
		case SyncType.SPECIAL7:
			return new SyncData(true, allowDimensions);
		case SyncType.SPECIAL8:
			return new SyncData(true, playerProtect);
		}
		return new SyncData(false, 0);
	}

	public void onLoaded() {
		if (!this.worldObj.isRemote) {
			this.addToFrequency();

		}
	}

	/**keeps the Flux Controller loaded*/
	public void loadChunks() {
		Ticket ticket = ForgeChunkManager.requestTicket(Calculator.instance, this.worldObj, Type.NORMAL);
		if (ticket != null) {
			ChunkCoordIntPair chunk = new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
			ChunkHandler.saveFluxController(ticket, this);
			ForgeChunkManager.forceChunk(ticket, chunk);
		}
	}
	/**stops the Flux Controller from being loaded*/
	public void removeChunks() {
		Ticket ticket = ForgeChunkManager.requestTicket(Calculator.instance, this.worldObj, Type.NORMAL);
		if (ticket != null) {
			ChunkCoordIntPair chunk = new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
			ForgeChunkManager.unforceChunk(ticket, chunk);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (!this.worldObj.isRemote) {
			this.removeFromFrequency();
		}
	}

	public boolean validPlayer(String playerName) {
		if (this.playerProtect == 0) {
			return true;
		}
		if (playerName.equals(this.playerName)) {
			return true;
		} else {
			for (int i = 0; i < 9; i++) {
				if (slots[i] != null) {
					if (slots[i].getItem() == Calculator.itemLocatorModule) {
						String sharedPlayer = LocatorModule.getPlayer(slots[i]);
						if (sharedPlayer != null) {
							return sharedPlayer.equals(playerName);
						}
					}
				}
			}
		}

		return false;
	}

	public String getPlayer() {
		return this.playerName;
	}

	public void setName(int newName) {
		int oldFreq = freq;
		this.removeFromFrequency();
		this.freq = newName;
		this.addToFrequency();
	}

	public void setPlayer(EntityPlayer player) {
		if (player == null) {
			return;
		}
		this.playerName = player.getGameProfile().getName();
		this.dimension = player.dimension;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (stack == null) {
			return false;
		}
		return stack.getItem() == Calculator.itemLocatorModule;
	}
}
