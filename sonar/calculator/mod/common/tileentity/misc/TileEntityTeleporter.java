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
import sonar.calculator.mod.api.ITeleport;
import sonar.calculator.mod.api.ITextField;
import sonar.calculator.mod.api.TeleportLink;
import sonar.calculator.mod.client.gui.misc.GuiFlux;
import sonar.calculator.mod.utils.CalculatorTeleporter;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.TeleporterRegistry;
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
	public boolean coolDown;

	public int linkID;
	public String linkPassword = "";

	/** client only list */
	public List<TeleportLink> links;

	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isRemote) {
			return;
		}
		if (!coolDown) {
			if (this.teleporterID == 0) {
				return;
			}
			List<ITeleport> links = TeleporterRegistry.getTeleporters();
			if (links != null && links.size() != 1) {
				for (ITeleport teleport : links) {

					TileEntityTeleporter tile = TeleporterRegistry.getTile(teleport);
					if (tile == null) {
						TeleporterRegistry.removeTeleporter(teleport);
						return;
					}
					if (tile.teleporterID != 0 && tile.teleporterID == this.linkID) {
						if ((tile.xCoord != this.xCoord || tile.yCoord != this.yCoord || tile.zCoord != this.zCoord) && tile.canTeleportPlayer()) {
							if ((tile.password == null || tile.password == "") || this.linkPassword.equals(tile.password)) {
								teleportPlayers(tile);
								if (!destinationName.equals(tile.name)) {
									destinationName = tile.name;
									NBTTagCompound syncData = new NBTTagCompound();
									writeData(syncData, NBTHelper.SyncType.SYNC);
									SonarPackets.network.sendToAllAround(new PacketTileSync(tile.xCoord, tile.yCoord, tile.zCoord, syncData), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

								}
							}
						}
					} else if (tile.teleporterID == 0) {
						tile.resetFrequency();
					}

				}
			}

		} else {
			List<EntityPlayer> players = this.getPlayerList();
			if (players == null || players.size() == 0) {
				coolDown = false;
			}
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

		return flag && yCoord - 2 > 0;
	}

	public List<EntityPlayer> getPlayerList() {
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 2, zCoord - 1, xCoord + 1, yCoord - 1, zCoord + 1);
		List<EntityPlayer> players = this.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, aabb, null);
		return players;
	}

	public void teleportPlayers(TileEntityTeleporter tile) {
		for (EntityPlayer entity : getPlayerList()) {
			if (tile.dimension() != this.dimension()) {
				travelToDimension(tile.dimension(), entity, tile);
			} else {
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
			}
			tile.coolDown = true;
		}
	}

	public void travelToDimension(int dimension, EntityPlayer entity, TileEntityTeleporter tile) {
		if (!this.worldObj.isRemote && !entity.isDead) {

			int currentDimension = entity.worldObj.provider.dimensionId;
			EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
			WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(dimension);
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dimension, new CalculatorTeleporter(worldServer, tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5));

			if (currentDimension == 1) {
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
				worldServer.spawnEntityInWorld(entity);
				worldServer.updateEntityWithOptionalForce(entity, false);
			} else {
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
			}

		}
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
