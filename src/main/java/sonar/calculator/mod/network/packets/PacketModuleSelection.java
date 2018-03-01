package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.item.calculators.FlawlessCalculator;
import sonar.core.SonarCore;
import sonar.core.utils.SonarCompat;

public class PacketModuleSelection implements IMessage {

	public String module;
	public int pos;

	public PacketModuleSelection() {
	}

	public PacketModuleSelection(IModule module, int pos) {
		this.module = module.getName();
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		module = ByteBufUtils.readUTF8String(buf);
		pos = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, module);
		buf.writeInt(pos);
	}

	public static class Handler implements IMessageHandler<PacketModuleSelection, IMessage> {

        @Override
		public IMessage onMessage(PacketModuleSelection message, MessageContext ctx) {
			EntityPlayer player = SonarCore.proxy.getPlayerEntity(ctx);
			if (player != null && ctx.side == Side.SERVER) {
				ItemStack held = player.getHeldItemMainhand();
				if (!SonarCompat.isEmpty(held)) {
					Item item = held.getItem();
					if (item instanceof FlawlessCalculator) {
						FlawlessCalculator calc = (FlawlessCalculator) item;
						NBTTagCompound tag = held.getTagCompound();
						tag.setInteger("slot", message.pos);
					}
				}
			}
			return null;
		}
	}
}
