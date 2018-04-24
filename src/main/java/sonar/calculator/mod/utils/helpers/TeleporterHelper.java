package sonar.calculator.mod.utils.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.utils.CalculatorTeleporter;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.helpers.SonarHelper;

import java.util.List;

public class TeleporterHelper {

	public static void travelToDimension(List<EntityPlayer> players, TileEntityTeleporter tile) {
		for (EntityPlayer entity : players) {
			int currentDimension = entity.world.provider.getDimension();
			BlockCoords coords = tile.getCoords();
			if (coords.getDimension() != currentDimension) {
				if (!tile.getWorld().isRemote && !entity.isDead) {

					EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    WorldServer worldServer = server.getWorld(coords.getDimension());
					server.getPlayerList().transferPlayerToDimension(entityPlayerMP, tile.getCoords().getDimension(), new CalculatorTeleporter(worldServer, coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5));
					if (currentDimension == 1) {
						((EntityPlayerMP) entity).connection.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
						worldServer.spawnEntity(entity);
						worldServer.updateEntityWithOptionalForce(entity, false);
					} else {
						((EntityPlayerMP) entity).connection.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(worldServer.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
					}
				}
			} else {
				((EntityPlayerMP) entity).connection.setPlayerLocation(coords.getX() + 0.5, coords.getY() - 2, coords.getZ() + 0.5, SonarHelper.getAngleFromMeta(entity.world.getBlockState(coords.getBlockPos()).getValue(SonarBlock.FACING).getIndex()), 0);
			}
			tile.coolDown = true;
			tile.coolDownTicks = 100;
		}
	}

	public static boolean canTeleport(TileEntityTeleporter target, TileEntityTeleporter current) {
		if (!target.getCoords().equals(current.getCoords()) && target.canTeleportPlayer()) {
            return target.password.getObject() == null || target.password.getObject().equals("") || current.linkPassword.getObject().equals(target.password.getObject());
		}
		return false;
	}
}
