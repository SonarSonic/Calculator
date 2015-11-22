package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.ITeleport;
import sonar.calculator.mod.api.ITextField;
import sonar.calculator.mod.api.TeleportLink;
import sonar.calculator.mod.client.gui.misc.GuiFlux;
import sonar.calculator.mod.utils.CalculatorTeleporter;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.calculator.mod.utils.helpers.TeleporterHelper;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.network.PacketSonarSides;
import sonar.core.network.PacketTileSync;
import sonar.core.network.SonarPackets;
import sonar.core.utils.IMachineButtons;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;

public class TileEntityTeleporter extends TileEntitySonar implements ITeleport, IMachineButtons, ITextField {

	public int teleporterID, accessSetting;
	public String name = "LINK NAME";
	public String destinationName = "DESTINATION";
	public String password = "";
	public boolean coolDown, passwordMatch;
	public int coolDownTicks = 0;

	public int linkID;
	public String linkPassword = "";

	/** client only list */
	public List<TeleportLink> links;

	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isRemote) {
			return;
		}
		if (coolDownTicks != 0) {
			coolDownTicks--;
		} else {
			if (!coolDown) {
				if (this.teleporterID == 0) {
					return;
				}
				startTeleportation();

			} else {
				List<EntityPlayer> players = this.getPlayerList();
				if (players == null || players.size() == 0) {
					coolDown = false;
				}
			}
		}
	}

	public void startTeleportation() {
		List<ITeleport> links = TeleporterRegistry.getTeleporters();
		if (links != null && links.size() != 1) {
			for (ITeleport teleport : links) {
				TileEntityTeleporter tile = TeleporterRegistry.getTile(teleport);
				if (tile == null) {
					TeleporterRegistry.removeTeleporter(teleport);
					return;
				}
				if (tile.teleporterID != 0 && tile.teleporterID == this.linkID) {
					if (TeleporterHelper.canTeleport(tile, this) && canTeleportPlayer() && tile.canTeleportPlayer()) {
						this.passwordMatch = true;
						TeleporterHelper.travelToDimension(this.getPlayerList(), tile);
						updateDimensionName(tile.name);
					} else {
						this.passwordMatch = false;
					}
				} else if (tile.teleporterID == 0) {
					tile.resetFrequency();
				}

			}
		} else {
			this.passwordMatch = false;
		}
	}

	public void updateDimensionName(String name) {
		if (!destinationName.equals(name)) {
			destinationName = name;
			NBTTagCompound syncData = new NBTTagCompound();
			writeData(syncData, NBTHelper.SyncType.SYNC);
			SonarPackets.network.sendToAllAround(new PacketTileSync(xCoord, yCoord, zCoord, syncData), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));
		}
	}

	public boolean canTeleportPlayer() {
		boolean flag = true;
		for (int i = 1; i < 3; i++) {
			Block block = worldObj.getBlock(xCoord, yCoord - i, zCoord);
			if (!(block == Blocks.air || block == null)) {
				flag = false;
			}
		}
		ForgeDirection[] dirs = new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
		int stable = 0;
		for (int i = 0; i < dirs.length; i++) {
			ForgeDirection dir = dirs[i];
			int blocks = 0;
			for (int j = 0; j < 3; j++) {
				if (worldObj.getBlock(xCoord + dir.offsetX, yCoord - j, zCoord + dir.offsetZ) == Calculator.stablestoneBlock) {
					blocks++;
				}
			}
			if (blocks == 3) {
				blocks = 0;
				stable++;
			}
		}

		return stable >= 3 && flag && yCoord - 2 > 0;
	}

	public List<EntityPlayer> getPlayerList() {
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 2, zCoord - 1, xCoord + 1, yCoord - 1, zCoord + 1);
		List<EntityPlayer> players = this.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, aabb, null);
		return players;
	}

	public void resetFrequency() {
		if (!this.worldObj.isRemote) {
			this.removeFromFrequency();
			this.addToFrequency();
		}
	}

	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			if (this.teleporterID == 0) {
				teleporterID = TeleporterRegistry.nextID();
			}
			TeleporterRegistry.addTeleporter(this);
		}
	}

	public void removeFromFrequency() {
		if (!this.worldObj.isRemote) {
			TeleporterRegistry.removeTeleporter(this);
		}
	}

	public void setFrequency(int freq) {
		removeFromFrequency();
		this.teleporterID = freq;
		addToFrequency();
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.accessSetting = nbt.getInteger("accessSetting");
			this.teleporterID = nbt.getInteger("freq");
			this.linkID = nbt.getInteger("linkID");
			this.name = nbt.getString("name");
			this.destinationName = nbt.getString("destinationName");
			this.linkPassword = nbt.getString("linkPassword");
			this.password = nbt.getString("password");
			this.coolDown = nbt.getBoolean("coolDown");
			this.passwordMatch = nbt.getBoolean("passwordMatch");
			this.coolDownTicks = nbt.getInteger("coolDownTicks");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("accessSetting", this.accessSetting);
			nbt.setInteger("freq", this.teleporterID);
			nbt.setInteger("linkID", this.linkID);
			nbt.setString("name", this.name);
			nbt.setString("destinationName", this.destinationName);
			nbt.setString("linkPassword", this.linkPassword);
			nbt.setString("password", this.password);
			nbt.setBoolean("coolDown", this.coolDown);
			nbt.setBoolean("passwordMatch", this.passwordMatch);
			nbt.setInteger("coolDownTicks", this.coolDownTicks);
		}
	}

	public void onChunkUnload() {
		this.removeFromFrequency();
	}

	public void onLoaded() {
		if (!this.worldObj.isRemote) {
			this.addToFrequency();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (!this.worldObj.isRemote) {
			this.removeFromFrequency();
		}
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add("Link Name: " + name);
		if (!destinationName.equals("DESTINATION")) {
			currenttip.add("Destination: " + destinationName);
		}
		return currenttip;
	}

	@Override
	public int teleporterID() {
		return teleporterID;
	}

	@Override
	public int accessSetting() {
		return accessSetting;
	}

	@Override
	public int xCoord() {
		return xCoord;
	}

	@Override
	public int yCoord() {
		return yCoord;
	}

	@Override
	public int zCoord() {
		return zCoord;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int dimension() {
		return this.worldObj.provider.dimensionId;
	}

	@Override
	public void buttonPress(int buttonID, int value) {
		this.removeFromFrequency();
		if (buttonID == 0) {
			if (this.accessSetting == 0) {
				this.accessSetting = 1;
			} else {
				this.accessSetting = 0;
			}
		}
		if (buttonID == 1) {
			this.linkID = value;
		}
		this.addToFrequency();
	}

	@Override
	public void textTyped(String string, int id) {
		if (id == 1)
			this.name = string;
		if (id == 2)
			this.password = string;
		if (id == 3)
			this.linkPassword = string;

	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

}
