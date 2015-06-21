package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketMachineButton implements IMessage {

	public int xCoord, yCoord, zCoord, id;

	public PacketMachineButton() {
	}

	public PacketMachineButton(int id, int xCoord, int yCoord, int zCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.id = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketMachineButton, IMessage> {

		@Override
		public IMessage onMessage(PacketMachineButton message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if (te != null) {
				if (message.id == 0) {
					if (te != null && te instanceof IUpgradeCircuits) {
						IUpgradeCircuits circuits = (IUpgradeCircuits) te;
						for (int i = 0; i < 3; i++) {
							if (circuits.getUpgrades(i) != 0 && UpgradeCircuit.getItem(i) != null) {
								ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(message.xCoord, message.yCoord, message.zCoord));
								EntityItem upgrade = new EntityItem(world, te.xCoord + dir.offsetX, te.yCoord + 0.5, te.zCoord + dir.offsetZ, new ItemStack(UpgradeCircuit.getItem(i),
										circuits.getUpgrades(i)));
								circuits.incrementUpgrades(i, -circuits.getUpgrades(i));
								world.spawnEntityInWorld(upgrade);
							}
						}
					}
				} else if (message.id == 2) {
					if (te != null && te instanceof IPausable) {
						IPausable pause = (IPausable) te;
						pause.onPause();
					}
				} else if (message.id == 3) {
					if (te != null && te instanceof TileEntityFluxController) {
						TileEntityFluxController controller = (TileEntityFluxController) te;
						if (controller.recieveMode < 4)
							controller.recieveMode++;
						else
							controller.recieveMode = 0;
					}
				} else if (message.id == 4) {
					if (te != null && te instanceof TileEntityFluxController) {
						TileEntityFluxController controller = (TileEntityFluxController) te;
						if (controller.sendMode < 2)
							controller.sendMode++;
						else
							controller.sendMode = 0;
					}
				} else if (message.id == 5) {
					if (te != null && te instanceof TileEntityFluxController) {
						TileEntityFluxController controller = (TileEntityFluxController) te;
						if(controller.allowDimensions==0){
							controller.allowDimensions=1;
						}else{
							controller.allowDimensions=0;
						}
					}
				} else if (message.id == 6) {
					if (te != null && te instanceof TileEntityFluxController) {
						TileEntityFluxController controller = (TileEntityFluxController) te;
						if(controller.playerProtect==0){
							controller.playerProtect=1;
						}else{
							controller.playerProtect=0;
						}
					}
				}
			}
			return null;
		}

	}
}
