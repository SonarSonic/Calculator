package sonar.calculator.mod.network.packets;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.ITextField;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.GuiSonar;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.core.utils.IUpgradeCircuits;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketTextField implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int id;
	public String string;

	public PacketTextField() {
	}

	public PacketTextField(String string, int xCoord, int yCoord, int zCoord, int id) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.string = string;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.string = ByteBufUtils.readUTF8String(buf);
		this.id = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		ByteBufUtils.writeUTF8String(buf, string);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketTextField, IMessage> {

		@Override
		public IMessage onMessage(PacketTextField message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if (te == null) {
				return null;
			}

			if (te instanceof ITextField) {
				ITextField field = (ITextField) te;
				field.textTyped(message.string, message.id);

			}
			return null;
		}

	}
}
