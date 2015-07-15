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
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.core.utils.IMachineButtons;
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
			if (te == null) {
				return null;
			}
			if (te instanceof IMachineButtons) {
				((IMachineButtons) te).buttonPress(message.id);
			}
		
			return null;
		}

	}
}
