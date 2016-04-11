package sonar.calculator.mod.common.tileentity.misc;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.ITeleport;
import sonar.calculator.mod.api.machines.TeleportLink;
import sonar.calculator.mod.client.gui.misc.GuiTeleporter;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.calculator.mod.utils.helpers.TeleporterHelper;
import sonar.core.SonarCore;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.ContainerEmpty;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

public class TileEntityTeleporter extends TileEntitySonar implements ITeleport, IByteBufTile, ITextField, IGuiTile {

	public int teleporterID;
	public String name = "LINK NAME";
	public String destinationName = "DESTINATION";
	public String password = "";
	public boolean coolDown, passwordMatch;
	public int coolDownTicks = 0;

	public int linkID;
	public String linkPassword = "";

	/** client only list */
	public List<TeleportLink> links;

	public void update() {
		super.update();
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
			SonarCore.sendFullSyncAround(this, 64);
		}
	}

	public boolean canTeleportPlayer() {
		boolean flag = true;
		for (int i = 1; i < 3; i++) {
			Block block = worldObj.getBlockState(pos.offset(EnumFacing.DOWN, i)).getBlock();
			if (!(block == Blocks.air || block == null)) {
				flag = false;
			}
		}
		EnumFacing[] dirs = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };
		int stable = 0;
		for (int i = 0; i < dirs.length; i++) {
			EnumFacing dir = dirs[i];
			int blocks = 0;
			for (int j = 0; j < 3; j++) {
				if (worldObj.getBlockState(pos.add(dir.getFrontOffsetX(), -j, dir.getFrontOffsetZ())).getBlock() == Calculator.stableStone) {
					blocks++;
				}
			}
			if (blocks == 3) {
				blocks = 0;
				stable++;
			}
		}

		return stable >= 3 && flag && pos.getY() - 2 > 0;
	}

	public List<EntityPlayer> getPlayerList() {
		AxisAlignedBB aabb = AxisAlignedBB.fromBounds(pos.getX() - 1, pos.getY() - 2, pos.getZ() - 1, pos.getX() + 1, pos.getY() - 1, pos.getZ() + 1);
		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb, null);
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
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
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
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
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
	public String name() {
		return name;
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

	@Override
	public void writePacket(ByteBuf buf, int id) {
		if (id == 0) {
			buf.writeInt(linkID);
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		this.removeFromFrequency();
		if (id == 0) {
			this.linkID = buf.readInt();
		}
		this.addToFrequency();
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerEmpty(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiTeleporter(player.inventory, this);
	}

}
