package sonar.calculator.mod.network.packets;

import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.GuiButtons;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
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

public class PacketFluxPoint implements IMessage {

	public int xCoord, yCoord, zCoord;
	public int string, id;
	public PacketFluxPoint() {
	}

	public PacketFluxPoint(int string, int xCoord, int yCoord, int zCoord, int id) {
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
		this.string = buf.readInt();
		this.id = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(string);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketFluxPoint, IMessage> {

		@Override
		public IMessage onMessage(PacketFluxPoint message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te =world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
			if(te!=null && te instanceof TileEntityFlux){
				TileEntityFlux flux = (TileEntityFlux) te;
				if(message.id==0){
				flux.setName(message.string);	
				System.out.print("id");			
				}else{
					TileEntityFluxPoint point = (TileEntityFluxPoint) te;
					point.priority=message.string;
					System.out.print("priority");		
				}
			}			
			return null;
		}

	}
}
