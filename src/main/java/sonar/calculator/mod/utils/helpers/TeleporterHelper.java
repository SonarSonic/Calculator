package sonar.calculator.mod.utils.helpers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.utils.CalculatorTeleporter;
import sonar.core.helpers.SonarHelper;

public class TeleporterHelper {

	public static void travelToDimension(List<EntityPlayer> players, TileEntityTeleporter tile) {
		for (EntityPlayer entity : players) {
			int currentDimension = entity.worldObj.provider.dimensionId;
			if (tile.dimension() != currentDimension) {
				if (!tile.getWorldObj().isRemote && !entity.isDead) {

					EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
					WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(tile.dimension());
					MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, tile.dimension(), new CalculatorTeleporter(worldServer, tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5));

					if (currentDimension == 1) {
						((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
						worldServer.spawnEntityInWorld(entity);
						worldServer.updateEntityWithOptionalForce(entity, false);
					} else {
						((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
					}

				}
			} else {
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(tile.xCoord + 0.5, tile.yCoord - 2, tile.zCoord + 0.5, SonarHelper.getAngleFromMeta(tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)), 0);
			}
			tile.coolDown = true;
			tile.coolDownTicks = 100;
		}
	}

	public static boolean canTeleport(TileEntityTeleporter target, TileEntityTeleporter current) {
		if ((target.xCoord != current.xCoord || target.yCoord != current.yCoord || target.zCoord != current.zCoord) && target.canTeleportPlayer()) {
			if ((target.password == null || target.password == "") || current.linkPassword.equals(target.password)) {
				return true;
			}
		}		
		return false;
	}
}
