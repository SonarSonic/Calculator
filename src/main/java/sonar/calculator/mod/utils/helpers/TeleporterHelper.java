package sonar.calculator.mod.utils.helpers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.utils.CalculatorTeleporter;
import sonar.core.api.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.helpers.SonarHelper;

public class TeleporterHelper {

	public static void travelToDimension(List<EntityPlayer> players, TileEntityTeleporter tile) {
		for (EntityPlayer entity : players) {
			int currentDimension = entity.worldObj.provider.getDimensionId();
			BlockCoords coords = tile.getCoords();
			if (coords.getDimension() != currentDimension) {
				if (!tile.getWorld().isRemote && !entity.isDead) {

					EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
					WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(coords.getDimension());
					MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, tile.getCoords().getDimension(), new CalculatorTeleporter(worldServer, coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5));

					if (currentDimension == 1) {
						((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
						worldServer.spawnEntityInWorld(entity);
						worldServer.updateEntityWithOptionalForce(entity, false);
					} else {
						((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
					}

				}
			} else {
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(entity.worldObj.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
			}
			tile.coolDown = true;
			tile.coolDownTicks = 100;
		}
	}

	public static boolean canTeleport(TileEntityTeleporter target, TileEntityTeleporter current) {
		if (!target.getCoords().equals(current.getCoords()) && target.canTeleportPlayer()) {
			if ((target.password == null || target.password == "") || current.linkPassword.equals(target.password)) {
				return true;
			}
		}		
		return false;
	}
}
