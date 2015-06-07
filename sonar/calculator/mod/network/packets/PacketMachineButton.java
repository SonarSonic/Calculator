package sonar.calculator.mod.network.packets;

import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
			TileEntity te =world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if(te!=null){		
				if (message.id == 0) {
					if (te != null && te instanceof IUpgradeCircuits) {
						IUpgradeCircuits circuits = (IUpgradeCircuits) te;
						for (int i = 0; i < 3; i++) {
							if (circuits.getUpgrades(i) != 0&& UpgradeCircuit.getItem(i) != null) {
								ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(message.xCoord, message.yCoord, message.zCoord));
								EntityItem upgrade = new EntityItem(world,
										te.xCoord + dir.offsetX, te.yCoord + 0.5,
										te.zCoord + dir.offsetZ, new ItemStack(UpgradeCircuit.getItem(i),circuits.getUpgrades(i)));
								circuits.incrementUpgrades(i,-circuits.getUpgrades(i));
								world.spawnEntityInWorld(upgrade);
							}
						}
					}
				}else if (message.id == 2) {
					if (te != null && te instanceof IPausable) {
						IPausable pause = (IPausable) te;
						pause.onPause();
					}
				}
			}
			
			
			return null;
		}

	}
}
